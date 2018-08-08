package com.diego.rptest2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.diego.rptest2.adapters.SongsAdapter;
import com.diego.rptest2.models.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Song> songs;
    private SongsAdapter adapter;
    private final int CODE_PERMISSION=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_rv);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_PERMISSION);
        }else
            checkOlderVersions(Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    private void instanceRecycler() {
       adapter = new SongsAdapter(songs,recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);

    }

    private ArrayList<Song> getSongList() {
        ArrayList<Song> aux = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri,null,null,null,null);

        if(cursor!=null&&cursor.moveToFirst()){
            int titleColum = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColum = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int dataColum = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

         do{
             String title = cursor.getString(titleColum);
             String artist = cursor.getString(artistColum);
             String album = cursor.getString(albumColum);
             String data = cursor.getString(dataColum);

             aux.add(new Song(title,artist,album,data));

         }while(cursor.moveToNext());

            Collections.sort(aux, new Comparator<Song>() {
                @Override
                public int compare(Song a, Song b) {
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
          }
        return aux;

    }
    //Newer Versions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODE_PERMISSION:
                if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    songs = getSongList();
                    instanceRecycler();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //OlderVersions
    public boolean checkOlderVersions(String permission){
        int prmn = ContextCompat.checkSelfPermission(MainActivity.this , permission);
        return prmn == PackageManager.PERMISSION_GRANTED;
    }

}
