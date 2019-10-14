package com.example.myweather.Activity.bean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientApi {
    @GET("weatherApi?")
    Observable<Data> getData(@Query("city") String city);
}
