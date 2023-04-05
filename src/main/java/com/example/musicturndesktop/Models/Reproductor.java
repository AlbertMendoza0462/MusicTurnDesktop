package com.example.musicturndesktop.Models;

import com.example.musicturndesktop.HelloApplication;
import com.example.musicturndesktop.HelloController;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.util.Random;

public class Reproductor {
    private MediaPlayer player;
    private String path = "C:/Users/alber/Music/Birthday";
    public boolean isReproduciendo = false;

    public void CargarCancion(String nombre) {
        Media pick = new Media(new File(path + "/" + nombre).toURI().toString());
        player = new MediaPlayer(pick);
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (!HelloApplication.helloController.reproducirSiguiente()){
                    HelloApplication.helloController.generarNuevaCancion();
                }
            }
        });

    }

    public void Play() {
        player.play();
        isReproduciendo = true;
    }

    public void Pausa() {
        player.pause();
        isReproduciendo = false;
    }

    public void Stop() {
        player.stop();
    }
}
