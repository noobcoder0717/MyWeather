package com.example.myweather.Activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.adapter.CityAdapter;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherListFragment extends Fragment {
    List<City> cityList;
    CityAdapter adapter;

    @Bind(R.id.weather_list)
    RecyclerView mRecyclerView;

    public static WeatherListFragment newInstance(){
        WeatherListFragment fragment=new WeatherListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_weather_list,container,false);
        ButterKnife.bind(this,view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        cityList=new ArrayList<>();
        cityList= LitePal.findAll(City.class);//获取收藏城市列表
        cityList.add(new City());

        adapter=new CityAdapter(cityList,getActivity());
        mRecyclerView.setAdapter(adapter);
    }
}
