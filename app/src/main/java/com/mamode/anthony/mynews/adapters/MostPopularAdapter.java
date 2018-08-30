package com.mamode.anthony.mynews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.MostPopularArticle;
import com.mamode.anthony.mynews.views.MostPopularViewHolder;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder>{
    private List<MostPopularArticle> mArticles;

    public MostPopularAdapter(List<MostPopularArticle> articles) {
        super();
        mArticles = articles;
    }

    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new MostPopularViewHolder(view);
    }

    //retrieve article object by position and set their UI
    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder holder, int position) {
        holder.updateWithMostPopularArticle(mArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}