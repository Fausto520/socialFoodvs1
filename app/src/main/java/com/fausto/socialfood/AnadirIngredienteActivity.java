package com.fausto.socialfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AnadirIngredienteActivity extends AppCompatActivity {

    EditText ingredienteNombretxt,ingredienteCantidadtxt;
    Button btnAnadirIngrediente;
    ArrayList<Ingrediente> ingredientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ingrediente);





        ingredienteNombretxt = findViewById(R.id.ingredienteNombretxt);
        ingredienteCantidadtxt = findViewById(R.id.ingredienteCantidadtxt);
        btnAnadirIngrediente = findViewById(R.id.btnAniadirIngrediente);

        ingredientes= DatosIngrediente.getInstance().getIngredientes();

        btnAnadirIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = ingredienteNombretxt.getText().toString();
                String cantidad = ingredienteCantidadtxt.getText().toString();

                ingredientes.add(new Ingrediente(nombre,cantidad));




                finish();


            }
        });





    }
}