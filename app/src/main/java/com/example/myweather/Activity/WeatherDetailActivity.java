package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myweather.Activity.Fragment.WeatherDetailFragment;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.R;

public class WeatherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        addFragment();
    }

    public static void start(Context context, String cityName){
        Intent intent=new Intent(context,WeatherDetailActivity.class );
        intent.putExtra("data",cityName);
        context.startActivity(intent);
    }

    public void addFragment(){
        String cityName=(String)getIntent().getSerializableExtra("data");
        Fragment fragment= WeatherDetailFragment.newInstance(cityName);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.weather_detail_container,fragment);
        transaction.commit();
    }
}
