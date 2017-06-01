package com.mta.weatherrest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mta.model.WeatherResponse;
import com.mta.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_current_weather);
                    getWeather();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    /**
     * retrofit example
     * for this json:
     * http://api.openweathermap.org/data/2.5/weather?q=Givatayim,il&appid=15cf9b712a8454b5fcd0670649018163&units=metric
     * <p>
     * paste here:
     * http://www.jsonschema2pojo.org/
     */
    private void getWeather() {
        String baseUrl = getResources().getString(R.string.weather_base_url);
        String key = getResources().getString(R.string.weather_api_key);

        WeatherService weatherService = RetrofitClient.getClient(baseUrl).create(WeatherService.class);

        Call<WeatherResponse> weather = weatherService.getWeather();
        weather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                mTextMessage.setText("It's "+response.body().getMain().getTemp()+" degrees currently in "+response.body().getName());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                mTextMessage.setText(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
