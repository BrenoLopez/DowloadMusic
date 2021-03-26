package com.cursoandroid.downloadermusic.app.Models;

import java.io.Serializable;
import java.util.List;

public class PayloadMusicPlayer implements Serializable {

    private Music currentMusic;
    private List<Music> listMusic;

    public PayloadMusicPlayer(Music currentMusic, List<Music> listMusic) {
        this.currentMusic = currentMusic;
        this.listMusic = listMusic;
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }

    public List<Music> getListMusic() {
        return listMusic;
    }

    public void setListMusic(List<Music> listMusic) {
        this.listMusic = listMusic;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "currentMusic=" + currentMusic +
                ", listMusic=" + listMusic +
                '}';
    }
}