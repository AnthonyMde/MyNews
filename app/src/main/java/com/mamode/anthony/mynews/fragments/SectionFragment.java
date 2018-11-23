package com.mamode.anthony.mynews.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mamode.anthony.mynews.NewsApi.NoConnectivityException;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.NewsApi.ArticleCalls;
import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.controllers.MainActivity;
import com.mamode.anthony.mynews.controllers.SearchActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.mamode.anthony.mynews.utils.NetworkUtil.isOnline;

public class SectionFragment extends Fragment implements ArticleCalls.onAPIResponseListener, RecyclerViewAdapter.OnItemClickListener {
    public interface onResearchComeBack {
        void returnToSearchFragment();
    }
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.indeterminateBar)
    ProgressBar mProgressBar;
    @BindView(R.id.section_frag_no_article_text)
    TextView mNoArticleFoundText;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mPullToRefresh;
    @BindView(R.id.arrow_pull_to_refresh)
    ImageView mArrowIconPullToRefresh;

    private SectionFragmentCallback mCallback;
    private static final String FRAGMENT_TYPE = "FRAGMENT-TYPE";
    private int mFragmentType = 0;
    private HashMap<String, String> mSearchQuery = new HashMap<>();
    private boolean mIsSnackBarDisplayed = false;
    private View mRootView = null;
    private onResearchComeBack mOnResearchComeBack;
    // Listen internet connection changes
    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isOnline(context) && mIsSnackBarDisplayed && mRootView != null) {
                Snackbar.make(mRootView, R.string.internet_connection_getback, Snackbar.LENGTH_LONG)
                        .show();
                mIsSnackBarDisplayed = false;
                mNoArticleFoundText.setVisibility(View.GONE);
                mArrowIconPullToRefresh.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                getDataApi();
            }
        }
    };

    public SectionFragment() {
        // Required empty public constructor
    }
    public interface SectionFragmentCallback {
        void openUrl(String url);
    }

    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type, HashMap<String, String> query) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        args.putSerializable("QueryHashMap", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null)
            mCallback = (SectionFragmentCallback) getActivity();
        if (getActivity() != null && getActivity() instanceof SearchActivity)
            mOnResearchComeBack = (onResearchComeBack) getActivity();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentType = getArguments().getInt(FRAGMENT_TYPE);
            mSearchQuery = (HashMap<String, String>) getArguments().getSerializable("QueryHashMap");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the root view if we want to display snackbar later.
        if (getView() != null)
            mRootView = getView().findViewById(R.id.main_recycler_view);
        mProgressBar.setVisibility(View.VISIBLE);
        getDataApi();
        configurePullToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (getContext() != null) {
            getContext().registerReceiver(networkChangeReceiver, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getContext() != null) {
            getContext().unregisterReceiver(networkChangeReceiver);
        }
    }

    /**
     * Method call if NYT API call is successful.
     * @param articles list of articles fetched from the NYT API.
     */
    @Override
    public void onResponse(@Nullable NewsArticles articles) {
        if (
            articles != null &&
            articles.getSearchArticles() != null &&
            articles.getSearchArticles().size() == 0
        ) {
            hideLoadingAndRefreshText();
            displayNoSearchResult();
        } else if (articles != null) {
            this.configureRecyclerView(articles);
            hideLoadingAndRefreshText();
        }
    }

    /**
     * Method call if NYT API call failed.
     */
    @Override
    public void onFailure(@NonNull Throwable t) {
        Log.e("ArticleCalls-onFailure", "Can not reach NYT data API");
        if (t instanceof NoConnectivityException && mRootView != null) {
            displayNoConnectionSnackBar();
        }
        hideLoadingButNotRefresh();
    }

    private void configureRecyclerView(NewsArticles articles) {
        if (articles.getArticles() != null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(articles.getArticles(), this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        } else if (articles.getSearchArticles() != null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(articles.getSearchArticles(), this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void configurePullToRefresh() {
        mPullToRefresh.setOnRefreshListener(this::getDataApi);
    }

    private void getDataApi(){
        if (mSearchQuery != null && !mSearchQuery.isEmpty())
            ArticleCalls.fetchNews(getContext(), this, mFragmentType, mSearchQuery);
        else
            ArticleCalls.fetchNews(getContext(), this, mFragmentType, null);
    }

    //Open webView on recyclerView item clicked.
    @Override
    public void onItemClick(NewsArticle article) {
        if (mCallback != null)
            mCallback.openUrl(article.getUrl());
    }

    private void displayNoConnectionSnackBar() {
        Snackbar.make(mRootView, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .show();
        mIsSnackBarDisplayed = true;
    }

    private void displayNoSearchResult() {
        if (getContext() != null) {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext(), styleCompatWithLolipopAndBelow());
            alertdialog
                    .setTitle(R.string.hmmm_snackbar)
                    .setMessage(R.string.snackbar_no_article_found_text)
                    .setPositiveButton(R.string.positive_btn_no_article_found_sb_text,(DialogInterface dialogInterface, int i) -> {
                                if (mOnResearchComeBack != null)
                                    mOnResearchComeBack.returnToSearchFragment();
                            }
                    )
                    .setNegativeButton(R.string.negative_btn_no_article_found_sb_text, (DialogInterface dialogInterface, int i) -> {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                    )
                    .show();
        }
    }

    private void hideLoadingAndRefreshText() {
        mProgressBar.setVisibility(View.GONE);
        mNoArticleFoundText.setVisibility(View.GONE);
        mArrowIconPullToRefresh.setVisibility(View.GONE);
        mPullToRefresh.setRefreshing(false);
    }

    private void hideLoadingButNotRefresh() {
        mProgressBar.setVisibility(View.GONE);
        mNoArticleFoundText.setVisibility(View.VISIBLE);
        mArrowIconPullToRefresh.setVisibility(View.VISIBLE);
        mPullToRefresh.setRefreshing(false);
    }

    private int styleCompatWithLolipopAndBelow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.R.style.Theme_Material_Dialog_Alert;
        }
        return R.style.Theme_AppCompat_Dialog_Alert;
    }
}
