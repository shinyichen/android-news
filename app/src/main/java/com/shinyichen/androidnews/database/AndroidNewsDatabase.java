package com.shinyichen.androidnews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shinyichen.androidnews.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class AndroidNewsDatabase extends RoomDatabase {
  public abstract ArticleDao dao();
}
