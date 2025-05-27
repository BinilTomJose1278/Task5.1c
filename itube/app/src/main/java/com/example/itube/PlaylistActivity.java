package com.example.itube;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private List<PlaylistItem> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        playlist = loadPlaylist();
        adapter = new PlaylistAdapter(playlist);
        recyclerView.setAdapter(adapter);
    }

    private List<PlaylistItem> loadPlaylist() {
        SharedPreferences prefs = getSharedPreferences("playlist_prefs", MODE_PRIVATE);
        String json = prefs.getString("playlist", null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<PlaylistItem>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    private static class PlaylistItem {
        String videoId;
        String title;

        PlaylistItem(String videoId, String title) {
            this.videoId = videoId;
            this.title = title;
        }
    }

    private class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
        private List<PlaylistItem> items;

        PlaylistAdapter(List<PlaylistItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playlist, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PlaylistItem item = items.get(position);
            holder.tvTitle.setText(item.title);
            holder.btnPlay.setOnClickListener(v -> {
                Intent intent = new Intent(PlaylistActivity.this, VideoPlayerActivity.class);
                intent.putExtra("video_id", item.videoId);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            MaterialButton btnPlay;

            ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                btnPlay = itemView.findViewById(R.id.btnPlay);
            }
        }
    }
}