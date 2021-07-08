package com.fausto.socialfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashActivity extends AppCompatActivity {
private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remplazamos la actividad main por la de inicio
                //en la actividad inicio hace la condicion en onstart
                Intent intent = new Intent(splashActivity.this,inicioActivity.class);
                startActivity(intent);
            }
        },2500);

    }
}