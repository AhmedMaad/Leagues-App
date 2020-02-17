package com.maad.footballleagueapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.maad.footballleagueapplication.data.LeagueModel;
import com.maad.footballleagueapplication.data.PlayerModel;
import com.maad.footballleagueapplication.data.TeamModel;

@Database(entities = {LeagueModel.Competitions.class
        , TeamModel.TeamDetail.class
        , PlayerModel.class}
        , version = 1
        , exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static AppDB instance;

    public static AppDB getInstance(Context context) {
        if (instance == null)
            synchronized (new Object()) {
                instance = Room
                        .databaseBuilder(context, AppDB.class, "football")
                        //It is a bad approach to work on the UI thread, but I didn't fully understand the executers.
                        .allowMainThreadQueries()
                        .build();
            }
        return instance;
    }

    public abstract LeagueDAO leagueDAO();

    public abstract TeamDAO teamDAO();

    public abstract PlayerDAO playerDAO();

}
