package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.R;

public class WeatherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
    }

    public static void start(Context context, Data data){
        Intent intent=new Intent(context,WeatherDetailActivity.class );
        intent.putExtra("data",data);
        context.startActivity(intent);
    }
}
