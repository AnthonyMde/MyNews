package com.mamode.anthony.mynews.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.mamode.anthony.mynews.NewsApi.ApiClient;
import com.mamode.anthony.mynews.NewsApi.NewsService;
import com.mamode.anthony.mynews.NewsRepository.NewsArticles;
import com.mamode.anthony.mynews.R;

import java.util.HashMap;
import java.util.Map;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class NotificationWorker extends Worker {
    private static final String CHANNEL_ID = "search_prefs_notification";
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        getArticlesThatMatchRequest(context);
        return Result.SUCCESS;
    }

    private void sendNotification(Context context, int articles) {
        String contentText = articles + " articles match your request !";
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_arrow_pull_to_refresh)
                .setContentTitle("New York Times")
                .setContentText(contentText)
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, notifBuilder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "search_prefs_notification";
            String description = "Send numbers of new articles that match the user saved research";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void getArticlesThatMatchRequest(Context context) {
        final NewsService newsService = ApiClient.getRetrofitInstance(context).create(NewsService.class);
        Map<String, String> query = getSavedQuery(context);
        Call<NewsArticles> call = newsService.getSearchArticles(query);
        // Start the call
        if (call != null) {
            call.enqueue(new Callback<NewsArticles>() {
                @Override
                public void onResponse(@NonNull Call<NewsArticles> call,@NonNull Response<NewsArticles> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int numberOfArticles = response.body().getSearchArticles().size();
                        createNotificationChannel(context);
                        sendNotification(context, numberOfArticles);
                    } else if (response.errorBody() != null) {
                        Log.e("API_RESPONSE_ERROR", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsArticles> call,@NonNull Throwable t) {
                    Log.e("API_FAILURE", t.toString());
                }
            });
        }
    }

    private Map<String, String> getSavedQuery(Context context) {
        String[] themes = {"Science", "Health", "World", "Technology", "Financial", "Education"};
        SharedPreferences sharedPrefs = context.getSharedPreferences("SAVED_NOTIFICATION", Context.MODE_PRIVATE);
        Map<String, String> query = new HashMap<>();
        String queryText = sharedPrefs.getString("query", "");
        StringBuilder sb = new StringBuilder();
        for (String theme: themes
             ) {
            if (sharedPrefs.getBoolean(theme, false)) {
                sb.append(String.format("\"%s\" ", theme));
            }
        }
        String queryThemes = String.format("news_desk(%s)", sb);
        Log.d(TAG, "getSavedQuery: " + queryThemes);
        if (!queryText.equals("")) {
            query.put("q", queryText);
            query.put("fq", queryThemes);
        }
        return query;
    }
}
