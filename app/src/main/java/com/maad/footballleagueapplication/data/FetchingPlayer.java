package com.maad.footballleagueapplication.data;

public interface FetchingPlayer {

    void onSuccess(PlayerModel playerModel);

    void onFailure(Throwable throwable);

}
