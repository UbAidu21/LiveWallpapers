package com.ubaidxdev.live_wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Add your image URLs to the list dynamically
        images.add("https://cdn.pixabay.com/photo/2023/11/11/08/23/woman-8380758_640.jpg");
        images.add("https://cdn.pixabay.com/photo/2023/06/19/05/53/temple-8073501_640.png");
        images.add("https://cdn.pixabay.com/photo/2023/09/13/07/25/people-8250302_640.jpg");
        images.add("https://cdn.pixabay.com/photo/2023/09/23/15/42/space-8271231_640.png");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new ImageAdapter(images));

    }
    public void onVideosButtonClicked(View view) {
        Intent intent = new Intent(this, VideosScreen.class);
        startActivity(intent);
    }


    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private List<String> imageList;

        public ImageAdapter(List<String> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            String imageUrl = imageList.get(position);
            Log.d("GlideDebug", "Loading image at position: " + position);
            Glide.with(HomeScreen.this).load(imageUrl).placeholder(R.drawable.placeholder).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Start MainActivity and pass the clicked image URL
                    Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                    intent.putExtra("imageUrl", imageUrl);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }


}
