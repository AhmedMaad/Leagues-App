package com.maad.footballleagueapplication.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maad.footballleagueapplication.database.PlayerListTypeConverter;

import java.util.List;

@Entity(tableName = "player")
public class PlayerModel extends Model {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("phone")
    private String phoneNumber;
    @SerializedName("website")
    private String website;
    @SerializedName("email")
    private String email;
    @Expose
    private int teamPlayerIdFK;
    @TypeConverters(PlayerListTypeConverter.class)
    @SerializedName("squad")
    private List<Player> players;


    @Ignore
    public PlayerModel(Context context) {
        super(context);

    }

    public PlayerModel(int id, String phoneNumber, String website, String email, List<Player> players) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.email = email;
        this.players = players;
    }

    public void setTeamPlayerIdFK(int teamPlayerIdFK) {
        this.teamPlayerIdFK = teamPlayerIdFK;
    }

    public int getId() {
        return id;
    }

    public int getTeamPlayerIdFK() {
        return teamPlayerIdFK;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public class Player {

        @SerializedName("name")
        private String playerName;
        @SerializedName("position")
        private String playerPosition;
        @SerializedName("nationality")
        private String playerNationality;

        public String getPlayerName() {
            return playerName;
        }

        public String getPlayerPosition() {
            return playerPosition;
        }

        public String getPlayerNationality() {
            return playerNationality;
        }
    }

    public void saveNewPlayerData(PlayerModel playerModel) {
        dbInstance().playerDAO().insertPlayer(playerModel);
        Log.d("RetrofitResponse", "New Player Data SAVED successfully");
    }

    public PlayerModel loadOldPlayerData(int playerId) {
        PlayerModel playerModel = dbInstance().playerDAO().selectTeamPlayers(playerId);
        Log.d("RetrofitResponse", "Old Player Data LOADED successfully");
        return playerModel;
    }

    public void deleteOldPlayerData(int playerId) {
        dbInstance().playerDAO().deleteTeamPlayer(playerId);
        Log.d("RetrofitResponse", "Old Player Data DELETED successfully");
    }

}
