package com.fausto.socialfood;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView  cardViewEnsalada;
    CardView  cardViewSalsas;
    CardView  cardViewHamburguesas;
    CardView  cardViewPizzas;
    CardView  cardViewSopas;
    CardView  cardViewArroces;


    View vista;
    TextView ensaladaTitulo;
    TextView salsaTitulo;
    TextView hamburguesaTitulo;
    TextView pizzaTitulo;
    TextView sopasTitulo;
    TextView arrocesTitulo;

    public CatalogoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogoFragment newInstance(String param1, String param2) {
        CatalogoFragment fragment = new CatalogoFragment();
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
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_catalogo, container, false);

        ensaladaTitulo = vista.findViewById(R.id.ensaladaTitulo);
        salsaTitulo = vista.findViewById(R.id.salsaTitulo);
        hamburguesaTitulo = vista.findViewById(R.id.hamburguesaTitulo);
        pizzaTitulo = vista.findViewById(R.id.pizzasTitulo);
        sopasTitulo = vista.findViewById(R.id.sopaTitulo);
        arrocesTitulo = vista.findViewById(R.id.arrocesTitulo);

        String tituloEnsalda = ensaladaTitulo.getText().toString();
        String tiluloSalsa = salsaTitulo.getText().toString();
        String tituloHamburguesas = hamburguesaTitulo.getText().toString();
        String tiluloPizzas = pizzaTitulo.getText().toString();
        String tiluloSopas = sopasTitulo.getText().toString();
        String tiluloArroces = arrocesTitulo.getText().toString();


        cardViewEnsalada = vista.findViewById(R.id.catalogoEnsaladas);
        cardViewSalsas = vista.findViewById(R.id.catalogoSalsa);

        cardViewHamburguesas = vista.findViewById(R.id.catalogoHamburguesas);

        cardViewPizzas = vista.findViewById(R.id.catalogoPizzas);

        cardViewSopas = vista.findViewById(R.id.caatalogoSopas);

        cardViewArroces = vista.findViewById(R.id.catalogoArroz);


        cardViewEnsalada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canbiarActividad(tituloEnsalda);

            }
        });


        cardViewSalsas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canbiarActividad(tiluloSalsa);
            }
        });

        cardViewHamburguesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canbiarActividad(tituloHamburguesas);
            }
        });

        cardViewPizzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canbiarActividad(tiluloPizzas);
            }
        });

        cardViewSopas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canbiarActividad(tiluloSopas);
            }
        });

        cardViewArroces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canbiarActividad(tiluloArroces);
            }
        });



        return vista;
    }








    public void canbiarActividad(String titulo){
        Intent intent = new Intent(getContext(),ListadeRecetasActivity.class);
        intent.putExtra("titulos",titulo);
        startActivity(intent);


    }





}