package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.models.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) TextView snippet;
    @BindView(R.id.recycler_item_ariane) TextView ariane;
    @BindView(R.id.recycler_item_date) TextView date;


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
        this.ariane.setText(ariane);

        this.date.setText(article.getUpdatedDate());
    }
}
