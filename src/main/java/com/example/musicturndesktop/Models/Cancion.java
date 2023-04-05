package com.example.musicturndesktop.Models;

public class Cancion {
    public int cancionId;
    public String nombre;

    public Cancion(){
        cancionId = 0;
        nombre = "";
    }

    public Cancion(String nombre){
        cancionId = 0;
        this.nombre = nombre;
    }

    public Cancion(int cancionId, String nombre){
        this.cancionId = cancionId;
        this.nombre = nombre;
    }
}
