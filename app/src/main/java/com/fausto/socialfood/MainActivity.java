package com.fausto.socialfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button iniciarSecion,registrate,ingresaconGoogle;
    private EditText inicioSecionCorreo, inicioSecionContraseña;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private GoogleSignInClient googleSignInClient;
    private final static int RC_SING_IN = 123;
    String url = "https://socil-food-default-rtdb.firebaseio.com/" ;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarSecion = findViewById(R.id.btnIniciarsesion);
        registrate = findViewById(R.id.btnInicioRegistrarte);
        inicioSecionCorreo = findViewById(R.id.inicioSecionCorreo);
        inicioSecionContraseña = findViewById(R.id.inicioSecionContraseña);
        ingresaconGoogle = findViewById(R.id.btnIngresacongoogle);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this); //iNICIIALIZAMOS PROGRES DIALOG
        dialog = new Dialog(MainActivity.this); // inicializamos el dialog

       //inicio con google
        // 1 Creamos la solicitud gogle
        crearSolicitud();

        iniciarSecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = inicioSecionCorreo.getText().toString();
                String contrasena = inicioSecionContraseña.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    inicioSecionCorreo.setError("Correo no valido");
                    inicioSecionCorreo.setFocusable(true);
                }else if(inicioSecionContraseña.length()<6){

                    inicioSecionContraseña.setError("Contraseña debe tener mas de 6 caracteres");
                    inicioSecionContraseña.setFocusable(true);
                }else{
                    iniciarUsuario(correo,contrasena);
                }

            }
        });

        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registrateActivity.class);
                startActivity(intent);
            }
        });

        ingresaconGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    //metodo solicitud a google
    private void crearSolicitud() {
        //configuracioin inicio sesion de google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //Creamos un googlrsinginclient con las opciones especificadas por gso

        googleSignInClient = GoogleSignIn.getClient(this,gso);

    }

 //2 crear la pantalla de google
    private void signIn(){

        Intent signIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,RC_SING_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      //resultado al iniciar intencion desde googlesingInApi.getSingIntent
        if(requestCode == RC_SING_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
          //El inicio de correo fue exitoso autentique con firebase

                GoogleSignInAccount account = task.getResult(ApiException.class);

                autenticactionFirebase(account);

            }catch (ApiException e ){

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //metodo para autenticar firebase Google
    private void autenticactionFirebase(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //si inicio correcto
                            FirebaseUser user = firebaseAuth.getCurrentUser(); //Obtenemos el usuario el cual quiere iniciar sesion

                            //Si el usuario inicia secion por primera vez
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                String uid = user.getUid();
                                String correo = user.getEmail();
                                String nombre = user.getDisplayName();


                                //Creacion Hash mapa para enviar a firebase
                                HashMap<Object, String> datosUsuario = new HashMap<>();

                                datosUsuario.put("uid",uid);
                                datosUsuario.put("nombre", nombre);
                                datosUsuario.put("correo", correo);
                                datosUsuario.put("imagen","");


                                //inicializamos la instancia a la base de datos de firebase
                                //colocar la url en get instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance(url);

                                //creamos la base de datos
                                DatabaseReference reference = database.getReference("USUARIOS_APP");

                                reference.child(uid).setValue(datosUsuario);


                            }
                        //nos dirigimos a la actividad inicio
                            startActivity(new Intent(MainActivity.this, inicioActivity.class));

                        }else{

                            //Error al iniciar secion con google
                            dialogoPerzonalizado();

                        }

                    }
                });


    }

    //Medtodo para logear usuario
    private void iniciarUsuario(String correo , String contrasena) {

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Ingresando");
        progressDialog.setMessage("Espere por favor");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Si inicia sesion correcta mente

                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            //cunado inicie sesion nos envie a la actividad inicio
                            startActivity(new Intent(MainActivity.this,inicioActivity.class));
                            assert user != null; //Afirmamos que usuario no este basio
                            Toast.makeText(MainActivity.this, "Bienvenodo(a)"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();

                        }else{

                            progressDialog.dismiss();
                            dialogoPerzonalizado();
                            Toast.makeText(MainActivity.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Creamos el dialogo perzonalizado

    public void dialogoPerzonalizado(){

        Button btnEntendido;

        dialog.setContentView(R.layout.no_sesion);

        btnEntendido = dialog.findViewById(R.id.btnEntendido); //Conexion al dialogo creado

        btnEntendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false); //al tocar fuera del dialogo no se cerrara hasta presionar el boton
        dialog.show();

    }




}