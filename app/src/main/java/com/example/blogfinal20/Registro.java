package com.example.blogfinal20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blogfinal20.Modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registro extends AppCompatActivity {

    //Declaro VARIABLES PARA REGISTRO
    private String nombre;
    private String email;
    private String telefono;
    private String password;

    //Declaro Objeto firebase
    FirebaseAuth fAuth;

    //Declaro Objeto Database
    DatabaseReference dataRef;

    Button btnregistrar, btnatras;
    EditText editNombre, editEmail, editTelefono, editContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnatras = findViewById(R.id.btnAtrasRegistro);
        btnregistrar = findViewById(R.id.btnRegistrar);
        editNombre = findViewById(R.id.editNombre);
        editEmail = findViewById(R.id.editEmail);
        editTelefono = findViewById(R.id.editPhone);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        //Instancio firebase
        fAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance("https://blogfinal-6ea1e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = editNombre.getText().toString();
                email = editEmail.getText().toString();
                telefono = editTelefono.getText().toString();
                password = editContra.getText().toString();

                if(!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if(password.length() >= 6){
                        registrarDatosFirebase(nombre, email, telefono, password);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debe completar los campos indicados", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void registrarDatosFirebase(String nombre, String email, String telefono, String password){
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("email", email);
                    map.put("telefono", telefono);
                    map.put("contraseña", password);

                    dataRef.child("Usuario").child(Objects.requireNonNull(fAuth.getCurrentUser()).getUid()).setValue(map);
                }else{
                    Toast.makeText(Registro.this, "No se pudo registrar este usuario", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*private void Registro(Usuario usuario, String key){
        fAuth.createUserWithEmailAndPassword(usuario.getEmail(), key).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = fAuth.getCurrentUser().getUid();
                    dataRef.child("Usuario").child(id).setValue(usuario.Mapping()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                startActivity(new Intent(getApplicationContext(), PaginaEleccion.class));
                                Toast.makeText(getApplicationContext(), "Registro Completado con Exito", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                }
            }
        });
    }*/
}