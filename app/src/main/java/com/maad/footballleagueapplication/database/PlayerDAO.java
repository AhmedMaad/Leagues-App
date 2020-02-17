package com.maad.footballleagueapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.maad.footballleagueapplication.data.PlayerModel;

@Dao
public interface PlayerDAO {

    @Insert
    void insertPlayer(PlayerModel playerModel);

    @Query("SELECT * FROM player where id = :id")
    PlayerModel selectTeamPlayers(int id);

    @Query("DELETE FROM player where teamPlayerIdFK = :id")
    void deleteTeamPlayer(int id);

}
