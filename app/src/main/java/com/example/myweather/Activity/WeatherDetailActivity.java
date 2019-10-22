package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myweather.Activity.Fragment.WeatherDetailFragment;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherDetailActivity extends AppCompatActivity {
    @Bind(R.id.weather_detail_viewpager)
    ViewPager mViewPager;
    List<Data> dataList;
    List<String> cityList;
    int position;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        ButterKnife.bind(this);
        cityList=new ArrayList<>();
        dataList=LitePal.findAll(Data.class);
        for(Data d:dataList){
            cityList.add(d.getCity());
        }
        position=getIntent().getIntExtra("position",0);
        city=getIntent().getStringExtra("data");
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        Log.i("WeatherDetailActivity",position+""+city);
        mViewPager.setCurrentItem(position);
    }

    public static void start(Context context, String cityName,int position){
        Intent intent=new Intent(context,WeatherDetailActivity.class );
        intent.putExtra("data",cityName);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm){super(fm);}

        public Fragment getItem(int i){return getWeatherDetailFragment(i);}

        public int getCount(){
            return cityList.size();
        }
    }

    public WeatherDetailFragment getWeatherDetailFragment(int position){
        String cityName=cityList.get(position);
        WeatherDetailFragment fragment=WeatherDetailFragment.newInstance(cityName);
        return fragment;
    }
}
