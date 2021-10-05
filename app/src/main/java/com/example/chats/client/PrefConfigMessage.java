package com.example.chats.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfigMessage {

    private static final String LIST_KEY_MESSAGE = "list_key_message";

    public static void writeListInPref(Context context, ArrayList<Message> list, String num){

        Gson gson2 = new Gson();
        String jsonString2 = gson2.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(num, jsonString2);
        editor.apply();
    }

    public static ArrayList<Message> readListFromPref(Context context, String num){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(num, null);

        Gson gson2 = new Gson();
        Type type2 = new TypeToken<ArrayList<Message>>() {}.getType();
        ArrayList<Message> list = gson2.fromJson(jsonString, type2);

        return list;
    }

    public static void deleteListInPref(Context context, String num){
        Toast.makeText(context.getApplicationContext(), ""+num, Toast.LENGTH_SHORT).show();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().remove(num).commit();

    }

}