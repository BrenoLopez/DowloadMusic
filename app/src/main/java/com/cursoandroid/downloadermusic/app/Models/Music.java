package com.cursoandroid.downloadermusic.app.Models;

public class Music {

    private String title;
    private String image;
    private String timestamp;
    private String author;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    private String videoId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", author='" + author + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }


    public Music(String title, String image, String timestamp, String author, String videoId) {
        this.title = title;
        this.image = image;
        this.timestamp = timestamp;
        this.author = author;
        this.videoId = videoId;
    }
}
