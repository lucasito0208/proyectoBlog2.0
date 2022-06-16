package com.example.blogfinal20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blogfinal20.Modelos.Usuario;
import com.example.blogfinal20.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Perfil extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference mData;
    private StorageReference myStorage;
    private ActivityResultLauncher<String> mTakePhoto;

    ImageView fotoperfil;
    TextView tvnombre, tvcorreo, tvtelefono;
    Button btnEntrar, btnImagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        fAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        fotoperfil = findViewById(R.id.imageView);
        tvnombre = findViewById(R.id.textoNombre);
        tvcorreo = findViewById(R.id.textoCorreo);
        tvtelefono = findViewById(R.id.textoTelefono);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnImagen = findViewById(R.id.cambiarFoto);

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        fotoperfil.setImageURI(result);
                    }
                }
        );

        myStorage = FirebaseStorage.getInstance().getReference();
        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTakePhoto.launch("image/*");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        StorageReference filePath = myStorage.child("fotos").child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Foto subida satisfactoriamente!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        infoUsu();

        btnEntrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Blog.class);
                startActivity(intent);
            }
        });
    }

    private void infoUsu(){

        String id = fAuth.getCurrentUser().getUid();

        mData.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.child("Usuario").child(id).removeEventListener(this);
                if (dataSnapshot.exists()) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    //tvnombre.
                    tvnombre.setText(Objects.requireNonNull(usuario).getNombre());
                    tvcorreo.setText((dataSnapshot.child("Email").getValue()).toString());
                    tvtelefono.setText(Objects.requireNonNull(usuario).getTelefono());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}