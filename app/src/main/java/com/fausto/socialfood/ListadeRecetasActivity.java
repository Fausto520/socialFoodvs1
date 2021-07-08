package com.fausto.socialfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListadeRecetasActivity extends AppCompatActivity {
TextView tituloCatalogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_recetas);

        tituloCatalogo = findViewById(R.id.tituloCatalogo);


        Intent intent = getIntent();
        String titulos = intent.getStringExtra("titulos");
        tituloCatalogo.setText(titulos);





    }
}