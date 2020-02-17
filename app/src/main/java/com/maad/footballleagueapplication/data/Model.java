package com.maad.footballleagueapplication.data;

import android.content.Context;

import androidx.room.Ignore;

import com.maad.footballleagueapplication.database.AppDB;

class Model {
    @Ignore
    private Context context;

    Model(Context context) {
        this.context = context;
    }

    Model() {
    }

    AppDB dbInstance() {
        return AppDB.getInstance(context);
    }

}
