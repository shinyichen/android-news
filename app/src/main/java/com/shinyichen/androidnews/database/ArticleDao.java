package com.shinyichen.androidnews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shinyichen.androidnews.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

  @Insert
  void saveArticle(Article article);

  @Query("SELECT * FROM article")
  LiveData<List<Article>> getAllArticles();

  @Delete
  void deleteArticles(Article... articles);
}
