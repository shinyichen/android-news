package com.shinyichen.androidnews.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shinyichen.androidnews.model.NewsResponse;
import com.shinyichen.androidnews.network.NetworkAPI;
import com.shinyichen.androidnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

  private final NetworkAPI.NewsApi newsApi;

  public NewsRepository(Context context) {
    newsApi = RetrofitClient.newInstance(context).create(NetworkAPI.NewsApi.class);
  }

  public LiveData<NewsResponse> getTopHeadlines(String country) {
    MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
    newsApi.getTopHeadlines(country)
      .enqueue(new Callback<NewsResponse>() {
        @Override
        public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
          if (response.isSuccessful()) {
            topHeadlinesLiveData.setValue(response.body());
          } else {
            topHeadlinesLiveData.setValue(null);
          }
        }

        @Override
        public void onFailure(Call<NewsResponse> call, Throwable t) {
          topHeadlinesLiveData.setValue(null);
        }
      });
    return topHeadlinesLiveData;
  }

  public LiveData<NewsResponse> searchNews(String query) {
    MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
    newsApi.getEverything(query, 40)
      .enqueue(
        new Callback<NewsResponse>() {
          @Override
          public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
            if (response.isSuccessful()) {
              everyThingLiveData.setValue(response.body());
            } else {
              everyThingLiveData.setValue(null);
            }
          }

          @Override
          public void onFailure(Call<NewsResponse> call, Throwable t) {
            everyThingLiveData.setValue(null);
          }
        });
    return everyThingLiveData;
  }
}
