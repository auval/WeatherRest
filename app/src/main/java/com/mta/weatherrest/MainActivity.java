package com.mta.weatherrest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mta.model.WeatherResponse;
import com.mta.util.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;// = (BottomNavigationView) findViewById(R.id.navigation);
    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.location)
    AppCompatEditText mWhere;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mWhere.getText().length() == 0) {
                        mWhere.setText("Ramat Gan,il");
                    }
                    getWeather(mWhere.getText().toString());
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_current_weather);
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
    private void getWeather(String city) {
        String baseUrl = getResources().getString(R.string.weather_base_url);
        String key = getResources().getString(R.string.weather_api_key);

        WeatherService weatherService = RetrofitClient.getClient(baseUrl).create(WeatherService.class);

        Call<WeatherResponse> weather = weatherService.getWeather(city);
        weather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                mTextMessage.setText("It's " + response.body().getMain().getTemp() + " degrees currently in " + response.body().getName());
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
        ButterKnife.bind(this);

        mWhere.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    getWeather(mWhere.getText().toString());
                    return true;
                }
                return false;
            }
        });
//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
