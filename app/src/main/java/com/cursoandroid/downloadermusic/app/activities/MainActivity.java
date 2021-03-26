package com.cursoandroid.downloadermusic.app.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cursoandroid.downloadermusic.R;
import com.cursoandroid.downloadermusic.app.Fragments.ListMusics;
import com.cursoandroid.downloadermusic.app.Fragments.ListPlaylists;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


public class MainActivity extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private SearchView searchView = null;
    private static String option = "video";
    private static String search;
    private static int state = 0;
    private static int position = 0;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smartTabLayout = findViewById(R.id.tabNavigator);
        viewPager = findViewById(R.id.viewpager);
        getSupportActionBar().setElevation(0);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Lista de Músicas", ListMusics.class)
                .add("Lista de Playlists", ListPlaylists.class)
                .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int positionSelected) {
                position = positionSelected;
            }

            @Override
            public void onPageScrollStateChanged(int stateScroll) {
                state = stateScroll;
                handleRequest(position,stateScroll);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

         searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search = query;
                handleRequest(position,state);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    private void handleRequest(int position,int state){
        if(search != null && state == 0 ){
            switch (position){
                case 0:
                    option = "video";
                    new ListMusics().receivedMusics(search,option,this);
                    break;
                case 1:
                    option = "playlist";
                    new ListPlaylists().receivePlaylist(search,option,this);
                    break;
            }
        }

    }
}
