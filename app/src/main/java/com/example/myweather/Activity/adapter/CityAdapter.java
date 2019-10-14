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
import com.example.myweather.Activity.bean.City;
import com.example.myweather.R;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<City> cityList;
    Context context;

    public CityAdapter(List<City> cityList,Context context){
        this.cityList=cityList;
        this.context=context;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CardView weatherCardview;
        ImageView weatherPhoto;
        TextView cityName;
        TextView weatherDegree;

        ViewHolder(View view){
            super(view);
            weatherPhoto=view.findViewById(R.id.weather_photo);
            cityName=view.findViewById(R.id.city_name);
            weatherDegree=view.findViewById(R.id.weather_degree);
            weatherCardview=view.findViewById(R.id.weather_cardview);
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
                City city=cityList.get(position);
                if(position!=cityList.size()-1) {
                    WeatherDetailActivity.start(context, city);
                }else{
                    AddCityActivity.start(context);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        City city=cityList.get(position);
        if(position!=cityList.size()-1){
            holder.cityName.setText(city.getData().getCity());
            holder.weatherDegree.setText(city.getData().getWendu());
            holder.weatherPhoto.setImageResource(R.drawable.weather);
        }
    }

    @Override
    public int getItemCount(){
        return cityList.size();
    }
}
