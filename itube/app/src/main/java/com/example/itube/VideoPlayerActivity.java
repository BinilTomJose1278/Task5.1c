package com.example.itube;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {
    private WebView webView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Get video ID from intent
        videoId = getIntent().getStringExtra("video_id");
        if (videoId == null) {
            Toast.makeText(this, "Error: No video ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize WebView
        webView = findViewById(R.id.webView);
        setupWebView();
        loadVideo();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void loadVideo() {
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
        String html = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>body { margin: 0; } .video-container { position: relative; padding-bottom: 56.25%; height: 0; overflow: hidden; max-width: 100%; } " +
                ".video-container iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }</style>" +
                "</head><body>" +
                "<div class=\"video-container\">" +
                "<iframe src=\"" + embedUrl + "\" frameborder=\"0\" allowfullscreen></iframe>" +
                "</div></body></html>";

        webView.loadData(html, "text/html", "utf-8");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}