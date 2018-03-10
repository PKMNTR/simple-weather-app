package com.example.pkmntr.greatweatherapp.rest;

import com.example.pkmntr.greatweatherapp.models.WeatherModels.Current;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pkmntr on 14/12/17.
 */

public interface WeatherApiService {

    @GET("{api_key}/conditions/q/{lat},{longitud}.json")
    Call<Current> getCurrentCoditions(@Path("api_key") String api_key, @Path("lat") String lat, @Path("longitud") String longitud);
}
