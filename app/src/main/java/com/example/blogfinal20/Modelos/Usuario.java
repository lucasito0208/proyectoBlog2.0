package com.example.blogfinal20.Modelos;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

        private String nombre;
        private String email;
        private String telefono;
        private String contraseña;

    public Usuario(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario() {
    }

    @Exclude
    public Map<String, Object> Mapping() {

        HashMap<String, Object> resultado = new HashMap<>();

        resultado.put("Nombre", nombre);
        resultado.put("Email", email);
        resultado.put("Telefono", telefono);
        return resultado;
    }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getContraseña() {
            return contraseña;
        }

        public void setContraseña(String contraseña) {
            this.contraseña = contraseña;
        }
    }




