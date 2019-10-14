package com.example.myweather.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myweather.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    @Bind(R.id.welcome)
    ImageView welcomePicture;
    @Bind(R.id.welcome_text)
    TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        Animation ani= AnimationUtils.loadAnimation(this,R.anim.welcome);
        ani.setFillAfter(true);

        welcomePicture.startAnimation(ani);
        welcomeText.startAnimation(ani);

        Integer time=3000;
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,WeatherListActivity.class));
                WelcomeActivity.this.finish();
            }
        },time);

    }
}
