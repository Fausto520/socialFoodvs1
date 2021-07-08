package com.fausto.socialfood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Button cerrarSesion;
    ImageView imagenPerfil;
    TextView nombrePerfilUser,correoPerfilUser;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;
    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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


        vista = inflater.inflate(R.layout.fragment_perfil, container, false);

        cerrarSesion = vista.findViewById(R.id.cerrarSesion);
        nombrePerfilUser = vista.findViewById(R.id.perfilNombreUser);
        correoPerfilUser = vista.findViewById(R.id.perfilCorreoUser);
        imagenPerfil = vista.findViewById(R.id.imagenPerfil);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("USUARIOS_APP");




        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singOut();
            }
        });


        // Inflate the layout for this fragment
        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
          verificacion();;


    }

    public void verificacion(){

        //Si ha iniciado secion nos dirige directamente ha esta actividad
        if(firebaseUser != null){
            recuperarDatosUsuario();
            //Toast.makeText(this, "Se ha iniciado secion", Toast.LENGTH_SHORT).show();
            //Caso contrario nos dirige  al main
        }else{

            startActivity(new Intent(getContext(),MainActivity.class));


        }

    }

    public void singOut(){
    firebaseAuth.signOut();
        Toast.makeText(getContext(), "Ha cerrado secion", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(),MainActivity.class));

    }


    public void recuperarDatosUsuario(){

        Query query = BASE_DE_DATOS.orderByChild("correo").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            //recorremos los registros asta obtener el usuario activo
            public void onDataChange( DataSnapshot dataSnapshot) {
                  for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                      String uid = ""+snapshot.child("uid").getValue();
                      String correo = ""+snapshot.child("correo").getValue();
                      String nombre = ""+snapshot.child("nombre").getValue();
                      String imagen = ""+snapshot.child("imagen").getValue();

                      nombrePerfilUser.setText(nombre);
                      correoPerfilUser.setText(correo);


                      //Gestion de foto de perfil
                      //
                      try {

                          Uri myUri = Uri.parse(imagen);

                          Glide.with(getContext())
                                  .load(myUri)
                                  .placeholder(R.drawable.usuario)
                                  .into(imagenPerfil);


                      }catch (Exception e){

                          Glide.with(getContext())
                                  .load(R.drawable.usuario)
                                  .into(imagenPerfil);
                      }

                  }
            }

            @Override
            public void onCancelled( DatabaseError dataError) {

            }
        });


    }
}