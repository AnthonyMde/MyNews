package com.mamode.anthony.mynews.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mamode.anthony.mynews.NewsApi.ArticleCalls;
import com.mamode.anthony.mynews.NewsApi.FragmentNewsType;
import com.mamode.anthony.mynews.NewsApi.NoConnectivityException;
import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.controllers.MainActivity;
import com.mamode.anthony.mynews.controllers.SearchActivity;
import com.mamode.anthony.mynews.utils.Animations;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mamode.anthony.mynews.utils.NetworkUtil.isOnline;
import static com.mamode.anthony.mynews.utils.Utils.styleCompatWithLollipopAndBelow;

public class SectionFragment extends Fragment implements ArticleCalls.onAPIResponseListener, RecyclerViewAdapter.OnItemClickListener {
    public interface onResearchComeBack {
        void returnToSearchFragment();
    }
    @BindView(R.id.main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.indeterminateBar) ProgressBar mProgressBar;
    @BindView(R.id.section_frag_no_article_text) TextView mNoArticleFoundText;
    @BindView(R.id.pullToRefresh) SwipeRefreshLayout mPullToRefresh;
    @BindView(R.id.arrow_pull_to_refresh) ImageView mArrowIconPullToRefresh;

    private SectionFragmentCallback mCallbackOnArticleClick;
    private static final String FRAGMENT_TYPE = "FRAGMENT-TYPE";
    private int mFragmentType = 0;
    private HashMap<String, String> mSearchQuery = new HashMap<>();
    private boolean mIsSnackBarDisplayed = false;
    private View mRootView = null;
    private onResearchComeBack mOnResearchComeBack;

    /**
     * Listen to internet connection changes. When the connection is retrieved,
     * it displays a SnackBar and reloads the page.
     */
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

    /**
     * Callback implemented by activities which need to
     * handle the click on articles list.
     */
    public interface SectionFragmentCallback {
        void openUrl(String url);
    }

    /**
     * @param type determine the type of SectionFragment returned.
     * @return an instance of SectionFragment
     */
    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This getter is used to get a search SectionFragment type.
     * @param query The user query used to make the articles research.
     * @return an instance of Section fragment (usually a search type).
     */
    public static SectionFragment newInstance(@FragmentNewsType.FragmentType int type, HashMap<String, String> query) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE, type);
        args.putSerializable("QueryHashMap", query);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * We set our activities callback here to be sure
     * the getActivity will not return a null pointer.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null)
            mCallbackOnArticleClick = (SectionFragmentCallback) getActivity();
        if (getActivity() != null && getActivity() instanceof SearchActivity)
            mOnResearchComeBack = (onResearchComeBack) getActivity();
    }

    /**
     * We retrieve our fragment type and search query params from bundle.
     * @param savedInstanceState Bundle containing the params we passed through when
     *                           creating our fragment instance.
     */
    @Override
    // The compiler can not be sure that the serializable's containing <String>,<String>
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentType = getArguments().getInt(FRAGMENT_TYPE);
            mSearchQuery = (HashMap<String, String>) getArguments().getSerializable("QueryHashMap");
        }
    }

    /**
     * Bind our views here.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Once the view is created we set the loader on, get the articles
     * from the API and enable the pull-to-refresh.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the root view if we want to display SnackBar later.
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
        // TODO: Change the implementation to avoid deprecated constant.
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
     * Call when NYT API call succeed.
     * Displays the list of articles in a RecyclerView or handle the
     * lack of articles with a custom behavior.
     * @param articles list of articles fetched from the NYT API.
     */
    @Override
    public void onResponse(@Nullable NewsArticles articles) {
        if (articles != null) {
            if (articles.getSearchArticles() != null && articles.getSearchArticles().size() == 0) {
                hideLoadingAndRefreshText();
                displayNoSearchResult();
            } else if (articles.getArticles() != null && articles.getArticles().size() == 0) {
                hideLoadingButNotRefresh();
                Animations.pullArrowAnimation(mArrowIconPullToRefresh);
            } else {
                configureRecyclerView(articles);
                hideLoadingAndRefreshText();
            }
        } else {
            hideLoadingButNotRefresh();
            Animations.pullArrowAnimation(mArrowIconPullToRefresh);
        }
    }

    /**
     * Call when NYT API call failed.
     * If the failure is due to a lack of internet connection, a
     * SnackBar is displayed to the user.
     */
    @Override
    public void onFailure(@NonNull Throwable t) {
        if (t instanceof NoConnectivityException && mRootView != null) {
            displayNoConnectionSnackBar();
        }
        hideLoadingButNotRefresh();
        Animations.pullArrowAnimation(mArrowIconPullToRefresh);
    }

    /**
     * We test which type of articles do we have to pass for the
     * RecyclerView.
     * @param articles NYT articles from the API.
     */
    private void configureRecyclerView(NewsArticles articles) {
        RecyclerViewAdapter adapter;
        if (articles.getArticles() != null) {
            adapter = new RecyclerViewAdapter(articles.getArticles(), this);
        } else {
            adapter = new RecyclerViewAdapter(articles.getSearchArticles(), this);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * We send a new request to the NYT API when the
     * user trigger the pull-to-refresh.
     */
    private void configurePullToRefresh() {
        mPullToRefresh.setOnRefreshListener(this::getDataApi);
    }

    /**
     * Make our call here. If some query are available, we are in a
     * search SectionFragment, so we call the NYT search API.
     */
    private void getDataApi(){
        if (mSearchQuery != null && !mSearchQuery.isEmpty())
            ArticleCalls.fetchNews(getContext(), this, mFragmentType, mSearchQuery);
        else
            ArticleCalls.fetchNews(getContext(), this, mFragmentType, null);
    }

    /**
     * Open webView on RecyclerView item clicked.
     * @param article the targeted article.
     */
    @Override
    public void onItemClick(NewsArticle article) {
        if (mCallbackOnArticleClick != null)
            mCallbackOnArticleClick.openUrl(article.getUrl());
    }

    /**
     * AlertDialog displayed when no result are found in the
     * SearchActivity. It allows to came back to the search fragment
     * or directly to the home.
     */
    private void displayNoSearchResult() {
        if (getContext() != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), styleCompatWithLollipopAndBelow());
            alertDialog
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

    private void displayNoConnectionSnackBar() {
        Snackbar.make(mRootView, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .show();
        mIsSnackBarDisplayed = true;
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
}
