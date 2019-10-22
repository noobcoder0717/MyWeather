package com.example.myweather.Activity.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myweather.Activity.bean.Forecast;
import com.example.myweather.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    List<Forecast> forecastList;




    public ForecastAdapter(List<Forecast> forecastList){
        this.forecastList=forecastList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView weekday;
        TextView forecast;
        ImageView weatherType;
        ViewHolder(View view){
            super(view);
            weekday=view.findViewById(R.id.weekday);
            forecast=view.findViewById(R.id.forecast);
            weatherType=view.findViewById(R.id.weather_type);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Forecast forecast=forecastList.get(position);
        String lowTemperature=forecast.getLow().substring(3);
        String highTemperature=forecast.getHigh().substring(3);
        holder.weekday.setText(forecast.getDate());
        holder.forecast.setText(lowTemperature+" — "+highTemperature);
        switch(forecast.getType()){
            case "多云":
                holder.weatherType.setImageResource(R.drawable.cloudyandsunny);
                break;
            case "晴":
                holder.weatherType.setImageResource(R.drawable.sunny);
                break;
            case "阴":
                holder.weatherType.setImageResource(R.drawable.cloudy);
                break;
            case "小雨":
                holder.weatherType.setImageResource(R.drawable.smallrain);
                break;
            case "中雨":
                holder.weatherType.setImageResource(R.drawable.middlerain);
                break;
            case "大雨":
                holder.weatherType.setImageResource(R.drawable.bigrain);
                break;
            case "暴雨":
                holder.weatherType.setImageResource(R.drawable.heavyrain);
                break;
            case "小雪":
                holder.weatherType.setImageResource(R.drawable.smallsnow);
                break;
            case "中雪":
                holder.weatherType.setImageResource(R.drawable.middlesnow);
                break;
            case "大雪":
                holder.weatherType.setImageResource(R.drawable.bigsnow);
                break;
            case "雾":
                holder.weatherType.setImageResource(R.drawable.fog);
                break;
            case "雨夹雪":
                holder.weatherType.setImageResource(R.drawable.rainandsnow);
                break;
            case "雷阵雨":
                holder.weatherType.setImageResource(R.drawable.thunder);
                break;
                default:
                    holder.weatherType.setImageResource(R.drawable.sunny);
                    break;
        }
    }
    @Override
    public int getItemCount(){return forecastList.size();}
}
