package com.fausto.socialfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterReceta extends RecyclerView.Adapter<AdapterReceta.RecetaHolder> {

    private ArrayList<Receta> recetas ;
    private LayoutInflater inflater;
    private Context context;

    public AdapterReceta(ArrayList<Receta> recetas, Context context){
        this.recetas = recetas;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }


    @Override
    public RecetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = inflater.inflate(R.layout.receta_item, parent, false);

        return new RecetaHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterReceta.RecetaHolder holder, int position) {

        Receta receta = recetas.get(position);

        holder.recetaTitulo.setText(receta.getTitulo());
        holder.recetaDescripcion.setText(receta.getDescripcion());
        Glide.with(context).load(receta.getImagen()).placeholder(R.drawable.ensalada_muestra).into(holder.recetaImagen);


    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    class RecetaHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

    TextView recetaTitulo,recetaDescripcion;
    ImageView recetaImagen;


    public RecetaHolder(@NonNull View itemView) {
        super(itemView);

        recetaTitulo = itemView.findViewById(R.id.recetaTitulo);
        recetaDescripcion = itemView.findViewById(R.id.recetaDescripcion);
        recetaImagen = itemView.findViewById(R.id.recetaImagen);

        itemView.setOnClickListener(this);


    }

        @Override
        public void onClick(View v) {

            int posicion = getLayoutPosition();

            Intent intent = new Intent(context, RecetaActivity.class);
            intent.putExtra("pocision",posicion);
            context.startActivity(intent);

        }
    }


}
