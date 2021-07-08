package com.fausto.socialfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.regex.Pattern;

public class registrateActivity extends AppCompatActivity {
private ImageView registroImagen;
private EditText registroNombre,registroCorreo,registroContrasena;
    private ProgressDialog progressDialog;
private Button btnregistrarse,btncancelar;
    private static final int GALLERY_INTENT = 1;
String url = "https://socil-food-default-rtdb.firebaseio.com/" ;
TextView registrateFoto;
FirebaseAuth firebaseAuth;
    StorageReference mStorage;
    Uri fotoPerfil;

//DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        /*ActionBar actionBar = getSupportActionBar();
        assert actionBar != null; //afirmamos titulo nulo
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
*/
        registroImagen = findViewById(R.id.registrateImagen);
        btnregistrarse = findViewById(R.id.btnRegsitrarse);
        btncancelar  = findViewById(R.id.btnRegistrarseCancelar);
        registroNombre = findViewById(R.id.registroNombre);
        registroCorreo = findViewById(R.id.registroCorreo);
        registroContrasena = findViewById(R.id.registroContraseña);
        registrateFoto=findViewById(R.id.registrateFoto);
        progressDialog = new ProgressDialog(registrateActivity.this); //iNICIIALIZAMOS PROGRES DIALOG
        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registroImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = registroCorreo.getText().toString();
                String contrasena = registroContrasena.getText().toString();

                //validacion correo y contraseña
                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches() ){
                    registroCorreo.setError("Correo no valido");
                    registroCorreo.setFocusable(true);
                }else if(contrasena.length() < 6){
                    registroContrasena.setError("Contraseña debe tener mas de 6 caracteres");
                    registroContrasena.setFocusable(true);
                }else{

                    registrarUsuario(correo,contrasena);
                }
            }
        });



    }
   //Metodo para registrar un usuario
    private void registrarUsuario(String correo, String contrasena) {
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Registrando");
        progressDialog.setMessage("Espere por favor");
        progressDialog.show();
    firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                // Si el registro es existoso
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                    //Datos que queremos registrar
                        //EditText deben ser diferentes a los string


                     String uid = user.getUid();
                     String nombre = registroNombre.getText().toString();
                     String correo = registroCorreo.getText().toString();
                     String contrasena = registroContrasena.getText().toString();
                     //ImageView = registroImagen.setImageURI(fotoPerfil);

                     //Creacion Hash mapa para enviar a firebase
                        HashMap<Object, String > datosUsuario = new HashMap<>();

                        datosUsuario.put("uid",uid);
                        datosUsuario.put("nombre", nombre);
                        datosUsuario.put("correo", correo);
                        datosUsuario.put("contrasena", contrasena);
                        datosUsuario.put("imagenPerfil","");



                     //inicializamos la instancia a la base de datos de firebase
                        //colocar la url en get instance
                        FirebaseDatabase database = FirebaseDatabase.getInstance(url);

                     //creamos la base de datos
                        DatabaseReference reference = database.getReference("USUARIOS_APP");

                        reference.child(uid).setValue(datosUsuario);

                        Toast.makeText(registrateActivity.this,"Se registro exitosamente",Toast.LENGTH_LONG).show();

                        //Una vez iniciado m¡nos dirigira al inicio

                        startActivity(new Intent(registrateActivity.this,inicioActivity.class));


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(registrateActivity.this, "Algo a salido mal", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull  Exception e) {
            progressDialog.dismiss();

            Toast.makeText(registrateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


        }
    });



    }

//Habilitamos la accion de retroceso
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Selecione la Aplicación"),GALLERY_INTENT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== GALLERY_INTENT && resultCode == RESULT_OK){

            fotoPerfil = data.getData();
            registroImagen.setImageURI(fotoPerfil);

           //Creacion de la carpeta a almacenar imagen
            StorageReference filePath = mStorage.child("FotosPerfil").child(fotoPerfil.getLastPathSegment());
            //Subir  Imagen
            filePath.putFile(fotoPerfil).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(registrateActivity.this, "Imagen Subida", Toast.LENGTH_SHORT).show();
                }
            });

        }


        }
    }
