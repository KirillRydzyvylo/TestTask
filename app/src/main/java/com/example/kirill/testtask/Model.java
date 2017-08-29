package com.example.kirill.testtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kirill.testtask.data.ShotData;
import com.example.kirill.testtask.realm.Shot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Retrofit;

/**
 * Created by kirill on 21.08.17.
 */

public class Model {

    private static final String DAY_LOAD = "dayLoad";



    interface GetDataFromDBComplete{
        void getComplete(ArrayList<Shot> shots);
    }

    interface  WriteDataToDBComplete{
        void writeComplete();
    }



    public  void addShotsToDB(List<Shot> shots, WriteDataToDBComplete complete) {
        App.getRealm().beginTransaction();
        Log.e("steps","4");
        for(Shot s : shots) {
            try{
                App.getRealm().insert(s);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        App.getRealm().commitTransaction();
        Log.e("steps","5");

        complete.writeComplete();
    }


    public static void getDateFromDB(GetDataFromDBComplete complete){
        Log.e("steps","8");
        ArrayList<Shot> shots  = new ArrayList<>();
        shots.addAll(App.getRealm().where(Shot.class).findAllSorted(DAY_LOAD, Sort.DESCENDING));
        complete.getComplete(shots);
        Log.e("steps","9");
    }

    public static int getDbSize(){
        return (int)App.getRealm().where(Shot.class).count();

    }

    public static long getMaxDayLoader(){
        return App.getRealm().where(Shot.class).max(DAY_LOAD).longValue();
    }

    public static long getMinDayLoader(){
        return App.getRealm().where(Shot.class).min(DAY_LOAD).longValue();
    }


    public static  boolean deleteOldShots(){
        App.getRealm().beginTransaction();
        App.getRealm().where(Shot.class).equalTo(DAY_LOAD,getMinDayLoader()).findAll().deleteAllFromRealm();
        App.getRealm().commitTransaction();
        return true;
    }

}
