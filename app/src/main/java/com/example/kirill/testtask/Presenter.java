package com.example.kirill.testtask;

import android.util.Log;

import com.example.kirill.testtask.data.ShotData;
import com.example.kirill.testtask.realm.Shot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kirill on 21.08.17.
 */

public class Presenter {
    private static final long MILLISECONDS_A_DAY= 86400000;
    private StartActivity activity;
    private Model model;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat fullDateFormat;
    private long loadDay;

    public Presenter(StartActivity activity) {
        this.activity = activity;
        simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        model = new Model();
    }

    public  void loadNextShots(){

        if(Model.getDbSize() > 50){
            Model.deleteOldShots();
        }

        try {
            loadDay = Model.getMaxDayLoader();
            Log.e("load","try");
        }
        catch (Exception e){
            Log.e("load","false");
            loadDay = new Date().getTime();
        }
        Log.e("load",Long.toString(loadDay));

        try{
            loadData(simpleDateFormat.format(new Date(countNextDay(loadDay))));
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            loadData(simpleDateFormat.format(new Date()));
        }

    }


    public void loadPreviousShots(){
        Log.e("Size",Integer.toString(Model.getDbSize()));
        if(Model.getDbSize() < 50) {

            try {
                loadDay = Model.getMinDayLoader();
            } catch (Exception e) {
                loadDay = new Date().getTime();
            }

            try{
                loadData(simpleDateFormat.format(new Date(countPreviousDay(loadDay))));
            }
            catch(IllegalArgumentException e){
                e.printStackTrace();
                loadData(simpleDateFormat.format(new Date()));
            }


        }
        else{
            Log.e("Error"," пределе количества сооб shots");
        }
        }



    public  void  loadData(final String date){

        fullDateFormat = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss");
        App.getApi().getData("2a41fea1d3b9f39dd8637388ab84c69f333153fac82959404eb3a1b72a5d6af2", date)
                .enqueue(new Callback<List<ShotData>>() {
                    @Override
                    public void onResponse(Call<List<ShotData>> call, Response<List<ShotData>> response) {

                        final List<Shot> shots = new ArrayList<>();
                        for(ShotData sd : response.body()){
                            if(!sd.isAnimated()) {
                                Shot shot = new Shot();
                                shot.setId(sd.getId());
                                shot.setTitle(sd.getTitle());

                                try{
                                    shot.setDescription(fixDescriptions(sd.getDescription()));
                                }
                                catch (NullPointerException e){
                                    e.printStackTrace();
                                    shot.setDescription("");
                                }

                                shot.setTeaser(sd.getImages().getTeaser());
                                shot.setNormal(sd.getImages().getNormal());
                                shot.setHidpi(sd.getImages().getHidpi());

                                try {
                                    shot.setDayLoad(fullDateFormat.parse(sd.getUpdate().substring(0,10).concat(sd.getUpdate().substring(11,19))).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Log.e("Shot",shot.toString());
                                shots.add(shot);
                            }
                        }

                        //если загрузить приложение в тот период пока нет даных по новому дню, то приложение будет подгружать 0 shots.
                        if(shots.size() == 0){
                            loadPreviousShots();
                        }

                        model.addShotsToDB(shots, new Model.WriteDataToDBComplete() {
                            @Override
                            public void writeComplete() {
                                activity.refreshSwipe();
                                showData();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<ShotData>> call, Throwable t) {
                        Log.e("loadData","Error");
                        activity.refreshSwipe();

                    }
                });


    }

    public  void showData(){
        Log.e("steps","7");
      Model.getDateFromDB(new Model.GetDataFromDBComplete(){
          @Override
          public void getComplete(ArrayList<Shot> shots) {
                for(Shot s : shots){
                    Log.e("steps",Long.toString(s.getDayLoad()));
                }
              activity.getShots().clear();
              activity.getShots().addAll(shots);
              activity.getRecyclerView().getAdapter().notifyDataSetChanged();
          }
      });
        Log.e("steps","10");

    }



    private  static long countPreviousDay(long day){
        if( day < 0) {
            throw new IllegalArgumentException();
        }
        return ((day - (day % MILLISECONDS_A_DAY)) - MILLISECONDS_A_DAY);
    }



    private static long countNextDay(long day){
        if( day < 0) {
            throw new IllegalArgumentException();
        }
        return ((day - (day % MILLISECONDS_A_DAY)) + MILLISECONDS_A_DAY);
    }



    private static String fixDescriptions(String jsonDescription){
        if(jsonDescription == null){
            throw new NullPointerException();
        }
        return jsonDescription.replaceAll("(?:<).*?(?:>)|\\n"," ").replaceAll("\\s{2,}"," ");
    }




}
