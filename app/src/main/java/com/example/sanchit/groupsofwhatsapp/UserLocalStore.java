package com.example.sanchit.groupsofwhatsapp;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

        public static final String SP_Name = "userDetails";
        SharedPreferences userLocalDatabase;

        public UserLocalStore(Context context)
        {
            userLocalDatabase = context.getSharedPreferences(SP_Name,0);
        }

    public void setCount(int flag){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putInt("count",flag);
        speditor.commit();
    }

    public int getCount(){

        int name = userLocalDatabase.getInt("count", 0);
        return name;
    }




}