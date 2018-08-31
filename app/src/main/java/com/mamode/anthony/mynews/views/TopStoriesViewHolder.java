package com.mamode.anthony.mynews.views;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.adapters.TopStoriesAdapter;
import com.mamode.anthony.mynews.models.TopStoriesArticle;
import com.mamode.anthony.mynews.models.Multimedium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_item_snippet) TextView snippet;
    @BindView(R.id.recycler_item_ariane) TextView ariadneThread;
    @BindView(R.id.recycler_item_date) TextView date;
    @BindView(R.id.recycler_item_image) ImageView image;
    @BindView(R.id.recycler_item) View recyclerItem;

    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Use API data to fill recycler view item
    public void updateWithTopStoriesArticle(TopStoriesArticle article){
        this.snippet.setText(article.getAbstract());

        String ariadneThread = article.getSection();
        if (!article.getSubsection().equals("")){
            ariadneThread += " > " + article.getSubsection();
        }
        this.ariadneThread.setText(ariadneThread);

        String date = this.parseDate(article);
        this.date.setText(date);

        List<Multimedium> multimedia = article.getMultimedia();
        if (multimedia.size() != 0) {
            String url = multimedia.get(0).getUrl();
            Glide.with(image.getContext()).load(url).into(image);
        }
    }

    private String parseDate(TopStoriesArticle article){
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));   // This line converts the given date into UTC time zone
        java.util.Date dateObj = null;
        try {
            dateObj = sdf.parse(article.getPublishedDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(dateObj);
    }

    public void bind(final TopStoriesArticle article, final TopStoriesAdapter.OnItemClickListener listener) {
        recyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClik(article);
            }
        });
    }
}
