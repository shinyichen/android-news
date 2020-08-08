package com.shinyichen.androidnews.network;

import com.shinyichen.androidnews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NetworkAPI {
  public interface NewsApi {

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
      @Query("country") String country, @Query("pageSize") int pageSize);

    @GET("everything")
    Call<NewsResponse> getEverything(
      @Query("q") String query, @Query("pageSize") int pageSize);

  }
}
