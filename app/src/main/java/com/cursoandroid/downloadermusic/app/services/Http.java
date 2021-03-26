package com.cursoandroid.downloadermusic.app.services;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.Playlist;
import com.cursoandroid.downloadermusic.app.activities.MainActivity;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Http extends AsyncTask<Void, Void, List<?>> {
    private String music;
    private String type;
    private ProgressDialog load;
    private Context context;
    public Http (String music,String type,Context context){
        this.music = music;
        this.type = type;
        this.context = context;
    }

    @Override
    protected void onCancelled(List<?> objects) {
        super.onCancelled(objects);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
                load = new ProgressDialog(context);
                load.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                load.setMessage("Buscando músicas");
                load.show();
    }

    protected List<?> doInBackground(Void... voids) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            Log.i("iniciando", "Buscando stream da musica: " + Thread.currentThread().getName());
            String url = "http://agendamais.app.br:3333/list-downloads?music="+this.music+"&type="+this.type;
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                Scanner scanner = new Scanner(apiUrl.openStream());
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
            }
            else {
                Toast.makeText(context, "Houve um erro no servidor, tente novamente!", Toast.LENGTH_LONG);
            }

        }
        catch ( MalformedURLException error){
            Log.i("AsyncTask", error.getMessage());
//            error.printStackTrace();
        }
        catch (IOException e) {
            Log.i("AsyncTask", e.getMessage());
//            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        switch (type){
            case "video":
                List<Music> musicArray = Arrays.asList(new Gson().fromJson(response.toString(), Music[].class));
                load.dismiss();
                return musicArray;
            case "playlist":
                List<Playlist> playlistArray = Arrays.asList(new Gson().fromJson(response.toString(), Playlist[].class));
                load.dismiss();
                return playlistArray;
            default: return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<?> objects) {
        super.onPostExecute(objects);
    }
}
