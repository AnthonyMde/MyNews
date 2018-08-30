package com.mamode.anthony.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.TopStoriesArticle;
import com.mamode.anthony.mynews.views.TopStoriesViewHolder;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder>{
    public interface OnItemClickListener {
        void onItemClik(TopStoriesArticle article);
    }

    private List<TopStoriesArticle> mArticles;
    private OnItemClickListener mListener;

    public TopStoriesAdapter(List<TopStoriesArticle> articles, OnItemClickListener listener) {
        super();
        mArticles = articles;
        mListener = listener;
    }

    @NonNull
    @Override
    public TopStoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new TopStoriesViewHolder(view);
    }

    //retrieve article object by position and set their UI
    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder holder, int position) {
        holder.updateWithTopStoriesArticle(mArticles.get(position));
        holder.bind(mArticles.get(position), mListener);
    }
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
