package com.example.myweather.Activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myweather.Activity.Utils.Utils;
import com.example.myweather.Activity.bean.City;
import com.example.myweather.Activity.bean.ClientApi;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.Activity.bean.Forecast;
import com.example.myweather.R;

import org.litepal.LitePal;

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

public class AddCityFragment extends Fragment {
    @Bind(R.id.edittext_addcity)
    EditText editText;

    @Bind(R.id.button_addcity)
    Button button;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    String cityName;

    public static AddCityFragment newInstance(){
        AddCityFragment fragment=new AddCityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_add_city,container,false);
        ButterKnife.bind(this,view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isFastClick()){
                    final List<Data> dataList = LitePal.findAll(Data.class);
                    cityName = editText.getText().toString();
                    Retrofit retrofit = create();
                    ClientApi api = retrofit.create(ClientApi.class);
                    Observable<City> CityObservable = api.getCity(cityName);
                    CityObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<City>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(City city) {
                                    String msg = city.getMsg();
                                    if (msg.equals("未获取到相关数据!")) {
                                        Toast.makeText(getContext(), "未找到该城市", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String name = city.getData().getCity();//获取从服务器上返回的城市名
                                        if (name.equals(cityName)) {//判断返回的城市是否与输入相同，因为输入“汕”会返回汕头的数据，所以需要添加该判断
                                            if (city != null) {//判断目前要添加的城市之前是否添加过
                                                Data data = new Data();
                                                boolean flag = false;
                                                for (Data d : dataList) {
                                                    if (d.getCity().equals(cityName)) {
                                                        flag = true;
                                                        break;
                                                    }
                                                }
                                                if (flag) {
                                                    Toast.makeText(getContext(), "该城市已添加", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    data.setAqi(city.getData().getAqi());
                                                    data.setCity(city.getData().getCity());
                                                    data.setGanmao(city.getData().getGanmao());
                                                    data.setWendu(city.getData().getWendu());
                                                    data.setYesterday(city.getData().getYesterday());
                                                    data.setWeatherToday(city.getData().getForecast().get(0).getType());
                                                    data.save();
                                                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "未找到该城市", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                }
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar=appCompatActivity.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("添加城市");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
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
