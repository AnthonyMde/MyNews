package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.MostPopularArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) private TextView snippet;
    @BindView(R.id.recycler_item_ariane) private TextView ariadneThread;
    @BindView(R.id.recycler_item_date) private TextView date;
    @BindView(R.id.recycler_item_image) private ImageView image;


    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMostPopularArticle(MostPopularArticle article){

    }
}
