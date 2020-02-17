package com.maad.footballleagueapplication.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamNetworkManager extends RetrofitNetworkManager {

    public void loadTeamData(FetchingTeam fetchingTeam, int leagueId) {
        Callable callable = apiCall();
        Call<TeamModel> teamCall = callable.listTeams(leagueId);
        teamCall.enqueue(new Callback<TeamModel>() {
            @Override
            public void onResponse(Call<TeamModel> call, Response<TeamModel> response) {
                if (response.code() == 403)
                    fetchingTeam.onPremiumAccess();
                else {
                    TeamModel teamModel = response.body();
                    List<TeamModel.TeamDetail> teamDetails = teamModel.getTeams();
                    for (int i = 0; i < teamDetails.size(); ++i)
                        teamDetails.get(i).setLeagueTeamIdFK(leagueId);
                    fetchingTeam.onSuccess(teamDetails);
                }
            }

            @Override
            public void onFailure(Call<TeamModel> call, Throwable t) {
                fetchingTeam.onFailure(t);
            }
        });
    }
}
