package com.maad.footballleagueapplication.data;

import com.maad.footballleagueapplication.data.LeagueModel;
import com.maad.footballleagueapplication.data.PlayerModel;
import com.maad.footballleagueapplication.data.TeamModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Callable {

    // TODO: Add your token here
    @Headers("X-Auth-Token: ")
    @GET("competitions")
    Call<LeagueModel> listLeagues();

    // TODO: Add your token here
    @Headers("X-Auth-Token: ")
    @GET("competitions/{id}/teams")
    Call<TeamModel> listTeams(@Path("id") int id);

    // TODO: Add your token here
    @Headers("X-Auth-Token: ")
    @GET("teams/{id}")
    Call<PlayerModel> listPlayers(@Path("id") int id);

}
