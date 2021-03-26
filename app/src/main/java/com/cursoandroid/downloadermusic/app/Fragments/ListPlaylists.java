package com.cursoandroid.downloadermusic.app.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cursoandroid.downloadermusic.R;
import com.cursoandroid.downloadermusic.app.Models.Music;
import com.cursoandroid.downloadermusic.app.Models.Playlist;
import com.cursoandroid.downloadermusic.app.activities.MainActivity;
import com.cursoandroid.downloadermusic.app.adapters.ListAdapter;
import com.cursoandroid.downloadermusic.app.services.Http;
import com.cursoandroid.downloadermusic.app.utils.RecyclerItemOnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPlaylists extends Fragment {
    private static RecyclerView playlistList;
    private static List<Playlist> listPlaylist = new ArrayList<>();


    public ListPlaylists() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_list_playlists, container, false);
        playlistList= rootView.findViewById(R.id.playlistList);
        playlistList.setHasFixedSize(true);
        playlistList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        playlistList.addOnItemTouchListener(new RecyclerItemOnClickListener(getActivity().getApplicationContext(), playlistList, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Playlist playlist =  listPlaylist.get(position);
                Toast.makeText(getActivity().getApplicationContext(),playlist.getListId(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
        ListAdapter listAdapter = new ListAdapter(listPlaylist,"playlist");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        playlistList.setLayoutManager(layoutManager);
        playlistList.setAdapter(listAdapter);
        return rootView;
    }
    public void receivePlaylist(String music, String type, Context context) {
        if(listPlaylist.size() > 0){
            listPlaylist.clear();
        }
        playlistList.getAdapter().notifyDataSetChanged();

        try {
            List<?> playlists = new Http(music, type,context).execute().get();
            for (Object item :  playlists) {
                listPlaylist.add((Playlist) item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
