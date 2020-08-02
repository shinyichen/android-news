package com.shinyichen.androidnews;

import android.app.Application;

import androidx.room.Room;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.facebook.stetho.Stetho;
import com.shinyichen.androidnews.database.AndroidNewsDatabase;

public class AndroidNewsApplication extends Application {

    private AndroidNewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
        Stetho.initializeWithDefaults(this);
        database = Room.databaseBuilder(this, AndroidNewsDatabase.class, "anews_db").build();

    }

    public AndroidNewsDatabase getDatabase() {
        return database;
    }
}
