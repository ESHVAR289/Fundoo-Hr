package com.bridgelabz.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SharedPreference {
    //Avoid mgic numbers.
    private static final int MAX_SIZE = 3;
    public SharedPreference(){
        super();
    }

    public static void storeList(Context context, String pref_name, String key, List countries){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(countries);
        editor.putString(key,jsonFavorites);
        editor.apply();
    }
    public static ArrayList<String> loadList(Context context,String pref_name,String key){
        SharedPreferences settings;
        List<String> favorites;
        settings = context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        if (settings.contains(key)){
            String jsonFavourites = settings.getString(key,null);
            Gson gson = new Gson();
            String[] favoritesItems = gson.fromJson(jsonFavourites,String[].class);
            favorites = Arrays.asList(favoritesItems);
            favorites = new ArrayList<>(favorites);
        }else
            return null;
        return (ArrayList<String>) favorites;
    }

    public static void addList(Context context,String pref_name,String key,String country){
        List<String> favorites = loadList(context,pref_name,key);
        if (favorites == null)
            favorites = new ArrayList<>();

        if (favorites.size() > MAX_SIZE){
            favorites.clear();
            deleteList(context,pref_name);
        }
        if (favorites.contains(country)){
            favorites.remove(country);
        }
        favorites.add(country);
        storeList(context,pref_name,key,favorites);
    }

    private static void deleteList(Context context, String pref_name) {
        SharedPreferences myPrefs = context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
    }

}
