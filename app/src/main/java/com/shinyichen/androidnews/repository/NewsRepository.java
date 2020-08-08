package com.shinyichen.androidnews.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shinyichen.androidnews.AndroidNewsApplication;
import com.shinyichen.androidnews.database.AndroidNewsDatabase;
import com.shinyichen.androidnews.model.Article;
import com.shinyichen.androidnews.model.NewsResponse;
import com.shinyichen.androidnews.network.NetworkAPI;
import com.shinyichen.androidnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

  private final NetworkAPI.NewsApi newsApi;

  private final AndroidNewsDatabase database;


  public NewsRepository(Context context) {
    newsApi = RetrofitClient.newInstance(context).create(NetworkAPI.NewsApi.class);
    database = ((AndroidNewsApplication) context.getApplicationContext()).getDatabase();
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

  public LiveData<Boolean> favoriteArticle(Article article) {
    MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
    new FavoriteAsyncTask(database, resultLiveData).execute(article);
    return resultLiveData;
  }

  public LiveData<List<Article>> getAllSavedArticles() {
    return database.dao().getAllArticles();
  }

  public void deleteSavedArticle(Article article) {
    AsyncTask.execute(() -> database.dao().deleteArticles(article));
  }

  private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {

    private final AndroidNewsDatabase database;

    private final MutableLiveData<Boolean> liveData;


    private FavoriteAsyncTask(AndroidNewsDatabase database, MutableLiveData<Boolean> liveData) {
      this.database = database;
      this.liveData = liveData;
    }

    @Override
    protected Boolean doInBackground(Article... articles) {
      Article article = articles[0];
      try {
        database.dao().saveArticle(article);
      } catch (Exception e) {
        return false;
      }

      return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
      liveData.setValue(success);
    }
  }
}
