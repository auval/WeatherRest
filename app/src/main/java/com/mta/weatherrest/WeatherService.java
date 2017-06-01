package com.mta.weatherrest;

import com.mta.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by amir on 6/1/17.
 */

public interface WeatherService {

    @GET("weather?q=Givatayim,il&appid=15cf9b712a8454b5fcd0670649018163&units=metric")
    Call<WeatherResponse> getWeather();

}