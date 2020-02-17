package com.maad.footballleagueapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.maad.footballleagueapplication.data.TeamModel;

import java.util.List;

@Dao
public interface TeamDAO {

    @Insert
    void insertLeagueTeams(List<TeamModel.TeamDetail> teamDetails);

    @Query("SELECT * from teams WHERE leagueTeamIdFK = :id")
    List<TeamModel.TeamDetail> selectLeagueTeams(int id);

    @Query("DELETE FROM teams WHERE leagueTeamIdFK = :id")
    void deleteLeagueTeam(int id);

}
