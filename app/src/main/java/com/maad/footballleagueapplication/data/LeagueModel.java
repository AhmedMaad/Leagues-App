package com.maad.footballleagueapplication.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class LeagueModel extends Model {

    public LeagueModel(Context context) {
        super(context);
    }

    @SerializedName("competitions")
    private List<Competitions> competitions;

    public List<Competitions> getCompetitions() {
        return competitions;
    }

    @Entity(tableName = "leagues")
    public static class Competitions {
        @SerializedName("name")
        private final String leagueLongName;
        @SerializedName("code")
        private final String leagueShortName;
        @PrimaryKey
        @SerializedName("id")
        private final int leagueId;

        public Competitions(String leagueLongName, String leagueShortName, int leagueId) {
            this.leagueLongName = leagueLongName;
            this.leagueShortName = leagueShortName;
            this.leagueId = leagueId;
        }

        public String getLeagueLongName() {
            return leagueLongName;
        }

        public String getLeagueShortName() {
            return leagueShortName;
        }

        public int getLeagueId() {
            return leagueId;
        }
    }

    public void saveNewLeagueData(List<LeagueModel.Competitions> competitions) {
        dbInstance().leagueDAO().insertAllLeague(competitions);
        Log.d("RetrofitResponse", "New League Data SAVED successfully");
    }

    public List<LeagueModel.Competitions> loadOldLeagueData() {
        List<LeagueModel.Competitions> competitions = dbInstance().leagueDAO().selectAllLeagues();
        //Reversing list elements to appear in the same order when it was saved
        Collections.reverse(competitions);
        Log.d("RetrofitResponse", "Showing Old League Data...");
        return competitions;
    }

    public void deleteOldLeagueData() {
        dbInstance().leagueDAO().deleteAllLeagues();
        Log.d("RetrofitResponse", "Old League Data DELETED successfully");
    }

}
