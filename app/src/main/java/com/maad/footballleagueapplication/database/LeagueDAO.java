package com.maad.footballleagueapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.maad.footballleagueapplication.data.LeagueModel;

import java.util.List;

@Dao
public interface LeagueDAO {

    @Insert
    void insertAllLeague(List<LeagueModel.Competitions> competitions);

    @Query("SELECT * FROM leagues")
    List<LeagueModel.Competitions> selectAllLeagues();

    @Query("DELETE FROM leagues")
    void deleteAllLeagues();

}
