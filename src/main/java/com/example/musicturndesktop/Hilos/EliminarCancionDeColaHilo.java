package com.example.musicturndesktop.Hilos;

import com.example.musicturndesktop.Models.Cancion;
import com.example.musicturndesktop.Models.Conexion;

public class EliminarCancionDeColaHilo implements Runnable {
    private Conexion conn;
    private int id;
    public boolean sePudo = false;

    public EliminarCancionDeColaHilo(Conexion c){
        conn = c;
    }

    @Override
    public void run() {
        while (!sePudo){
            sePudo = conn.solicitudDELETE("canciones/cola/primera").equals("true");
            if (!sePudo){
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
