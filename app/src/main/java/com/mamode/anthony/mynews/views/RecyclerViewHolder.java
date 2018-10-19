package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.NewsRepository.NewsArticle;
import com.mamode.anthony.mynews.NewsRepository.Multimedia;
import com.mamode.anthony.mynews.utils.NewsDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) TextView snippet;
    @BindView(R.id.recycler_item_bread_crumbs) TextView breadCrumbs;
    @BindView(R.id.recycler_item_date) TextView date;
    @BindView(R.id.recycler_item_image) ImageView image;
    @BindView(R.id.recycler_item) View recyclerItem;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Use API data to fill recycler view item.
    public void updateWithArticleContent(NewsArticle article){
        snippet.setText(article.getAbstract());
        displayBreadCrumbs(article);
        displayDate(article);
        displayImage(article);
    }

    private void displayBreadCrumbs(NewsArticle article) {
        String breadCrumbs = article.getSection();
        if (article.getSubsection() != null && !article.getSubsection().equals("")){
            breadCrumbs += " > " + article.getSubsection();
        }
        this.breadCrumbs.setText(breadCrumbs);
    }
    private void displayDate(NewsArticle article) {
        String date = NewsDate.setFrenchDateFormat(NewsDate.parseDate(article));
        this.date.setText(date);
    }
    private void displayImage(NewsArticle article) {
        if (article.getMultimedia() != null) {
            List<Multimedia> multimedia = article.getMultimedia();
            if (multimedia.size() != 0) {
                String url = multimedia.get(0).getUrl();
                Glide.with(image.getContext()).load(url).into(image);
            }
        }else if (article.getMedia() != null) {
            if (article.getMedia().size() != 0 && article.getMedia().get(0).getMediaMetadata().size() != 0){
                String url = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                Glide.with(image.getContext()).load(url).into(image);
            }
        }
    }

    //Set onClickListener (on each recyclerView item) for fragment implementing the OnItemClickListener interface.
    //This bind method is used by the Adapter (no by the ViewHolder directly).
    public void bind(final NewsArticle article, final RecyclerViewAdapter.OnItemClickListener listener) {
        recyclerItem.setOnClickListener(view -> listener.onItemClick(article));
    }
}
