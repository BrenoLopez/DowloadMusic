package com.cursoandroid.downloadermusic.app.background;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicBackground extends Service implements  MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnSeekCompleteListener {
    public MediaPlayer music;
    private static final String ACTION_PLAY = "com.example.action.PLAY";

    // Binder given to clients
    private final IBinder binder = new LocalBinder();

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Toast.makeText(getApplicationContext(), (CharSequence) mediaPlayer,Toast.LENGTH_LONG);
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        System.out.println("Terminou"+mediaPlayer);
    }


    public class LocalBinder extends Binder {
        public MusicBackground getService() {
            // Return this instance of LocalService so clients can call public methods
            return MusicBackground.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        String id = data.getString("id");
        try {
            this.startMusic(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Service.START_STICKY;
    }

    public void startMusic(String id) throws IOException {
        music = new MediaPlayer();
        music.setAudioStreamType(AudioManager.STREAM_MUSIC);
        music.setDataSource("http://agendamais.app.br:3333/music-stream/"+id);
        music.setOnPreparedListener(this);
        music.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        music.release();
        music = null;

    }
}
