package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class mediaPlayer extends AppCompatActivity {

    MediaPlayer music = new MediaPlayer();
    Button playButton,pauseButton;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String songUrl = getIntent().getStringExtra("song");
        uri = Uri.parse(songUrl);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        playButton = (Button) findViewById(R.id.button_play);
        pauseButton = (Button) findViewById(R.id.button_pause);

        music.setAudioStreamType(AudioManager.STREAM_MUSIC);



        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlay(view,uri);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPause(view);
            }
        });

    }
    public void musicPlay(View view, Uri uri){
        try {
            music.setDataSource(this, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            music.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        music.start();
    }

    public void musicPause(View view){
        music.pause();
    }

}