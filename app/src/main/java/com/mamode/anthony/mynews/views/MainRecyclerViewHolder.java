package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.TopStoriesFragment;
import com.mamode.anthony.mynews.models.Article;
import com.mamode.anthony.mynews.models.Multimedium;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) TextView snippet;
    @BindView(R.id.recycler_item_ariane) TextView ariadneThread;
    @BindView(R.id.recycler_item_date) TextView date;
    @BindView(R.id.recycler_item_image) ImageView image;


    public MainRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Use API data to fill recycler view item
    public void updateWithArticle(Article article){
        this.snippet.setText(article.getAbstract());

        String ariane = article.getSection();
        if (!article.getSubsection().equals("")){
            ariane += " > " + article.getSubsection();
        }
        this.ariadneThread.setText(ariane);

        this.date.setText(article.getUpdatedDate());

        List<Multimedium> multimedia = article.getMultimedia();
        if (multimedia.size() != 0) {
            String url = multimedia.get(0).getUrl();
            Glide.with(image.getContext()).load(url).into(image);
        }
    }
}
