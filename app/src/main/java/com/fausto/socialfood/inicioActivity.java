package com.fausto.socialfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class inicioActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference mRootReference;



    //Fragments
    PerfilFragment perfilFragment = new PerfilFragment();
    InicioFragment inicioFragment = new InicioFragment();
    AnadirFragment anadirFragment = new AnadirFragment();
    CatalogoFragment catalogoFragment = new CatalogoFragment();
    BuscarFragment buscarFragment = new  BuscarFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

       /* ActionBar actionBar = getSupportActionBar();
        assert actionBar != null; //afirmamos titulo nulo
        actionBar.setTitle("Inicio");*/

        //frame:configuracion bottombavegation
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavegationItemSelectedListener);
        mRootReference = FirebaseDatabase.getInstance().getReference();

        //Llamar al metodo loadframe (que frame ba a aparecre)
        loadFragment(inicioFragment);





        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



    }

    @Override
    protected void onStart() {
        super.onStart();
        //llamamos a la funcion para que se ejecute cuando inicie la actividad
        verificacionInicioSecion();





    }

    //Metodo para verificar si un usuario ya ha iniciado secion
    public void verificacionInicioSecion(){

        //Si ha iniciado secion nos dirige directamente ha esta actividad
        if(firebaseUser != null){

            //Toast.makeText(this, "Se ha iniciado secion", Toast.LENGTH_SHORT).show();
        //Caso contrario nos dirige  al main
        }else{

            startActivity(new Intent(inicioActivity.this,MainActivity.class));
            finish();

        }

    }



    //Habilitamos la accion de retroceso
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //Bottom navegation conexion con el menu crado.

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavegationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


            switch (item.getItemId()){

                case (R.id.firstFragment):
                    loadFragment(perfilFragment);
                    return true;

                case (R.id.segundoFragment):
                    loadFragment(inicioFragment);
                    return true;

                case (R.id.tercerFragment):
                    loadFragment(anadirFragment);
                    return  true;

                case (R.id.cuartoFragment):
                    loadFragment(catalogoFragment);
                    return  true;

                case (R.id.quintoFragment):
                    loadFragment(buscarFragment);
                    return  true;

            }

            return false;

        }
    };


    //Metodo loadFragment
    public void loadFragment (Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();


    }

    //Metodo cerrar secion
    public void setCerrarSecion(){

        firebaseAuth.signOut();
        Toast.makeText(this, "ha cerrado sesion", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(inicioActivity.this,MainActivity.class));
    }




}