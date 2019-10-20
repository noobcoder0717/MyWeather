package com.example.myweather.Activity.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.Utils.LineChart;
import com.example.myweather.Activity.adapter.ForecastAdapter;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.ClientApi;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.Activity.bean.Forecast;
import com.example.myweather.R;

import java.util.ArrayList;
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

public class WeatherDetailFragment extends Fragment {
    @Bind(R.id.city)
    TextView mCity;

    @Bind(R.id.now)
    TextView now;

    @Bind(R.id.temperature)
    TextView temperature;

    @Bind(R.id.linechart)
    LineChart lineChart;

    String CITYNAME;

    List<Forecast> forecastList;
    List<String> xAxis;
    List<Integer> low;
    List<Integer> high;

    @Bind(R.id.forecast_recyclerview)
    RecyclerView recyclerView;
    ForecastAdapter adapter;

    public static WeatherDetailFragment newInstance(String cityName){
        Bundle bundle=new Bundle();
        bundle.putSerializable("cityName",cityName);
        WeatherDetailFragment fragment=new WeatherDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_weather_detail,container,false);
        ButterKnife.bind(this,view);
        CITYNAME=getArguments().getString("cityName");
        forecastList=new ArrayList<>();
        xAxis=new ArrayList<>();
        low=new ArrayList<>();
        high=new ArrayList<>();
        System.out.println("onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        Retrofit retrofit=create();
        ClientApi api=retrofit.create(ClientApi.class);
        Observable<City> cityObservable=api.getCity(CITYNAME);

        cityObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<City>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(City city) {
                        System.out.println("onNext");
                        mCity.setText(city.getData().getCity());
                        now.setText(city.getData().getForecast().get(0).getType());
                        temperature.setText(city.getData().getWendu()+"℃");
                        forecastList.add(city.getData().getForecast().get(0));
                        forecastList.add(city.getData().getForecast().get(1));
                        forecastList.add(city.getData().getForecast().get(2));
                        forecastList.add(city.getData().getForecast().get(3));
                        forecastList.add(city.getData().getForecast().get(4));
                        xAxis.add(city.getData().getForecast().get(0).getDate());
                        xAxis.add(city.getData().getForecast().get(1).getDate());
                        xAxis.add(city.getData().getForecast().get(2).getDate());
                        xAxis.add(city.getData().getForecast().get(3).getDate());
                        xAxis.add(city.getData().getForecast().get(4).getDate());
                        low.add(getIntTemperature(city.getData().getForecast().get(0).getLow()));
                        low.add(getIntTemperature(city.getData().getForecast().get(1).getLow()));
                        low.add(getIntTemperature(city.getData().getForecast().get(2).getLow()));
                        low.add(getIntTemperature(city.getData().getForecast().get(3).getLow()));
                        low.add(getIntTemperature(city.getData().getForecast().get(4).getLow()));
                        high.add(getIntTemperature(city.getData().getForecast().get(0).getHigh()));
                        high.add(getIntTemperature(city.getData().getForecast().get(1).getHigh()));
                        high.add(getIntTemperature(city.getData().getForecast().get(2).getHigh()));
                        high.add(getIntTemperature(city.getData().getForecast().get(3).getHigh()));
                        high.add(getIntTemperature(city.getData().getForecast().get(4).getHigh()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter=new ForecastAdapter(forecastList);
                        recyclerView.setAdapter(adapter);
                        System.out.println(forecastList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(),"请连接网络后重试",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics dm=new DisplayMetrics();
                int width=dm.widthPixels;
                int height=dm.heightPixels;
                float xGap=0.8f*width/(xAxis.size()-1);

                lineChart.xAxis=xAxis;
                lineChart.low=low;
                lineChart.high=high;
                lineChart.width=width;
                lineChart.height=height;
                lineChart.xGap=xGap;

            }
        },1000);


    }
    @Override
    public void onStart(){
        super.onStart();
        System.out.println("onStart");
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

    public int getIntTemperature(String text){
        String temp=text.substring(3,text.length()-1);
        return Integer.parseInt(temp);
    }
}
