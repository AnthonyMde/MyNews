package com.mamode.anthony.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.views.RecyclerViewHolder;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    /**
     * Implementing by fragment/activity which needs callback
     * on recyclerView item click.
     */
    public interface OnItemClickListener {
        void onItemClick(NewsArticle article);
    }
    private List<NewsArticle> mArticles;
    private OnItemClickListener mListener;

    /**
     * RecyclerView adapter constructor.
     * @param articles list of articles from NYT API.
     * @param listener fragment/activity which will handle the
     *                 click on RecyclerView items (articles).
     */
    public RecyclerViewAdapter(List<NewsArticle> articles, OnItemClickListener listener) {
        super();
        mArticles = articles;
        mListener = listener;
    }

    /**
     * @return an instance of our custom ViewHolder.
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    /**
     * We get the article according to the position in the RecyclerView.
     * We set the content and click listener using the RecyclerViewHolder
     * methods.
     * @param holder our custom ViewHolder.
     * @param position the position of the item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.updateWithArticleContent(mArticles.get(position));
        holder.bind(mArticles.get(position), mListener);
    }

    /**
     * Set the total size of the RecyclerView.
     * @return the number of articles we get from the API.
     */
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
