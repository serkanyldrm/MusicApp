package com.example.myapplication;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        View view = inflater.inflate(R.layout.song_row_item,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Song song = songs.get(position);
        SongViewHolder viewHolder =(SongViewHolder) holder;

        viewHolder.titleHolder.setText(song.getName());
        viewHolder.numberHolder.setText(String.valueOf(position+1));
        viewHolder.durationHolder.setText(getDuration(song.getDuration()));
        viewHolder.artistHolder.setText(song.getArtist());
        viewHolder.albumartHolder.setImageResource(R.drawable.default_albumart);


        viewHolder.rowItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri songUri = song.uri;
                Intent intent = new Intent(view.getContext(),mediaPlayer.class);
                intent.putExtra("song",songUri.toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout rowItemLayout;
        ImageView albumartHolder;
        TextView numberHolder, titleHolder, durationHolder, artistHolder;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItemLayout = itemView.findViewById(R.id.rowItemLayout);
            albumartHolder = itemView.findViewById(R.id.albumart);
            numberHolder = itemView.findViewById(R.id.number);
            titleHolder = itemView.findViewById(R.id.title);
            durationHolder = itemView.findViewById(R.id.duration);
            artistHolder = itemView.findViewById(R.id.artist);
        }
    }
    private String getDuration(int totalDuration) {
        String totalDurationText;
        int hrs = totalDuration / (1000 * 60 * 60);
        int min = (totalDuration % (1000 * 60 * 60)) / (1000 * 60);
        int sec = (((totalDuration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        if (hrs < 1) {
            totalDurationText = String.format("%02d:%02d", min, sec);
        } else {
            totalDurationText = String.format("%1d:%02d:%02d", hrs, min, sec);
        }
        return totalDurationText;
    }


}




