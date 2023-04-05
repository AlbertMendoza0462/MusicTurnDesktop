package com.example.musicturndesktop.Models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class Conexion {
    private String url = "http://albert0462.somee.com/api/";
    private String res = "";
    private Client client;

    public String solicitudGET(String uri){
        try {
            client = ClientBuilder.newClient();
            WebTarget target = client.target(url + uri);
            Invocation.Builder solicitud = target.request();

            solicitud.header(HttpHeaders.ACCEPT, "application/json");
            solicitud.header(HttpHeaders.CONTENT_TYPE, "application/json");
            solicitud.header(HttpHeaders.USER_AGENT, "MusicTurnDesktop");

            Response get = solicitud.get();

            String responseJson = get.readEntity(String.class);
            res = responseJson;

            switch (get.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error get " + uri;
                    break;
            }
        }catch (Exception e){
            res = e.toString();
        }

        return res;
    }

    public String solicitudPOST(String uri, Object data){
        try {
            client = ClientBuilder.newClient();
            WebTarget target = client.target(url + uri);
            Invocation.Builder solicitud = target.request();

            solicitud.header(HttpHeaders.ACCEPT, "application/json");
            solicitud.header(HttpHeaders.CONTENT_TYPE, "application/json");
            solicitud.header(HttpHeaders.USER_AGENT, "MusicTurnDesktop");

            Gson gson = new Gson();
            String jsonString = gson.toJson(data);

            Response post = solicitud.post(Entity.json(jsonString));

            String responseJson = post.readEntity(String.class);
            res = responseJson;

            switch (post.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error post " + uri + responseJson;
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            res = e.toString();
        }

        return res;
    }

    public String solicitudDELETE(String uri){
        try {
            client = ClientBuilder.newClient();

            WebTarget target = client.target(url + uri);

            Invocation.Builder solicitud = target.request();

            solicitud.header(HttpHeaders.ACCEPT, "application/json");
            solicitud.header(HttpHeaders.CONTENT_TYPE, "application/json");
            solicitud.header(HttpHeaders.USER_AGENT, "MusicTurnDesktop");

            Response delete = solicitud.delete();

            String responseJson = delete.readEntity(String.class);
            res = responseJson;

            switch (delete.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error delete " + uri;
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            res = e.toString();
        }

        return res;
    }
}
