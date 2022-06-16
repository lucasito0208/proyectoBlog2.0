package com.example.blogfinal20.Modelos;

public class Publicacion {
    private String titulo;
    private String publicacion;
    private Usuario autor;

    public Publicacion(String titulo, String publicacion, Usuario autor) {
        this.titulo = titulo;
        this.publicacion = publicacion;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
