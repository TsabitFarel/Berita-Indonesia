package com.example.beritaindonesia.API;

import com.example.beritaindonesia.POJO.ResponseBerita;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("Top-headlines")
    Call<ResponseBerita> getHeadlines (
            @Query("Country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<ResponseBerita> getSpecificData (
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
