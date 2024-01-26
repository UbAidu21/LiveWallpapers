package com.ubaidxdev.live_wallpapers;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;

public class VideoWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new VideoWallpaperEngine();
    }

    private class VideoWallpaperEngine extends Engine
            implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

        private MediaPlayer mediaPlayer;
        private SurfaceHolder surfaceHolder;
        private int videoResourceId;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            surfaceHolder = holder;
            try {
                initializeMediaPlayer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            releaseMediaPlayer();
        }

        private void initializeMediaPlayer() throws IOException {
            if (mediaPlayer != null) {
                releaseMediaPlayer();
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setSurface(surfaceHolder.getSurface());
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);

            // Get the video resource ID from SharedPreferences
            SharedPreferences preferences = getSharedPreferences("WallpaperPrefs", MODE_PRIVATE);
            videoResourceId = preferences.getInt("videoResourceId", 0);

            // Set the data source dynamically based on the resource ID
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
            mediaPlayer.setDataSource(getApplicationContext(), videoUri);
            mediaPlayer.prepareAsync();
        }

        private void releaseMediaPlayer() {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            // Restart the video when it completes
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        }
    }
}
