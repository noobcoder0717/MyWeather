package com.example.myweather.Activity.adapter;

import android.content.Context;
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
import com.example.myweather.R;

import org.litepal.LitePal;

import java.util.List;

public class DataAdapterForDeleteCity extends RecyclerView.Adapter<DataAdapterForDeleteCity.ViewHolder> {

    String weatherToday;
    List<Data> dataList;
    Context context;

    public DataAdapterForDeleteCity(List<Data> dataList,Context context){

        this.dataList=dataList;
        this.context=context;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CardView weatherCardview;
        CardView weatherCard;
        TextView DataName;
        TextView weatherDegree;
        TextView weatherToday;
        ImageView delete;

        ViewHolder(View view){
            super(view);
            weatherCard=view.findViewById(R.id.weather_photo_deletecity);
            DataName=view.findViewById(R.id.city_name_deletecity);
            weatherDegree=view.findViewById(R.id.weather_degree_deletecity);
            weatherCardview=view.findViewById(R.id.weather_cardview_deletecity);
            weatherToday=view.findViewById(R.id.weather_today_deletecity);
            delete=view.findViewById(R.id.delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_city,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position){
        final Data data=dataList.get(position);
        final int position1=position+1;

        holder.DataName.setText(data.getCity());
        holder.weatherDegree.setText(data.getWendu()+"℃");
        holder.weatherCardview.setBackgroundResource(R.drawable.weather);
        holder.weatherToday.setText(data.getWeatherToday());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=holder.DataName.getText().toString();
                Data data=new Data();
                data.setCity(city);
                LitePal.deleteAll(Data.class,"city=?",city);

            }
        });

    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}
