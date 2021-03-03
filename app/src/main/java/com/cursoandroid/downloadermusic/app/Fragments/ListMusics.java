package com.cursoandroid.downloadermusic.app.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.cursoandroid.downloadermusic.app.adapters.ListAdapter;
import com.cursoandroid.downloadermusic.app.services.Http;
import com.cursoandroid.downloadermusic.app.utils.RecyclerItemOnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMusics extends Fragment {
    private static RecyclerView musicList;
    private static List<Music> listMusic = new ArrayList<>();
    private AdapterView.OnItemClickListener mListener;
    GestureDetector mGestureDetector;

    public ListMusics() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_musics, container, false);
        musicList= rootView.findViewById(R.id.musicList);
        musicList.setHasFixedSize(true);
        musicList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        musicList.addOnItemTouchListener(new RecyclerItemOnClickListener(getActivity().getApplicationContext(), musicList, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              Music music =  listMusic.get(position);
                Toast.makeText(getActivity().getApplicationContext(),music.getVideoId(),Toast.LENGTH_LONG).show();
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
    public static void receivedMusics(String music, String type) {
        if(listMusic.size() > 0){
            listMusic.clear();
        }
        musicList.getAdapter().notifyDataSetChanged();

        try {
            List<?> musics = new Http(music, type).execute().get();
            System.out.println(musics);
                for (Object item :  musics) {
                    listMusic.add((Music) item);
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
