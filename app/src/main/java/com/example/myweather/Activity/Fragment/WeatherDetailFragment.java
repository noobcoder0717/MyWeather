package com.example.myweather.Activity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    String CITYNAME;

    List<Forecast> forecastList;

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
}
