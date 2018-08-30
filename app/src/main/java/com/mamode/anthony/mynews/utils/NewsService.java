package com.mamode.anthony.mynews.utils;

import com.mamode.anthony.mynews.models.MostPopulars;
import com.mamode.anthony.mynews.models.TopStories;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("svc/topstories/v2/home.json")
    Call<TopStories> getTopStories(@Query("api-key") String apiKey);

    @GET("svc/mostpopular/v2/mostviewed/all-sections/1.json")
    Call<MostPopulars> getMostPopular(@Query("api-key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}


