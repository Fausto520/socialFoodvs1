package com.fausto.socialfood;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnadirFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = -1;
    private static final int GALLERY_INTENT = 2;
    private Receta.Categorias categoriasReceta;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText anadirProcedimiento,aniadirTitulo,aniadirDescripcion;
    ImageButton anadirIngredientebtn;
    View vista;
    RecyclerView recyclerIngredientes;
    AdapterIngredientes adapterIngrediente;
    ArrayList<Ingrediente> ingredientes;
    ArrayList<Receta> recetas;
    Spinner spinnerCatalogo;
    String seleccion;
    Button btnAniadirReceta;
    Button btnClear;
    StorageReference mStorage;
    ImageView aniadirImagen;
    String categoria = "";



    DatabaseReference mRootReference;



    public AnadirFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirFragment newInstance(String param1, String param2) {
        AnadirFragment fragment = new AnadirFragment();
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

        vista = inflater.inflate(R.layout.fragment_anadir, container, false);


        anadirProcedimiento = vista.findViewById(R.id.anadirProcedimiento);
        anadirIngredientebtn = vista.findViewById(R.id.aniadirIngredientebtn);
        btnAniadirReceta = vista.findViewById(R.id.btnAniadirReceta);
        btnClear = vista.findViewById(R.id.btnClear);
        aniadirImagen = vista.findViewById(R.id.aniadirImagen);
        aniadirTitulo = vista.findViewById(R.id.anadirTitulo);
        aniadirDescripcion = vista.findViewById(R.id.aniadirDescripcion);


        mRootReference = FirebaseDatabase.getInstance().getReference();


        ingredientes = DatosIngrediente.getInstance().getIngredientes();
        recetas = DatosReceta.getInstance().getRecetas();

        mStorage = FirebaseStorage.getInstance().getReference();

       //Imagneview para añadir imagen
        aniadirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentImagen = new Intent(Intent.ACTION_PICK);
                intentImagen.setType("image/*");
                startActivityForResult(intentImagen, GALLERY_INTENT);
            }
        });


        //Scroller EditText
        anadirProcedimiento.setScroller(new Scroller(getActivity().getApplication()));
        anadirProcedimiento.setVerticalScrollBarEnabled(true);
        anadirProcedimiento.setMinLines(2);
        anadirProcedimiento.setMaxLines(30);


        //Spinner
        spinnerCatalogo = vista.findViewById(R.id.spinnerCatalogo);
        String[] categorias = new String[]{"Ensaladas", "Salsas", "Hamburguesas", "Pizzas", "Sopas", "Arroces"};
        ArrayAdapter<CharSequence> adapterCategoria = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categorias);
        spinnerCatalogo.setAdapter(adapterCategoria);

        //btn Aniadir receta
        btnAniadirReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seleccionCategoria();


                String tituloReceta = aniadirTitulo.getText().toString();
                String descripcionReceta = aniadirDescripcion.getText().toString();
                String procedimientoReceta = anadirProcedimiento.getText().toString();
                String ingredientesReceta = "";
                String imagenReceta = "";


                for (Ingrediente ingre:ingredientes) {



                    ingredientesReceta += ingre.getCantidad() +" "+ingre.getNombre();
                    ingredientesReceta += ",";


                }

              //  recetas.add(new Receta(tituloReceta,descripcionReceta,ingredientesReceta,procedimientoReceta,imagenReceta,categoria));

                Toast.makeText(getContext(), "Receta Publicada", Toast.LENGTH_SHORT).show();




                //Metodo cargar datos a Firebase

                cargarDatosFirebase(tituloReceta, descripcionReceta, procedimientoReceta, ingredientesReceta);


            }



        });

        //btn Clear
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadirTitulo.setText("");
                anadirProcedimiento.setText("");
                aniadirDescripcion.setText("");
                ingredientes.clear();
                adapterIngrediente.notifyDataSetChanged();

            }
        });

        //btn Añadir ingrediente
      anadirIngredientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AnadirIngredienteActivity.class);

                startActivityForResult(intent, 20);

            }
        });


        //Adapter y RecyclerView de ingredientes (Ingredientes)

        recyclerIngredientes = vista.findViewById(R.id.recyclerIngredientes);
        adapterIngrediente = new AdapterIngredientes(ingredientes, getContext());
        recyclerIngredientes.setAdapter(adapterIngrediente);
        recyclerIngredientes.setLayoutManager(new LinearLayoutManager(getContext()));


        return vista;


    }

    private void cargarDatosFirebase(String tituloReceta, String descripcionReceta, String procedimientoReceta, String ingredientesReceta) {
        Map<String,Object> datosReceta = new HashMap<>();
        datosReceta.put("Titulo", tituloReceta);
        datosReceta.put("Descripcion", descripcionReceta);
        datosReceta.put("Procedimiento", procedimientoReceta);
        datosReceta.put("Ingredientes", ingredientesReceta);
        datosReceta.put("imagen","");
        datosReceta.put("Categoria", categoria);


        mRootReference.child("receta").push().setValue(datosReceta);
    }

    //Colocamos en onResume para que actualice el Adapter
    @Override
    public void onResume() {
        super.onResume();

        adapterIngrediente.notifyDataSetChanged();
    }

    //Metodo para la seleccion del spinner
    public void seleccionCategoria() {

        seleccion = spinnerCatalogo.getSelectedItem().toString();

        if (seleccion.equals("Ensaladas")) {
            Toast.makeText(getContext(), "Ensaladas", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.ENSALADAS;
            categoria = "Ensaladas";

        } else if (seleccion.equals("Salsas")) {
            Toast.makeText(getContext(), "Salsas", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.SALSAS;
            categoria = "Salsas";


        } else if (seleccion.equals("Hamburguesas")) {
            Toast.makeText(getContext(), "Hamburguesas", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.HAMBURGUESAS;
            categoria = "Hamburguesas";

        } else if (seleccion.equals("Pizzas")) {
            Toast.makeText(getContext(), "Pizzas", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.PIZZAS;
            categoria = "Pizzas";


        } else if (seleccion.equals("Sopas")) {
            Toast.makeText(getContext(), "Sopas", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.SOPAS;
            categoria = "Sopas";

        } else if (seleccion.equals("Arroces")) {
            Toast.makeText(getContext(), "Arroces", Toast.LENGTH_SHORT).show();
            categoriasReceta = Receta.Categorias.ARROZ;
            categoria = "Arroces";

        }

    }

    //Metodo para guardar imagen
  /*@Override

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INTENT || resultCode == RESULT_OK) ;


        imagenReceta = data.getData();

        aniadirImagen.setImageURI(imagenReceta);

        //Creacion de la carpeta a almacenar imagen
        StorageReference filePath = mStorage.child("ImagenRecetas").child(imagenReceta.getLastPathSegment());
        //Subir  Imagen
        filePath.putFile(imagenReceta).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Imagen Subida", Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}

