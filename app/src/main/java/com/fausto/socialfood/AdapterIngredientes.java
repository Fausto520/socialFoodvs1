package com.fausto.socialfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterIngredientes extends  RecyclerView.Adapter<AdapterIngredientes.RecetasHolder>{

    private ArrayList<Ingrediente> ingredientes ;
    private LayoutInflater inflater;
    private Context context;

    public AdapterIngredientes(ArrayList<Ingrediente> ingredientes, Context context){
        this.ingredientes = ingredientes;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public RecetasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.ingrediente_item, parent, false);
        return new RecetasHolder(item);
    }

    @Override
    public void onBindViewHolder(@NotNull AdapterIngredientes.RecetasHolder holder, int position) {

        Ingrediente ingrediente = ingredientes.get(position);


        holder.ingredienteNombre.setText(ingrediente.getNombre());
        holder.ingredieneteCantidad.setText(ingrediente.getCantidad());

    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    class RecetasHolder extends RecyclerView.ViewHolder{

        TextView ingredieneteCantidad;
        TextView ingredienteNombre;




        public RecetasHolder(@NonNull View itemView) {
            super(itemView);

            ingredieneteCantidad = itemView.findViewById(R.id.ingredieneteCantidad);
            ingredienteNombre = itemView.findViewById(R.id.ingredienteNombre);







        }
    }




}
