package com.example.newsapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.models.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        TextView title = view.findViewById(R.id.detailTitle);
        TextView desc = view.findViewById(R.id.detailDesc);
        ImageView image = view.findViewById(R.id.detailImage);
        RecyclerView relatedRecycler = view.findViewById(R.id.relatedRecycler);


        Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getString("title"));
            desc.setText(args.getString("desc"));
            image.setImageResource(args.getInt("imgRes"));
        }


        List<NewsItem> relatedStories = new ArrayList<>();
        relatedStories.add(new NewsItem("AI in HealthTech", R.drawable.health, "AI enables early detection in hospitals."));
        relatedStories.add(new NewsItem("SpaceX Starship Test", R.drawable.spacex, "SpaceX conducts successful orbital test."));
        relatedStories.add(new NewsItem("5G+AI Future", R.drawable.tech, "Next-gen connectivity boosts edge computing."));


        relatedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        NewsAdapter relatedAdapter = new NewsAdapter(relatedStories, item -> {
            // Optional: Handle clicks on related stories
            Bundle newBundle = new Bundle();
            newBundle.putString("title", item.getTitle());
            newBundle.putString("desc", item.getDescription());
            newBundle.putInt("imgRes", item.getImageResId());

            NewsDetailFragment detailFragment = new NewsDetailFragment();
            detailFragment.setArguments(newBundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });

        relatedRecycler.setAdapter(relatedAdapter);

        return view;
    }
}
