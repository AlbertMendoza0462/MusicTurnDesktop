package com.example.musicturndesktop.Models;

import com.example.musicturndesktop.HelloApplication;
import com.example.musicturndesktop.HelloController;
import com.example.musicturndesktop.Hilos.EliminarCancionDeColaHilo;
import com.example.musicturndesktop.Hilos.EnviarCancionAColaHilo;
import com.example.musicturndesktop.Hilos.EnviarListaCancionesHilo;
import com.example.musicturndesktop.Hilos.SolicitarColaCancionesHilo;

import java.util.ArrayList;

public class Api {
    private Conexion conn = new Conexion();

    public Conexion getConn() {
        return conn;
    }

    public void enviarListaCanciones(ArrayList<Cancion> lista){
        EnviarListaCancionesHilo hilo = new EnviarListaCancionesHilo(conn, lista);
        Thread thread = new Thread(hilo);
        thread.setDaemon(true);
        thread.start();
    }

    public void solicitarColaCancionesHilo(){
        SolicitarColaCancionesHilo hilo = new SolicitarColaCancionesHilo(conn);
        Thread thread = new Thread(hilo);
        thread.setDaemon(true);
        thread.start();
    }

    public void recibirColaCanciones(ArrayList<Cancion> lista){
        HelloApplication.helloController.recargarApp(lista);
    }

    public void enviarCancionAColaHilo(Cancion cancion){
        EnviarCancionAColaHilo hilo = new EnviarCancionAColaHilo(conn, cancion);
        Thread thread = new Thread(hilo);
        thread.setDaemon(true);
        thread.start();
    }

    public void eliminarCancionDeColaHilo(){
        EliminarCancionDeColaHilo hilo = new EliminarCancionDeColaHilo(conn);
        Thread thread = new Thread(hilo);
        thread.setDaemon(true);
        thread.start();
    }
}
