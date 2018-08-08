package com.diego.rptest2.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.diego.rptest2.R;
import com.diego.rptest2.models.Song;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> implements MediaController.MediaPlayerControl {
    private ArrayList<Song> songs;
    private Context context;
    private MediaPlayer mediaPlayer;
    private MediaController controller;

    public SongsAdapter(ArrayList<Song> songs,View v) {
        this.songs = songs;
        context = v.getContext();
        controller = new MediaController(context);
        controller.setMediaPlayer(this);
        controller.setEnabled(true);
        controller.setAnchorView(v);
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_rv,parent,false);
        return new SongsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        holder.title.setText(songs.get(position).getTitle());
        holder.artist.setText(songs.get(position).getArtist());
        holder.album.setText(songs.get(position).getAlbum());
    }



    public class SongsViewHolder extends RecyclerView.ViewHolder {
        private TextView title,artist,album;

        public SongsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titulo);
            artist = itemView.findViewById(R.id.artista);
            album = itemView.findViewById(R.id.album);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    mediaPlayer = MediaPlayer.create(context, Uri.parse(songs.get(getAdapterPosition()).getData()));
                    mediaPlayer.start();
                    controller.show(0);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }
}
