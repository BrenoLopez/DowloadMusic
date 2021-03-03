package com.cursoandroid.downloadermusic.app.services;



import android.os.AsyncTask;

import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.Playlist;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Http extends AsyncTask<Void, Void, List<?>> {
    private String music;
    private String type;
    public Http (String music,String type){
        this.music = music;
        this.type = type;
    }
    protected List<?> doInBackground(Void... voids) {
        StringBuilder response = new StringBuilder();
        try {
            String url = "http://localhost:3333/list-downloads?music="+this.music+"&type="+this.type;
            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(apiUrl.openStream());
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            System.out.println("response"+response);
        }catch ( MalformedURLException error){
            error.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (type){
            case "video":
                List<Music> musicArray = Arrays.asList(new Gson().fromJson(response.toString(), Music[].class));
                return musicArray;
            case "playlist":
                List<Playlist> playlistArray = Arrays.asList(new Gson().fromJson(response.toString(), Playlist[].class));
                return playlistArray;
            default: return new ArrayList<>();
        }
    }

}
