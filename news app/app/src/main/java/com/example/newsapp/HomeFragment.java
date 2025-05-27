package com.example.newsapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.models.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<NewsItem> topStories;
    List<NewsItem> newsItems;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // -------- TOP STORIES (Horizontal) --------
        RecyclerView topRecycler = view.findViewById(R.id.topRecycler);
        topRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        topStories = new ArrayList<>();
        topStories.add(new NewsItem(
                "OpenAI Launches GPT-4.5 and 'Operator'",
                R.drawable.chatgpt,
                "OpenAI unveils GPT-4.5 and its automation agent 'Operator', marking a major leap in AI capabilities."
        ));
        topStories.add(new NewsItem(
                "IBM Invests $150B in Quantum and AI",
                R.drawable.ibm,
                "IBM commits $150 billion to boost U.S. quantum computing and AI manufacturing over the next 5 years."
        ));

        NewsAdapter topAdapter = new NewsAdapter(topStories, this::navigateToDetail);
        topRecycler.setAdapter(topAdapter);

        // -------- TECH NEWS (Grid - 2 columns) --------
        RecyclerView newsRecycler = view.findViewById(R.id.newsRecycler);
        newsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        newsItems = new ArrayList<>();
        newsItems.add(new NewsItem(
                "Motorola Razr 2025 Unveiled",
                R.drawable.motorolo,
                "The 2025 Razr lineup features Moto AI, Snapdragon 8 Elite, and up to 1TB of storage."
        ));
        newsItems.add(new NewsItem(
                "CES 2025 Highlights AI in Everything",
                R.drawable.ces,
                "CES 2025 showcases lithium-free batteries, AI PCs, and needle-free health tech."
        ));
        newsItems.add(new NewsItem(
                "Meta's Q1 Revenue Soars to $42.3B",
                R.drawable.meta,
                "Zuckerberg emphasizes Meta’s shift to AI infrastructure in record-breaking quarter."
        ));
        newsItems.add(new NewsItem(
                "TSMC Fuels Taiwan's Tech Boom",
                R.drawable.tsmc,
                "TSMC posts 60% net profit jump as global AI chip demand drives Taiwan's GDP up to 3.6%."
        ));
        newsItems.add(new NewsItem(
                "Apple Unveils Vision Pro 2 with Breakthrough AR Capabilities",
                R.drawable.apple,
                " Apple's second-gen AR headset offers retina-quality visuals, longer battery life, and seamless app integration."
        ));
        newsItems.add(new NewsItem(
                " Google Gemini AI Powers Gmail, Docs, and Search",
                R.drawable.gemini,
                "Google embeds Gemini AI across its ecosystem, enabling real-time smart suggestions and writing assistance."
        ));
        newsItems.add(new NewsItem(
                "NVIDIA Blackwell GPUs Redefine AI Training",
                R.drawable.nvdia,
                "NVIDIA launches its next-gen Blackwell GPU, delivering 2x the performance of H100 for LLMs and deep learning."
        ));

        newsItems.add(new NewsItem(
                "Samsung Galaxy S25 AI Series Sets New Standard",
                R.drawable.samsung,
                "Samsung introduces on-device AI features, real-time call translation, and generative photo editing in the Galaxy S25."
        ));

        NewsAdapter newsAdapter = new NewsAdapter(newsItems, this::navigateToDetail);
        newsRecycler.setAdapter(newsAdapter);

        return view;
    }

    private void navigateToDetail(NewsItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("title", item.getTitle());
        bundle.putString("desc", item.getDescription());
        bundle.putInt("imgRes", item.getImageResId());

        NewsDetailFragment detailFragment = new NewsDetailFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
