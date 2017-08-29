package com.example.kirill.testtask.retrofit;

import com.example.kirill.testtask.data.ShotData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//https://api.dribbble.com/v1/shots?access_token=2a41fea1d3b9f39dd8637388ab84c69f333153fac82959404eb3a1b72a5d6af2&date=2017-08-16


public interface DribbbleAPI {
    @GET("/v1/shots")
    Call<List<ShotData>> getData(@Query("access_token") String password, @Query("date") String day);

}
