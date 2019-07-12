package com.example.asus.dictionary.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.asus.dictionary.R;

public class DictionaryPreference {

    private SharedPreferences prefs;
    private Context context;

    public DictionaryPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){

        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.dictionary_preference);
        editor.putBoolean(key,input);
        editor.apply();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.dictionary_preference);
        return prefs.getBoolean(key, true);
    }

}