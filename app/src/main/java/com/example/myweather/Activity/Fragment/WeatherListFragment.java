package com.example.myweather.Activity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.adapter.DataAdapter;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherListFragment extends Fragment {
    List<Data> dataList;
    DataAdapter adapter;

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

        dataList=new ArrayList<>();
        dataList= LitePal.findAll(Data.class);//获取收藏城市列表
        dataList.add(new Data());

        adapter=new DataAdapter(dataList,getActivity());
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
        dataList.clear();
        dataList=LitePal.findAll(Data.class);
        dataList.add(new Data());
        adapter=new DataAdapter(dataList,getActivity());
        mRecyclerView.setAdapter(adapter);
        Log.i("WeatherListFragment","onStart()");
    }

    public static Retrofit create(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
