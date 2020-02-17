package com.maad.footballleagueapplication.data;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerNetworkManager extends RetrofitNetworkManager {

    public void loadNewPlayerData(FetchingPlayer fetchingPlayer, int teamId) {

        Callable callable = apiCall();
        Call<PlayerModel> playerCall = callable.listPlayers(teamId);
        playerCall.enqueue(new Callback<PlayerModel>() {
            @Override
            public void onResponse(Call<PlayerModel> call, Response<PlayerModel> response) {
                PlayerModel playerModel = response.body();
                playerModel.setTeamPlayerIdFK(playerModel.getId());
                fetchingPlayer.onSuccess(playerModel);
            }

            @Override
            public void onFailure(Call<PlayerModel> call, Throwable t) {
                fetchingPlayer.onFailure(t);
            }
        });
    }

}
