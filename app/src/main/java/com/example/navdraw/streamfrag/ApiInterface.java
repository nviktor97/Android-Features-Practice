package com.example.navdraw.streamfrag;

import com.example.navdraw.streamfrag.Results;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("api/")
    Call<Results> getRandomUsers(@QueryMap Map<String, String> options);
}
