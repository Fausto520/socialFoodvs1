package com.fausto.socialfood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerReceta;
    AdapterReceta adapterReceta;
    ArrayList<Receta> recetas;
    ArrayList<Ingrediente> ingredientes;
    DatabaseReference mRootReference;
    //String tituloFirebase,categoriaFirebase,procedimientoFirebase,descripcionFirebase,ingredientesFirebase,imagenFirebase;

    View vista;



    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        mRootReference = FirebaseDatabase.getInstance().getReference();

        recyclerReceta = vista.findViewById(R.id.recyclerReceta);
        recetas = DatosReceta.getInstance().getRecetas();
        ingredientes = DatosIngrediente.getInstance().getIngredientes();
        adapterReceta = new AdapterReceta(recetas, getContext());
        recyclerReceta.setAdapter(adapterReceta);
        recyclerReceta.setLayoutManager(new LinearLayoutManager(getContext()));


        //Obtener datos de firebase

       mRootReference.child("receta").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

             recetas.removeAll(recetas);



               for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                   Receta receta = snapshot.getValue(Receta.class);

                  // receta.setTitulo(dataSnapshot.getValue().)

                   recetas.add(receta);



               }
               adapterReceta.notifyDataSetChanged();


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


        return vista;
    }





    }


