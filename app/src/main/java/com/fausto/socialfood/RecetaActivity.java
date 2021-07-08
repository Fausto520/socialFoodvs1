package com.fausto.socialfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecetaActivity extends AppCompatActivity {

    ArrayList<Receta> recetas;
    ImageView imagenReceta;
    TextView tituloReceta,descripcionReceta,ingredientesReceta,procedimientoReceta;
    int pocicion;
    Receta receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        tituloReceta = findViewById(R.id.tituloReceta);

        descripcionReceta = findViewById(R.id.descripcionReceta);
        ingredientesReceta =findViewById(R.id.ingredientesReceta);

        procedimientoReceta = findViewById(R.id.procedimientoReceta);

        imagenReceta = findViewById(R.id.imagenReceta);


        procedimientoReceta.setMovementMethod(new ScrollingMovementMethod());
        ingredientesReceta.setMovementMethod(new ScrollingMovementMethod());
        descripcionReceta.setMovementMethod(new ScrollingMovementMethod());



        recetas = DatosReceta.getInstance().getRecetas();

        Intent intent = getIntent();
        pocicion = intent.getIntExtra("pocision",0);

        receta = recetas.get(pocicion);

        String uri = receta.getImagen();

        Uri myUri = Uri.parse(uri);


         tituloReceta.setText(receta.getTitulo());
         descripcionReceta.setText(receta.getDescripcion());

         procedimientoReceta.setText(receta.getProcedimiento());
         ingredientesReceta.setText(receta.getIngredientes());


         Glide.with(this)
                 .load(myUri)
                 .placeholder(R.drawable.ic_recetaerror)
                 .into(imagenReceta);





    }
}