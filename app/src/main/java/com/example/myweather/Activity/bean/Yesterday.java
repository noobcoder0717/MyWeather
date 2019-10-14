package com.example.myweather.Activity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Yesterday implements Serializable {
    @SerializedName("date")
    String date;

    @SerializedName("high")
    String high;

    @SerializedName("fx")
    String fx;

    @SerializedName("low")
    String low;

    @SerializedName("fl")
    String fl;

    @SerializedName("type")
    String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
