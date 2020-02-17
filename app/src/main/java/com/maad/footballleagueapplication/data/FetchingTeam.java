package com.maad.footballleagueapplication.data;

import com.maad.footballleagueapplication.data.TeamModel;

import java.util.List;


public interface FetchingTeam {

    void onSuccess(List<TeamModel.TeamDetail> teamDetails);
    void onFailure(Throwable throwable);
    void onPremiumAccess();
}
