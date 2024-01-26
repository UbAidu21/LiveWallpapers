package com.ubaidxdev.live_wallpapers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class VideoWallpaperScreen extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private Button applyPaperButton;
    private VideoView videoPreview;
    private int videoResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_wallpaper_screen);

        applyPaperButton = findViewById(R.id.applyPaper);
        videoPreview = findViewById(R.id.videoView3);

        // Get the video resource ID from the intent
        videoResourceId = getIntent().getIntExtra("videoResourceId", 0);

        // Set up the video preview
        setupVideoPreview();

        // Set click listener for Apply Wallpaper button
        applyPaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Apply the video as live wallpaper
                applyVideoAsLiveWallpaper(videoResourceId);
            }
        });
    }

    private void setupVideoPreview() {
        videoPreview.getHolder().addCallback(this);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
        videoPreview.setVideoURI(videoUri);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        videoPreview.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Surface changed callback
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        videoPreview.stopPlayback();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // VideoView prepared callback
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoPreview.seekTo(0);
        videoPreview.start();
    }

    private void applyVideoAsLiveWallpaper(int videoResourceId) {
        // Save the videoResourceId in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("WallpaperPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("videoResourceId", videoResourceId);
        editor.apply();

        // Start the VideoWallpaperService
        Intent intent = new Intent(this, VideoWallpaperService.class);
        startService(intent);

        showToast("Video applied as live wallpaper");
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
