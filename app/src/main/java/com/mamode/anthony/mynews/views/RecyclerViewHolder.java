package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mamode.anthony.mynews.newsmodels.Multimedia;
import com.mamode.anthony.mynews.newsmodels.NewsArticle;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.utils.NewsDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet)
    TextView snippet;
    @BindView(R.id.recycler_item_bread_crumbs)
    TextView breadCrumbs;
    @BindView(R.id.recycler_item_date)
    TextView date;
    @BindView(R.id.recycler_item_image)
    ImageView image;
    @BindView(R.id.recycler_item)
    View recyclerItem;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Use API data to fill recyclerView item.
     */
    public void updateWithArticleContent(NewsArticle article) {
        snippet.setText(article.getAbstract());
        displayBreadCrumbs(article);
        displayDate(article);
        displayImage(article);
    }

    /**
     * Set the breadcrumb text to the item view.
     * Breadcrumb is composed of the section type and possible with the subsection
     * type if its available.
     */
    private void displayBreadCrumbs(NewsArticle article) {
        String breadCrumbs = article.getSection();
        if (article.getSubsection() != null && !article.getSubsection().equals("")) {
            breadCrumbs += " > " + article.getSubsection();
        }
        this.breadCrumbs.setText(breadCrumbs);
    }

    /**
     * Set the published date of the article.
     */
    private void displayDate(NewsArticle article) {
        String date = NewsDate.setFrenchDateFormat(NewsDate.parseDate(article));
        this.date.setText(date);
    }

    /**
     * Set the miniature relative to the article.
     * If there is no image available, a placeholder is automatically displayed
     * (static image in res folder).
     */
    private void displayImage(NewsArticle article) {
        if (article.getMultimedia() != null) {
            List<Multimedia> multimedia = article.getMultimedia();
            if (multimedia.size() != 0) {
                String url = multimedia.get(0).getUrl();
                if (url.contains("https://")) {
                    Glide.with(image.getContext()).load(url).into(image);
                } else if (multimedia.get(2) != null) {
                    // The good image format is in the third place in the searchAPI response array
                    String formatUrl = "https://static01.nyt.com/" + multimedia.get(2).getUrl();
                    Glide.with(image.getContext()).load(formatUrl).into(image);
                }
            }
        } else if (article.getMedia() != null) {
            if (article.getMedia().size() != 0 && article.getMedia().get(0).getMediaMetadata().size() != 0) {
                String url = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                Glide.with(image.getContext()).load(url).into(image);
            }
        }
    }

    /**
     * Set onClickListener (on each recyclerView item) for fragment implementing the
     * OnItemClickListener interface.
     * This bind method is used by the Adapter (not by the ViewHolder directly).
     *
     * @param article  list of articles returned by the API
     * @param listener the fragment or activity which implements the method to handle what action
     *                 triggered on a RecyclerView click event.
     */
    public void bind(final NewsArticle article, final RecyclerViewAdapter.OnItemClickListener listener) {
        recyclerItem.setOnClickListener(view -> listener.onItemClick(article));
    }
}
