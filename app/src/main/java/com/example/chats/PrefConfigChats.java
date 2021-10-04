package com.example.chats;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfigChats {

    private static final String LIST_KEY_CHATS = "list_key_chat";

    public static void writeListInPref(Context context, ArrayList<Chat> list){

        Gson gson1 = new Gson();
        String jsonString1 = gson1.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY_CHATS, jsonString1);
        editor.apply();
    }

    public static ArrayList<Chat> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY_CHATS, null);

        Gson gson1 = new Gson();
        Type type1 = new TypeToken<ArrayList<Chat>>() {}.getType();
        ArrayList<Chat> list = gson1.fromJson(jsonString, type1);

        return list;
    }

}