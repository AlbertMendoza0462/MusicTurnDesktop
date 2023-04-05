package com.example.musicturndesktop.Hilos;

import com.example.musicturndesktop.HelloApplication;
import com.example.musicturndesktop.HelloController;
import com.example.musicturndesktop.Models.Api;
import com.example.musicturndesktop.Models.Cancion;
import com.example.musicturndesktop.Models.Conexion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class SolicitarColaCancionesHilo implements Runnable {
    private Conexion conn;
    public boolean sePudo = false;

    public SolicitarColaCancionesHilo(Conexion c) {
        conn = c;
    }

    @Override
    public void run() {
        while (true) {
            String res = conn.solicitudGET("canciones/cola");
            ArrayList<Cancion> cola = new ArrayList<Cancion>();

            Gson gson = new Gson();
            try {
                ObjectMapper mapper = new ObjectMapper();
                cola = mapper.readValue(res, new TypeReference<ArrayList<Cancion>>() {});

                sePudo = true;
            } catch (Exception e) {
                sePudo = false;
                e.printStackTrace();
            }

            if (sePudo) {
                ArrayList<Cancion> finalCola = cola;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HelloApplication.helloController.api.recibirColaCanciones(finalCola);
                    }
                });
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}