package com.fausto.socialfood;

import java.util.ArrayList;

public class DatosReceta {

    private static DatosReceta instancia;
    private static ArrayList<Receta> recetas;

public static DatosReceta getInstance(){
    if(instancia == null ){

        instancia = new DatosReceta();

    }
    return  instancia;
}

private DatosReceta(){

    recetas = new ArrayList<>();



}

public static void inicializarDatos(){
instancia = null;
}

public ArrayList<Receta> getRecetas(){

    return recetas;

    }


}
