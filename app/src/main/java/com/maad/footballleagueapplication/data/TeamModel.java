package com.maad.footballleagueapplication.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamModel extends Model{

    @SerializedName("teams")
    private List<TeamDetail> teams;

    public TeamModel(Context context) {
        super(context);
    }

    List<TeamDetail> getTeams() {
        return teams;
    }

    @Entity(tableName = "teams")
    public static class TeamDetail implements Parcelable {

        @PrimaryKey
        @SerializedName("id")
        private final int teamId;
        @SerializedName("crestUrl")
        private final String teamLogo;
        @SerializedName("name")
        private final String teamLongName;
        @SerializedName("shortName")
        private final String teamShortName;

        @Expose
        private int leagueTeamIdFK;

        public void setLeagueTeamIdFK(int leagueTeamIdFK) {
            this.leagueTeamIdFK = leagueTeamIdFK;
        }

        public TeamDetail(int teamId, String teamLogo, String teamLongName, String teamShortName) {
            this.teamId = teamId;
            this.teamLogo = teamLogo;
            this.teamLongName = teamLongName;
            this.teamShortName = teamShortName;
        }

        public int getLeagueTeamIdFK() {
            return leagueTeamIdFK;
        }

        @Ignore
        TeamDetail(Parcel in) {
            teamId = in.readInt();
            teamLogo = in.readString();
            teamLongName = in.readString();
            teamShortName = in.readString();
        }

        public static final Creator<TeamDetail> CREATOR = new Creator<TeamDetail>() {
            @Override
            public TeamDetail createFromParcel(Parcel in) {
                return new TeamDetail(in);
            }

            @Override
            public TeamDetail[] newArray(int size) {
                return new TeamDetail[size];
            }
        };

        public String getTeamLogo() {
            return teamLogo;
        }

        public String getTeamLongName() {
            return teamLongName;
        }

        public String getTeamShortName() {
            return teamShortName;
        }

        public int getTeamId() {
            return teamId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(teamId);
            parcel.writeString(teamLogo);
            parcel.writeString(teamLongName);
            parcel.writeString(teamShortName);
        }
    }

    public void saveNewTeamData(List<TeamModel.TeamDetail> teamDetails) {
        dbInstance().teamDAO().insertLeagueTeams(teamDetails);
        Log.d("RetrofitResponse", "Team Data SAVED successfully");
    }

    public List<TeamModel.TeamDetail> loadOldTeamData(int leagueId) {
        List<TeamModel.TeamDetail> teamDetails = dbInstance().teamDAO().selectLeagueTeams(leagueId);
        Log.d("RetrofitResponse", "Old Team Data LOADED successfully");
        return teamDetails;
    }

    public void deleteOldTeamData(int leaguId) {
        dbInstance().teamDAO().deleteLeagueTeam(leaguId);
        Log.d("RetrofitResponse", "Team Data DELETED successfully");
    }

}
