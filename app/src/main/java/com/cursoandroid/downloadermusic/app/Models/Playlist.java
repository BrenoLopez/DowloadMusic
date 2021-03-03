package com.cursoandroid.downloadermusic.app.Models;

public class Playlist {
    private String title;
    private String image;
    private String videoCount;
    private String listId;
    private String author;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", videoCount='" + videoCount + '\'' +
                ", listId='" + listId + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Playlist(String title, String image, String videoCount, String listId, String author) {
        this.title = title;
        this.image = image;
        this.videoCount = videoCount;
        this.listId = listId;
        this.author = author;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
