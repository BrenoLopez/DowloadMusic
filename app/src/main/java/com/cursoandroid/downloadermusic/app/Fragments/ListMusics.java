package com.cursoandroid.downloadermusic.app.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cursoandroid.downloadermusic.R;
import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.PayloadMusicPlayer;
import com.cursoandroid.downloadermusic.app.activities.MainActivity;
import com.cursoandroid.downloadermusic.app.activities.MusicPlayer;
import com.cursoandroid.downloadermusic.app.adapters.ListAdapter;
import com.cursoandroid.downloadermusic.app.services.Http;
import com.cursoandroid.downloadermusic.app.utils.RecyclerItemOnClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */


public class ListMusics extends Fragment implements Serializable{
    private static RecyclerView musicList;
    private static List<Music> listMusic = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_musics, container, false);
        musicList= rootView.findViewById(R.id.musicList);
        musicList.setHasFixedSize(true);
        musicList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        musicList.addOnItemTouchListener(new RecyclerItemOnClickListener(getActivity().getApplicationContext(), musicList, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              final Music music =  listMusic.get(position);
              final PayloadMusicPlayer payload = new PayloadMusicPlayer(music,listMusic);
//              Toast.makeText(getActivity().getApplicationContext(),music.getVideoId(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), MusicPlayer.class);
                intent.putExtra("payload", payload);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        ListAdapter listAdapter = new ListAdapter(listMusic,"video");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        musicList.setLayoutManager(layoutManager);
        musicList.setAdapter(listAdapter);
        return rootView;
    }
    public void receivedMusics(String music, String type, Context context) {
        if(listMusic.size() > 0){
            listMusic.clear();
        }
        musicList.getAdapter().notifyDataSetChanged();
        try {
            List<?> musics = new Http(music, type,context).execute().get();
            int count = 0;
                for (Object item :  musics) {
                    Music itemMusic = (Music) item;
                    listMusic.add(new Music(count,itemMusic.getTitle(),itemMusic.getImage(),itemMusic.getTimestamp(),itemMusic.getAuthor(),itemMusic.getVideoId()));
                    count++;
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
