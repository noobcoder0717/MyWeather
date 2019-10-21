package com.example.myweather.Activity.Fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.Utils.getDay;
import com.example.myweather.Activity.adapter.ForecastAdapter;
import com.example.myweather.Activity.adapter.XAxisAdapter;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.ClientApi;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.Activity.bean.Forecast;
import com.example.myweather.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Bind(R.id.toolbar_weatherdetail)
    Toolbar toolbar;

    String CITYNAME;


    List<Forecast> forecastList;
    List<String> xAxis;
    List<Integer> high;
    List<Integer> low;

    @Bind(R.id.forecast_recyclerview)
    RecyclerView recyclerView;
    ForecastAdapter adapter;

    public static WeatherDetailFragment newInstance(String cityName) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cityName", cityName);
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        ButterKnife.bind(this, view);
        CITYNAME = getArguments().getString("cityName");
        forecastList = new ArrayList<>();
        xAxis = new ArrayList<>();
        low = new ArrayList<>();
        high = new ArrayList<>();
        System.out.println("onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        setHasOptionsMenu(true);
        Retrofit retrofit = create();
        ClientApi api = retrofit.create(ClientApi.class);
        Observable<City> cityObservable = api.getCity(CITYNAME);

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
                        temperature.setText(city.getData().getWendu() + "℃");
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
                        adapter = new ForecastAdapter(forecastList);
                        recyclerView.setAdapter(adapter);
                        initLineChart(high, low);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "请连接网络后重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initLineChart(high, low);
                Log.i("WeatherDetailFragment", high.size() + "" + low.size());
            }
        }, 1000);


        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar=appCompatActivity.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
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

    public int getIntTemperature(String text) {
        String temp = text.substring(3, text.length() - 1);
        return Integer.parseInt(temp);
    }

    public void initLineChart(List<Integer> h, List<Integer> l) {
        String[] day = getDay.getFiveDay();
        Description description = new Description();
        ArrayList<Entry> low = new ArrayList<>();
        ArrayList<Entry> high = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        final List<String> dayList = Arrays.asList(day);

        description.setText("未来5日天气");
        description.setTextSize(10);

        for (int i = 0; i < 5; i++) {
            low.add(new Entry(Integer.parseInt(day[i]), l.get(i)));
            high.add(new Entry(Integer.parseInt(day[i]), h.get(i)));
        }
        LineDataSet lowTemp = new LineDataSet(low, "最低气温");
        LineDataSet highTemp = new LineDataSet(high, "最高气温");




        //设置x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1.0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//x轴在底部
        xAxis.setLabelCount(5, true);//true表示间距均分
        xAxis.setGridColor(getResources().getColor(R.color.transparent));//把网格颜色设为透明，从而隐藏网格
        xAxis.setValueFormatter(new IAxisValueFormatter() {//自定义x轴样式，设置为日期,格式为月份+“.”+日期
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String str=String.valueOf((int)value);
                String result=str.substring(0,2)+"."+str.substring(2,4);
                return result;
            }
        });

        //隐藏左右Y轴
        YAxis leftYAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        leftYAxis.setEnabled(false);
        rightYAxis.setEnabled(false);



        lowTemp.setValueFormatter(new IValueFormatter() {//设置温度显示样式，如果不设置，将默认显示xx.0，而不是xx℃
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String str=String.valueOf(value);
                return str.substring(0,str.length()-2).concat("℃");
            }
        });
        lowTemp.setValueTextSize(10f);
        lowTemp.setDrawCircleHole(false);
        lowTemp.setLineWidth(1);

        highTemp.setValueFormatter(new IValueFormatter() {//设置温度显示样式，如果不设置，将默认显示xx.0，而不是xx℃
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String str=String.valueOf(value);
                return str.substring(0,str.length()-2).concat("℃");
            }
        });
        highTemp.setValueTextSize(10f);
        highTemp.setDrawCircleHole(false);
        highTemp.setLineWidth(1);
        highTemp.setCircleColor(getResources().getColor(R.color.highTemp));
        highTemp.setColor(getResources().getColor(R.color.highTemp));



        dataSets.add(lowTemp);
        dataSets.add(highTemp);
        LineData data = new LineData(dataSets);

        lineChart.setDescription(description);
        lineChart.setHighlightPerTapEnabled(false);//隐藏点击高亮十字
        lineChart.setHighlightPerDragEnabled(false);//隐藏拖动高亮十字
        lineChart.setDoubleTapToZoomEnabled(false);//关闭双击放大
        lineChart.setData(data);



    }

}
