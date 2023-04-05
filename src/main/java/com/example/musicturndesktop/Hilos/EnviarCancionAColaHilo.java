package com.example.musicturndesktop.Hilos;

import com.example.musicturndesktop.Models.Cancion;
import com.example.musicturndesktop.Models.Conexion;

import java.util.ArrayList;

public class EnviarCancionAColaHilo implements Runnable {
    private Conexion conn;
    private Cancion cancion;
    public boolean sePudo = false;

    public EnviarCancionAColaHilo(Conexion c, Cancion can){
        conn = c;
        cancion = can;
    }

    @Override
    public void run() {
        while (!sePudo){
            sePudo = conn.solicitudPOST("canciones/cola", cancion).equals("true");
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
