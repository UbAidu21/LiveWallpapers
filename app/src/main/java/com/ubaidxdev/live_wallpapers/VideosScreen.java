package com.ubaidxdev.live_wallpapers;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VideosScreen extends AppCompatActivity {
    List<Integer> videoResourceIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_screen);

        videoResourceIds.add(R.raw.video1);
        videoResourceIds.add(R.raw.video2);
        videoResourceIds.add(R.raw.video3);

        VideoView videoView = findViewById(R.id.videoView);
        VideoView videoView1 = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);

        setVideoProperties(videoView, videoResourceIds.get(0));
        setVideoProperties(videoView1, videoResourceIds.get(1));
        setVideoProperties(videoView2, videoResourceIds.get(2));

        // Set click listeners for VideoViews
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVideoWallpaper(videoResourceIds.get(0));
            }
        });

        videoView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVideoWallpaper(videoResourceIds.get(1));
            }
        });

        videoView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToVideoWallpaper(videoResourceIds.get(2));
            }
        });
    }

    private void navigateToVideoWallpaper(int videoResourceId) {
        Intent intent = new Intent(this, VideoWallpaperScreen.class);
        intent.putExtra("videoResourceId", videoResourceId);
        startActivity(intent);
    }

    private void setVideoProperties(VideoView videoView, int videoResourceId) {
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId));

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Video playback is completed, restart the video
                videoView.seekTo(0);
                videoView.start();
            }
        });

        // Start playing the video
        videoView.start();
    }
}
