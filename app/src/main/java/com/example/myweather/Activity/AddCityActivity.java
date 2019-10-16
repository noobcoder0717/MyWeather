package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myweather.Activity.Fragment.AddCityFragment;
import com.example.myweather.R;

public class AddCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        addFragment();
    }

    public static void start(Context context){
        Intent intent=new Intent(context,AddCityActivity.class);
        context.startActivity(intent);
    }

    public void addFragment(){
        AddCityFragment fragment=AddCityFragment.newInstance();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.add_city_container,fragment);
        transaction.commit();
    }
}
