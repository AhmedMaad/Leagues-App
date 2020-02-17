package com.maad.footballleagueapplication.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maad.footballleagueapplication.data.PlayerModel;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PlayerListTypeConverter {

    private static final Gson gson = new Gson();

    @SuppressWarnings("WeakerAccess")
    @TypeConverter
    public static List<PlayerModel.Player> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<PlayerModel.Player>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @SuppressWarnings("WeakerAccess")
    @TypeConverter
    public static String someObjectListToString(List<PlayerModel.Player> someObjects) {
        return gson.toJson(someObjects);
    }

}
