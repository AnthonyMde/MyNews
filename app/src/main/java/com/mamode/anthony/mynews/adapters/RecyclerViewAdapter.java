package com.mamode.anthony.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.NewsArticle;
import com.mamode.anthony.mynews.views.RecyclerViewHolder;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    // Implementing by fragment who need callback on recyclerView item click.
    public interface OnItemClickListener {
        void onItemClick(NewsArticle article);
    }
    private List<NewsArticle> mArticles;
    private OnItemClickListener mListener;

    public RecyclerViewAdapter(OnItemClickListener listener){
        mListener = listener;
    }

    public RecyclerViewAdapter(List<NewsArticle> articles, OnItemClickListener listener) {
        super();
        mArticles = articles;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    // Retrieve article object by position,
    // set recyclerView item content with updateWithArticleContent() method,
    // set onClickListener for each item with bind() method.
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.updateWithArticleContent(mArticles.get(position));
        holder.bind(mArticles.get(position), mListener);
    }
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
