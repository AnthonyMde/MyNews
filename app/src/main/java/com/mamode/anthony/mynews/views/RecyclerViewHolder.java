package com.mamode.anthony.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.RecyclerViewAdapter;
import com.mamode.anthony.mynews.models.NewsArticle;
import com.mamode.anthony.mynews.models.Multimedia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) TextView snippet;
    @BindView(R.id.recycler_item_ariane) TextView ariadneThread;
    @BindView(R.id.recycler_item_date) TextView date;
    @BindView(R.id.recycler_item_image) ImageView image;
    @BindView(R.id.recycler_item) View recyclerItem;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Use API data to fill recycler view item.
    public void updateWithArticleContent(NewsArticle article){
        this.snippet.setText(article.getAbstract());

        String ariadneThread = article.getSection();
            if (article.getSubsection() != null && !article.getSubsection().equals("")){
                ariadneThread += " > " + article.getSubsection();
            }
            this.ariadneThread.setText(ariadneThread);

        String date = this.parseDate(article);
        this.date.setText(date);

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

    //Convert the data api date in usable String.
    private String parseDate(NewsArticle article){
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));   // This line converts the given date into UTC time zone
        java.util.Date dateObj;
        try {
            dateObj = sdf.parse(article.getPublishedDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(dateObj);
    }

    //Set onClickListener (on each recyclerView item) for fragment implementing the OnItemClickListener interface.
    //This bind method is used by the Adapter (no by the ViewHolder directly).
    public void bind(final NewsArticle article, final RecyclerViewAdapter.OnItemClickListener listener) {
        recyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(article);
            }
        });
    }
}
