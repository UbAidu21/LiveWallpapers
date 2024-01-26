package com.ubaidxdev.live_wallpapers;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView wallpaperImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wallpaperImageView = findViewById(R.id.wallpaperImage);

        // Get the image URL from the intent
        String imageUrl = getIntent().getStringExtra("imageUrl");

        if (imageUrl != null) {
            // Load the image from the URL
            new LoadImageTask().execute(imageUrl);
        }
    }

    public void onClickApplyWallpaper(View view) {
        // Called when the "Apply Wallpaper" button is clicked
        applyWallpaper();
    }

    private void applyWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        try {
            // Get the Bitmap from the ImageView
            wallpaperImageView.setDrawingCacheEnabled(true);
            wallpaperImageView.buildDrawingCache();
            wallpaperManager.setBitmap(wallpaperImageView.getDrawingCache());

            // Notify user that the wallpaper has been applied (optional)
            showToast("Wallpaper Applied");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Failed to apply wallpaper");
        } finally {
            wallpaperImageView.setDrawingCacheEnabled(false);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            return downloadImage(imageUrl);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // Set the downloaded image to the ImageView
                wallpaperImageView.setImageBitmap(result);
            } else {
                // Handle the case when the image download fails
                showToast("Failed to download image");
            }
        }

        private Bitmap downloadImage(String imageUrl) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
