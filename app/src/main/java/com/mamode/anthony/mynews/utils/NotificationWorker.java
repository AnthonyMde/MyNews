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

import com.mamode.anthony.mynews.newsapi.ApiClient;
import com.mamode.anthony.mynews.newsapi.NewsService;
import com.mamode.anthony.mynews.newsmodels.NewsArticles;
import com.mamode.anthony.mynews.R;

import java.util.HashMap;
import java.util.Map;

import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Allow to launch some work in background, one time or
 * periodically.
 * Under the hood, the worker uses the most appropriate
 * class for doing the job among AlarmManager,
 * JobScheduler and JobDispatcher.
 */
public class NotificationWorker extends Worker {
    /**
     * Channel is required when sending notification with
     * android SDK above Android Oreo.
     */
    private static final String CHANNEL_ID = "search_prefs_notification";
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * We are doing the background work here.
     */
    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        getArticlesThatMatchRequest(context);
        return Result.success();
    }


    /**
     * Here we send the notification to the user with a
     * title, an icon and the number of article we found.
     * @param articles the number of articles we get from
     *                 the NYT API.
     */
    private void sendNotification(Context context, int articles) {
        String contentText = articles + context.getString(R.string.notification_content_articles_found);
        String title = context.getString(R.string.notification_content_title);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.nyt_placeholder)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, builder.build());
    }

    /**
     * If the SDK version is above android Oreo, we create
     * a channel for sending our notifications.
     */
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notification_channel_title);
            String description = context.getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. We can't change notification behaviors after.
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Launch the API call here to know if some articles match the
     * user saved preferences.
     */
    private void getArticlesThatMatchRequest(Context context) {
        final NewsService newsService = ApiClient.getRetrofitInstance(context).create(NewsService.class);
        Map<String, String> query = getSavedQuery(context);
        Call<NewsArticles> call = newsService.getSearchArticles(query);
        // Start the call
        if (call != null) {
            call.enqueue(new Callback<NewsArticles>() {
                /**
                 * We send the notification only if we receive at least one article in
                 * our body reponse.
                 * @param call the retrofit API call.
                 * @param response the New York Times API response.
                 */
                @Override
                public void onResponse(@NonNull Call<NewsArticles> call,@NonNull Response<NewsArticles> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int numberOfArticles = response.body().getSearchArticles().size();
                        if (numberOfArticles > 0) {
                            createNotificationChannel(context);
                            sendNotification(context, numberOfArticles);
                        }
                    } else if (response.errorBody() != null) {
                        Log.e("API_RESPONSE_ERROR WM", response.errorBody().toString());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<NewsArticles> call,@NonNull Throwable t) {
                    Log.e("API_FAILURE", t.toString());
                }
            });
        }
    }

    /**
     * Retrieve the selected themes and the search query
     * from the user preferences.
     */
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
        if (queryText != null && !queryText.equals("")) {
            query.put("q", queryText);
            query.put("fq", queryThemes);
        }
        return query;
    }
}
