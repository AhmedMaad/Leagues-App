package com.maad.footballleagueapplication.data;

import com.maad.footballleagueapplication.data.LeagueModel;

import java.util.List;

public interface FetchingLeague {

    void onSuccess(List<LeagueModel.Competitions> competitions);
    void onFailure(Throwable throwable);

}
