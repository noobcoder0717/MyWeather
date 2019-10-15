package com.example.myweather.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    Forecast forecastListToday;
    Forecast forecastListTomorrow;
    Forecast forecastlistDayAfterTomorrow;
    List<List<Forecast>> forecastList;
    List<Data> dataList;
    Context context;

    public DataAdapter(List<Data> dataList,Forecast forecastListToday,Forecast forecastListTomorrow,Forecast forecastlistDayAfterTomorrow,Context context){
        this.forecastListToday=forecastListToday;
        this.forecastListTomorrow=forecastListTomorrow;
        this.forecastlistDayAfterTomorrow=forecastlistDayAfterTomorrow;
        this.dataList=dataList;
        this.context=context;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CardView weatherCardview;
        ImageView weatherPhoto;
        TextView DataName;
        TextView weatherDegree;
        TextView weatherToday;

        ViewHolder(View view){
            super(view);
            weatherPhoto=view.findViewById(R.id.weather_photo);
            DataName=view.findViewById(R.id.city_name);
            weatherDegree=view.findViewById(R.id.weather_degree);
            weatherCardview=view.findViewById(R.id.weather_cardview);
            weatherToday=view.findViewById(R.id.weather_today);
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
                Data data=dataList.get(position);
                if(position!=dataList.size()-1) {
                    WeatherDetailActivity.start(context, data);
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
            holder.weatherPhoto.setImageResource(R.drawable.weather);
            holder.weatherToday.setText(forecastListToday.getType());
        }
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}