package com.example.myweather.Activity.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

public class Data extends LitePalSupport implements Serializable {
    @SerializedName("yesterday")
    Yesterday yesterday;

    @SerializedName("city")
    String city;

    @SerializedName("aqi")
    String aqi;

    @SerializedName("forecast")
    List<Forecast> forecast;

    @SerializedName("ganmao")
    String ganmao;

    @SerializedName("wendu")
    String wendu;

    String weatherToday;//储存今天的天气状况

    List<String> weather;
    List<String> high;
    List<String> low;
    List<String> fengli;
    List<String> date;
    List<String> type;

    public List<String> getWeather() {
        return weather;
    }

    public void setWeather(List<String> weather) {
        this.weather = weather;
    }

    public List<String> getHigh() {
        return high;
    }

    public void setHigh(List<String> high) {
        this.high = high;
    }

    public List<String> getLow() {
        return low;
    }

    public void setLow(List<String> low) {
        this.low = low;
    }

    public List<String> getFengli() {
        return fengli;
    }

    public void setFengli(List<String> fengli) {
        this.fengli = fengli;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }



    public String getWeatherToday() {
        return weatherToday;
    }

    public void setWeatherToday(String weatherToday) {
        this.weatherToday = weatherToday;
    }

    public Yesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
}

