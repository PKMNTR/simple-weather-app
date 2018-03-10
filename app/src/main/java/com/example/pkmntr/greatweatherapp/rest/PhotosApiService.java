package com.example.pkmntr.greatweatherapp.rest;

import com.example.pkmntr.greatweatherapp.models.PhotosModels.Photos;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ricky on 10/12/2017.
 */

public interface PhotosApiService {

    @GET("/photos/random/?featured")
    Call<Photos> getRandomPhoto(@Query("client_id") String apiKey);

}
