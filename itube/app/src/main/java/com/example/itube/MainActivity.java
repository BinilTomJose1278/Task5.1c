package com.example.itube;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText etYoutubeUrl;
    private MaterialButton btnPlay;
    private MaterialButton btnAddToPlaylist;
    private MaterialButton btnMyPlaylist;
    private List<PlaylistItem> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etYoutubeUrl = findViewById(R.id.etYoutubeUrl);
        btnPlay = findViewById(R.id.btnPlay);
        btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnMyPlaylist = findViewById(R.id.btnMyPlaylist);

        // Load playlist from SharedPreferences
        loadPlaylist();

        // Set click listeners
        btnPlay.setOnClickListener(v -> handlePlay());
        btnAddToPlaylist.setOnClickListener(v -> handleAddToPlaylist());
        btnMyPlaylist.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
            startActivity(intent);
        });
    }

    private void handlePlay() {
        String url = etYoutubeUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoId = extractVideoId(url);
        if (videoId == null) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
        intent.putExtra("video_id", videoId);
        startActivity(intent);
    }

    private void handleAddToPlaylist() {
        String url = etYoutubeUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoId = extractVideoId(url);
        if (videoId == null) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add to playlist
        PlaylistItem item = new PlaylistItem(videoId, "YouTube Video"); // You can add title extraction later
        playlist.add(item);
        savePlaylist();

        Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show();
        etYoutubeUrl.setText("");
    }

    private String extractVideoId(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        // Patterns to match various YouTube URL formats
        String[] patterns = {
                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*",
                "(?<=shorts/)[^#\\&\\?\\n]*"  // For YouTube Shorts
        };

        for (String pattern : patterns) {
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(url);
            if (matcher.find()) {
                return matcher.group();
            }
        }

        // If the input is just the video ID
        if (url.matches("^[A-Za-z0-9_-]{11}$")) {
            return url;
        }

        return null;
    }

    private void loadPlaylist() {
        SharedPreferences prefs = getSharedPreferences("playlist_prefs", MODE_PRIVATE);
        String json = prefs.getString("playlist", null);
        if (json == null) {
            playlist = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<PlaylistItem>>(){}.getType();
            playlist = new Gson().fromJson(json, type);
        }
    }

    private void savePlaylist() {
        SharedPreferences prefs = getSharedPreferences("playlist_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(playlist);
        editor.putString("playlist", json);
        editor.apply();
    }

    public static class PlaylistItem {
        String videoId;
        String title;

        PlaylistItem(String videoId, String title) {
            this.videoId = videoId;
            this.title = title;
        }
    }
}