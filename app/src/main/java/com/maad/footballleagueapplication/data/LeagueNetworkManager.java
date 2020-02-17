package com.maad.footballleagueapplication.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeagueNetworkManager extends RetrofitNetworkManager {

    public void loadNewLeagueData(FetchingLeague fetchingLeague) {
        Callable callable = apiCall();
        Call<LeagueModel> leagueCall = callable.listLeagues();
        leagueCall.enqueue(new Callback<LeagueModel>() {
            @Override
            public void onResponse(Call<LeagueModel> call, Response<LeagueModel> response) {
                LeagueModel leagueModel = response.body();
                List<LeagueModel.Competitions> competitions = leagueModel.getCompetitions();
                fetchingLeague.onSuccess(competitions);
            }

            @Override
            public void onFailure(Call<LeagueModel> call, Throwable t) {
                //Log.d("RetrofitResponse", "Failed: " + t.getMessage());
                fetchingLeague.onFailure(t);
            }
        });
    }
}