package com.example.kirill.testtask;

import android.app.Application;


import com.example.kirill.testtask.retrofit.DribbbleAPI;

import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kirill on 16.08.17.
 */

public class App extends Application {
    private static DribbbleAPI dribbbleAPI;
    private Retrofit retrofit;
    private static Realm realm;
    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dribbble.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dribbbleAPI = retrofit.create(DribbbleAPI.class);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }



    public static DribbbleAPI getApi() {
        return dribbbleAPI;
    }
    public static  Realm getRealm(){
        return realm;
    }
}
