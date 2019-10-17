package com.example.myweather.Activity.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.DeleteCityActivity;
import com.example.myweather.Activity.adapter.DataAdapter;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.ClientApi;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherListFragment extends Fragment {
    List<Data> dataList;
    DataAdapter adapter;


    @Bind(R.id.toolbar_weatherlist)
    Toolbar toolbar;

    @Bind(R.id.weather_list)
    RecyclerView mRecyclerView;

    public static WeatherListFragment newInstance() {
        WeatherListFragment fragment = new WeatherListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.i("WeatherListFragment","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("WeatherListFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataList = new ArrayList<>();
        dataList = LitePal.findAll(Data.class);//获取收藏城市列表
        dataList.add(new Data());

        adapter = new DataAdapter(dataList, getActivity());
        mRecyclerView.setAdapter(adapter);
        refresh();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_weatherlist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            case R.id.edit:
                startActivity(new Intent(getContext(), DeleteCityActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        dataList.clear();
        dataList = LitePal.findAll(Data.class);
        dataList.add(new Data());
        adapter = new DataAdapter(dataList, getActivity());
        mRecyclerView.setAdapter(adapter);
        Log.i("WeatherListFragment", "onStart()");
    }

    public static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void refresh() {
        for (int i = 0; i < dataList.size() - 1; i++) {
            String cityname = dataList.get(i).getCity();
            final int idx = i;


            Retrofit retrofit = create();
            ClientApi api = retrofit.create(ClientApi.class);
            Observable<City> cityObservable = api.getCity(cityname);
            cityObservable.subscribeOn(Schedulers.io())//这里改一下线程，会使onNext先于notifyDataSetChanged()调用
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<City>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(City city) {
                            dataList.get(idx).setWeatherToday(city.getData().getForecast().get(0).getType());
                            dataList.get(idx).setWendu(city.getData().getWendu());

                            Data data=new Data();
                            data.setWeatherToday(city.getData().getForecast().get(0).getType());
                            data.setWendu(city.getData().getWendu());
                            data.updateAll("city=?",dataList.get(idx).getCity());//更新数据库中对应城市的数据


                            Log.i("WeatherListFragment", "onNext");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("WeatherListFragment", e.toString());
                            Toast.makeText(getActivity(),"刷新失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                appCompatActivity.setSupportActionBar(toolbar);
                ActionBar actionBar = appCompatActivity.getSupportActionBar();
                Calendar calendar = Calendar.getInstance();
                int hour = (calendar.get(Calendar.HOUR_OF_DAY));
                int minute = calendar.get(Calendar.MINUTE);
                if (actionBar != null) {
                    if (minute < 10)
                        actionBar.setTitle("上次更新时间：" + hour + ":0" + minute);
                    else
                        actionBar.setTitle("上次更新时间：" + hour + ":" + minute);
                }
            }
        });

        //设置2秒延迟，更新recyclerView
        final Integer interval = 2000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                Log.i("WeatherListFragment", "adapter.notifyDataSetChanged()");
            }
        }, interval);

    }
}