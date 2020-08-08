package com.shinyichen.androidnews.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shinyichen.androidnews.model.Article;
import com.shinyichen.androidnews.repository.NewsRepository;

import java.util.List;

public class SaveViewModel extends ViewModel {

  private final NewsRepository repository;

  public SaveViewModel(NewsRepository repository) {
    this.repository = repository;
  }

  public LiveData<List<Article>> getAllSavedArticles() {
    return repository.getAllSavedArticles();
  }

  public void deleteSavedArticle(Article article) {
    repository.deleteSavedArticle(article);
  }
}
