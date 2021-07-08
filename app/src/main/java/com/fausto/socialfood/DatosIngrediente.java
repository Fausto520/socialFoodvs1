package com.fausto.socialfood;

import java.util.ArrayList;

public class DatosIngrediente {

    private static DatosIngrediente instancia;
    private static ArrayList<Ingrediente> ingredientes;

    public static DatosIngrediente getInstance(){
        if(instancia == null ){

            instancia = new DatosIngrediente();

        }
        return  instancia;
    }

    private DatosIngrediente(){

        ingredientes = new ArrayList<>();

    }

    public static void inicializarDatos(){
        instancia = null;
    }

    public ArrayList<Ingrediente> getIngredientes(){

        return ingredientes;

    }


}
