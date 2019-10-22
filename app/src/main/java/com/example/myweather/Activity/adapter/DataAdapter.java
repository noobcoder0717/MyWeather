package com.example.myweather.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.AddCityActivity;
import com.example.myweather.Activity.WeatherDetailActivity;
import com.example.myweather.Activity.bean.Data;
import com.example.myweather.Activity.bean.Forecast;
import com.example.myweather.R;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
//    Forecast forecastListToday;
//    Forecast forecastListTomorrow;
//    Forecast forecastlistDayAfterTomorrow;
//    List<List<Forecast>> forecastList;
    String weatherToday;
    List<Data> dataList;
    Context context;

    public DataAdapter(List<Data> dataList,Context context){

        this.dataList=dataList;
        this.context=context;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout weatherCardview;
        CardView weatherCard;
        TextView DataName;
        TextView weatherDegree;
        TextView weatherToday;
        ImageView weathernow;

        ViewHolder(View view){
            super(view);

            DataName=view.findViewById(R.id.city_name);
            weatherDegree=view.findViewById(R.id.weather_degree);
            weatherCardview=view.findViewById(R.id.weather_cardview);
            weatherToday=view.findViewById(R.id.weather_today);
            weathernow=view.findViewById(R.id.weather_now);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.weatherCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                String cityName=dataList.get(position).getCity();
                if(position!=dataList.size()-1) {
                    WeatherDetailActivity.start(context, cityName);
                }else{
                    AddCityActivity.start(context);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Data data=dataList.get(position);
        if(position!=dataList.size()-1){
            holder.DataName.setText(data.getCity());
            holder.weatherDegree.setText(data.getWendu()+"℃");
            holder.weatherToday.setText(data.getWeatherToday());
            switch(data.getWeatherToday()){
                case "多云":
                    holder.weathernow.setImageResource(R.drawable.cloudyandsunny_list);
                    break;
                case "晴":
                    holder.weathernow.setImageResource(R.drawable.sunny_list);
                    break;
                case "阴":
                    holder.weathernow.setImageResource(R.drawable.cloudy_list);
                    break;
                case "小雨":
                    holder.weathernow.setImageResource(R.drawable.smallrain_list);
                    break;
                case "中雨":
                    holder.weathernow.setImageResource(R.drawable.middlerain_list);
                    break;
                case "大雨":
                    holder.weathernow.setImageResource(R.drawable.bigrain_list);
                    break;
                case "暴雨":
                    holder.weathernow.setImageResource(R.drawable.heavyrain_list);
                    break;
                case "小雪":
                    holder.weathernow.setImageResource(R.drawable.smallsnow_list);
                    break;
                case "中雪":
                    holder.weathernow.setImageResource(R.drawable.middlesnow_list);
                    break;
                case "大雪":
                    holder.weathernow.setImageResource(R.drawable.bigsnow_list);
                    break;
                case "雾":
                    holder.weathernow.setImageResource(R.drawable.fog_list);
                    break;
                case "雨夹雪":
                    holder.weathernow.setImageResource(R.drawable.rainandsnow_list);
                    break;
                case "雷阵雨":
                    holder.weathernow.setImageResource(R.drawable.thunder_list);
                    break;
                default:

                    break;
            }
        }
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}
