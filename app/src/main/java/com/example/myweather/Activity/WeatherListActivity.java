package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myweather.Activity.Fragment.WeatherListFragment;
import com.example.myweather.R;

public class WeatherListActivity extends AppCompatActivity {
    private WeatherListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        addFragment();
    }

    public void addFragment(){
        fragment=WeatherListFragment.newInstance();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.weather_list_container,fragment);
        transaction.commit();
    }
}
