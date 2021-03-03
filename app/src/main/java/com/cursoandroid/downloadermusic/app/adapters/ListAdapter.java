package com.cursoandroid.downloadermusic.app.adapters;



import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cursoandroid.downloadermusic.R;
import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.Playlist;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<?> list;
    private String type;
    private Context context;

    public ListAdapter(List<?> list, String type) {
        this.list = list;
        this.type = type;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList;
        switch (this.type){
            case "video":
                itemList = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_adapter_music,parent,false);
                break;
            case "playlist":
                itemList = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_adapter_playlist,parent,false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.type);
        }

        MyViewHolder myViewHolder = new MyViewHolder(itemList);
        this.context = parent.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(type == "video"){
            Music music = (Music) list.get(position);
            holder.author.setText(music.getAuthor());
            Glide.with(context).load(Uri.parse(music.getImage())).into(holder.image);
            holder.title.setText(music.getTitle());
            holder.timestamp.setText(music.getTimestamp());
        }
        else {
            Playlist playlist = (Playlist) list.get(position);
            holder.author.setText(playlist.getAuthor());
            Glide.with(context).load(Uri.parse(playlist.getImage())).into(holder.image);
            holder.title.setText(playlist.getTitle());
            holder.videoCount.setText(playlist.getVideoCount());
        }

    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        TextView timestamp;
        TextView author;
        TextView videoCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if(type == "video"){
                title = itemView.findViewById(R.id.title);
                image = itemView.findViewById(R.id.imageThumb);
                timestamp = itemView.findViewById(R.id.timestamp);
                author = itemView.findViewById(R.id.author);
            }else {
                title = itemView.findViewById(R.id.title);
                image = itemView.findViewById(R.id.imageThumb);
                videoCount = itemView.findViewById(R.id.videoCount);
                author = itemView.findViewById(R.id.author);
            }


        }
    }


}
