package com.cursoandroid.downloadermusic.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cursoandroid.downloadermusic.R;
import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.PayloadMusicPlayer;
import com.cursoandroid.downloadermusic.app.background.MusicBackground;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends AppCompatActivity {
    private ImageView musicImage;
    private TextView musicTitle;
    private ImageButton playStopButton;
    private PayloadMusicPlayer payloadMusicPlayer;
    private Music currentMusic;
    private MusicBackground mService;
    private SeekBar progressBar;
    private Handler mHandler;
    private Runnable mRunnable;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MusicBackground.LocalBinder binder = (MusicBackground.LocalBinder) service;
            mService = binder.getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        //start music
        Bundle data = getIntent().getExtras();
        payloadMusicPlayer = (PayloadMusicPlayer) data.getSerializable("payload");
        startMusic(payloadMusicPlayer.getCurrentMusic().getVideoId());
        Intent intent = new Intent(this, MusicBackground.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        currentMusic = payloadMusicPlayer.getCurrentMusic();

        musicImage = findViewById(R.id.musicImage);
        musicTitle = findViewById(R.id.musicTitle);
        playStopButton = findViewById(R.id.musicPlay);
        progressBar = findViewById(R.id.progressBar);
        playStopButton.setBackgroundResource(R.drawable.pause_icon);
        Glide.with(getApplicationContext()).load(Uri.parse(payloadMusicPlayer.getCurrentMusic().getImage())).into(musicImage);
        musicTitle.setText(payloadMusicPlayer.getCurrentMusic().getTitle());
//        mHandler = new Handler();
//        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(mService.music!=null && b){
//                    mService.music.seekTo(i*1000);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }


    public void execAndPauseSound(View view){
        if(mService.music.isPlaying()){
            mService.music.pause();
            playStopButton.setBackgroundResource(R.drawable.play_icon);
//            initializeSeekBar();
        }
        else{
            mService.music.start();
            playStopButton.setBackgroundResource(R.drawable.pause_icon);
//            if(mHandler!=null){
//                mHandler.removeCallbacks(mRunnable);
//            }
        }
    }
    public void backPage(View view){
        mService.music.release();
        mService.music = null;
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void nextMusic(View view) {
        mService.music.stop();
        mService.music.release();
        mService.music = null;
        int id = currentMusic.getId();
        final Music nextMusic;
        if(id != payloadMusicPlayer.getListMusic().size()-1){
            id = id+1;
            nextMusic = payloadMusicPlayer.getListMusic().get(id);
        }
        else {
            id = payloadMusicPlayer.getListMusic().size()-1;
            nextMusic = payloadMusicPlayer.getListMusic().get(id);
            Toast.makeText(getApplicationContext(),"Última música da lista",Toast.LENGTH_LONG);
        }
        currentMusic = new Music(id,nextMusic.getTitle(), nextMusic.getImage(), nextMusic.getTimestamp(), nextMusic.getAuthor(), nextMusic.getVideoId());
//        set new image
        Glide.with(getApplicationContext()).load(Uri.parse(nextMusic.getImage())).into(musicImage);
//        set new title
        musicTitle.setText(nextMusic.getTitle());
            this.startMusic(nextMusic.getVideoId());
    }

    public void previousMusic(View view) throws IOException {
        mService.music.stop();
        mService.music.release();
        mService.music = null;
        int id = currentMusic.getId();
        Music previousMusic ;

        if(currentMusic.getId() == 0){
            previousMusic = payloadMusicPlayer.getListMusic().get(id);
            Toast.makeText(getApplicationContext(),"Esta é a primeira música",Toast.LENGTH_LONG);
        }
        else {
            previousMusic = payloadMusicPlayer.getListMusic().get(id-1);
            id = id-1;
        }
     currentMusic = new Music(id,previousMusic.getTitle(), previousMusic.getImage(), previousMusic.getTimestamp(), previousMusic.getAuthor(), previousMusic.getVideoId());
        //set new image
        Glide.with(getApplicationContext()).load(Uri.parse(previousMusic.getImage())).into(musicImage);
        //set new title
        musicTitle.setText(previousMusic.getTitle());
        this.startMusic(previousMusic.getVideoId());
    }

    public void startMusic(String id){
        Intent intent = new Intent(getApplicationContext(), MusicBackground.class);
        intent.putExtra("id", id);
        startService(intent);
    }
//    protected void getAudioStats(){
//        int duration  = mService.music.getDuration()/1000; // In milliseconds
//        int due = ( mService.music.getDuration() -  mService.music.getCurrentPosition())/1000;
//        int pass = duration - due;
//
//        mPass.setText("" + pass + " seconds");
//        mDuration.setText("" + duration + " seconds");
//        mDue.setText("" + due + " seconds");
//    }
//    protected void initializeSeekBar(){
//        progressBar.setMax(mService.music.getDuration()/1000);
//
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if(mService.music != null){
//                    int mCurrentPosition = mService.music.getCurrentPosition()/1000; // In milliseconds
//                    progressBar.setProgress(mCurrentPosition);
//                    getAudioStats();
//
//                }
//                mHandler.postDelayed(mRunnable,1000);
//
//
//            }
//        };
//        mHandler.postDelayed(mRunnable,1000);
//    }

}