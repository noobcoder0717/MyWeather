package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myweather.Activity.Fragment.DeleteCityFragment;
import com.example.myweather.R;

public class DeleteCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        addFragment();
    }

    public void addFragment(){
        DeleteCityFragment fragment=DeleteCityFragment.newInstance();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.delete_city_container,fragment);
        transaction.commit();
    }
}
