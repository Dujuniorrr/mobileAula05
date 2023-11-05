package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    ImageView imageLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageLogo = findViewById(R.id.ImageLogo);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageLogo, "rotation", 0, 360);
        rotation.setDuration(1500);
        rotation.start();

        new Timer().schedule(new TimerTask(){
            public void run(){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }

        }, 1800);

    }
}