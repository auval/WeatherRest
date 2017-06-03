package com.mta.weatherrest;

import com.mta.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by amir on 6/1/17.
 */

public interface WeatherService {

    @GET("weather?q=Givatayim,il&appid=15cf9b712a8454b5fcd0670649018163&units=metric")
    Call<WeatherResponse> getWeather();

    /**
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/http/Query.html
     *
     * The @Query("q") annotation appends "&q=..the_city.." to the end of the GET command
     *
     * @param city
     * @return
     */
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String key,
            @Query("units") String units
            );

}
