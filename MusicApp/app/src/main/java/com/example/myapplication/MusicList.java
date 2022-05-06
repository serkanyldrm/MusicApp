package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MusicList extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;
    RecyclerView recyclerview;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        recyclerview = findViewById(R.id.recyclerview);

        if(ContextCompat.checkSelfPermission(MusicList.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MusicList.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MusicList.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }else{
            getMusic();

        }


    }

    public void getMusic(){
        List<Song> songs = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
        };

        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        try(Cursor songcursor = getContentResolver().query(songUri, projection,null,null,sortOrder)){
            int idColumn = songcursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = songcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = songcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int albumIdColumn = songcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = songcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

            while(songcursor.moveToNext()){
                long id = songcursor.getLong(idColumn);
                String name = songcursor.getString(nameColumn);
                int duration = songcursor.getInt(durationColumn);
                long albumId = songcursor.getLong(albumIdColumn);
                String artist = songcursor.getString(artistColumn);

                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id);

                Uri albumartUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),albumId);

                name = name.substring(0,name.lastIndexOf("."));

                Song song = new Song(id,uri,name,duration,albumId,artist);

                songs.add(song);


            }
            showSongs(songs);
            Toast.makeText(getApplicationContext(),"Number of Songs: "+songs.size(),Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongs(List<Song> songs){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        songAdapter = new SongAdapter(songs);
        recyclerview.setAdapter(songAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MusicList.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                        getMusic();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

}