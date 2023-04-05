package com.example.musicturndesktop.Hilos;

import com.example.musicturndesktop.Models.Cancion;
import com.example.musicturndesktop.Models.Conexion;

import java.util.ArrayList;

public class EnviarListaCancionesHilo implements Runnable {
    private Conexion conn;
    private ArrayList<Cancion> lista;
    public boolean sePudo = false;

    public EnviarListaCancionesHilo(Conexion c, ArrayList<Cancion> l){
        conn = c;
        lista = l;
    }

    @Override
    public void run() {
        while (!sePudo){
            sePudo = conn.solicitudPOST("canciones/insertarLista", lista).equals("true");
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
