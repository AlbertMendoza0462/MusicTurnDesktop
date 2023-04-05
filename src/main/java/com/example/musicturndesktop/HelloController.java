package com.example.musicturndesktop;

import com.example.musicturndesktop.Models.Api;
import com.example.musicturndesktop.Models.Cancion;
import com.example.musicturndesktop.Models.Conexion;
import com.example.musicturndesktop.Models.Reproductor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController implements Initializable {
    @FXML
    private VBox VboxColaCanciones;

    @FXML
    private Label enCurso;

    @FXML
    private Label btnCerrar;

    @FXML
    private Label btnMinimizar;

    @FXML
    private Label btnRcargarLista;

    @FXML
    private ImageView btnPausarReproducir;

    @FXML
    private ImageView btnSiguiente;

    @FXML
    private AnchorPane panelBarra;

    public ArrayList<Cancion> colaCanciones = new ArrayList<>();

    public ArrayList<Cancion> listaCanciones = new ArrayList<>();

    private Reproductor reproductor = new Reproductor();

    public Api api = new Api();

    public Cancion cancionActual = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        permitirMoverVentana(panelBarra);
        api.enviarListaCanciones(getCanciones());
        api.solicitarColaCancionesHilo();
        actualizarCancionActual();
    }

    public void permitirMoverVentana(AnchorPane panel){
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

        panel.setOnMousePressed(e -> {
            Stage stage = (Stage) panel.getScene().getWindow();
            xOffset.set(stage.getX() - e.getScreenX());
            yOffset.set(stage.getY() - e.getScreenY());
        });

        panel.setOnMouseDragged(e -> {
            Stage stage = (Stage) panel.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset.get());
            stage.setY(e.getScreenY() + yOffset.get());
        });
    }

    @FXML
    protected void onBtnRcargarListaClick(){
        api.enviarListaCanciones(getCanciones());
    }
    @FXML
    protected void onBtnPausarReproducirClick(){
        if (reproductor.isReproduciendo){
            pausar();
        } else {
            reproducir();
        }
    }

    @FXML
    protected void onBtnSiguienteClick(){
        if (!reproducirSiguiente()){
            generarNuevaCancion();
        }
    }

    public boolean reproducirSiguiente(){
        try {
            boolean estadoActual = reproductor.isReproduciendo;
            pausar();
            if(colaCanciones.size() > 1) {
                terminarCancion();
                cancionActual = colaCanciones.get(0);
                reproductor.CargarCancion(colaCanciones.get(0).nombre);
                if(estadoActual) reproducir();
                return true;
            } else {
                //reproductor = new Reproductor();
                terminarCancion();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void onBtnAnteriorClick(){
        try {
            boolean estadoActual = reproductor.isReproduciendo;
            reproductor.Stop();
            if(estadoActual) reproducir();
        }catch (Exception e){}
    }

    @FXML
    protected void onBtnCerrarCliick() {
        HelloApplication.app.close();
    }

    @FXML
    protected void onBtnMinimizarCliick() {
        HelloApplication.app.setIconified(true);
    }

    public void terminarCancion(){
        try{
            api.eliminarCancionDeColaHilo();
            colaCanciones.remove(0);
            recargarApp(colaCanciones);
        }catch (Exception e){}
    }

    public void pausar(){
        try {
            reproductor.Pausa();
            btnPausarReproducir.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/play.png"))));
        }catch (Exception e){}
    }

    public void reproducir(){
        try {
            if (cancionActual == null)
                generarNuevaCancion();
            reproductor.Play();
            btnPausarReproducir.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/pause.png"))));
        }catch (Exception e){}
    }

    public void recargarApp(ArrayList<Cancion> nuevaCola){
        colaCanciones = nuevaCola;
        VboxColaCanciones.getChildren().clear();
        if (nuevaCola.size() > 0) {
            for (Cancion c : nuevaCola) {
                if (c != nuevaCola.get(0))
                    agregarCancion(c);
            }
        }

        actualizarCancionActual();
    }

    public void actualizarCancionActual(){
        if (colaCanciones.size() > 0) {
            Cancion primeraCancion = colaCanciones.get(0);
            enCurso.setText(primeraCancion.nombre.substring(0, primeraCancion.nombre.length() - 4));

            if (cancionActual == null){
                reproductor.CargarCancion(primeraCancion.nombre);
            }
            cancionActual = primeraCancion;
        } else {
            cancionActual = null;
            enCurso.setText("Ninguna canción para reproducir...");
        }
    }

    @FXML
    public void agregarCancion(Cancion cancion) {
        VBox item = new VBox();
        Label text = new Label(cancion.nombre);
        item.getChildren().add(text);
        VboxColaCanciones.getChildren().add(item);
    }

    public ArrayList<Cancion> getCanciones() {
        File[] files = new File("C:/Users/alber/Music/Birthday").listFiles();
        ArrayList<Cancion> lista = new ArrayList<>();

        int i = 1;
        for (File file : files) {
            if (file.isFile() && file.getName().substring(file.getName().length()-4, file.getName().length()).equals(".mp3")) {
                Cancion cancion = new Cancion(i, file.getName());
                lista.add(cancion);
                i++;
            }
        }

        listaCanciones = lista;
        return lista;
    }

    public void generarNuevaCancion(){
        Random rdm = new Random();
        int indice = rdm.nextInt(listaCanciones.size());
        Cancion elegida = listaCanciones.get(indice);
        api.enviarCancionAColaHilo(elegida);
        colaCanciones.add(elegida);
        //reproductor.CargarCancion(elegida.nombre);
        recargarApp(colaCanciones);
        reproducir();
    }
}