package com.example.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headlines;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    final String API_KEY = "7278da6f04fb4797bd2c376d8f2e78c7";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String title = (String) getTitle();
        retrieveJson(title, API_KEY);
    }

    public void retrieveJson(String title, String apiKey) {
        retrofit2.Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(title, apiKey);
        call.enqueue(new retrofit2.Callback<Headlines>() {
            @Override
            public void onResponse(retrofit2.Call<Headlines> call, retrofit2.Response<Headlines> response) {
//                if (response.isSuccessful() && response.body().getArticles()!=null)
//                    articles.clear();
//                articles=response.body().getArticles();
                if (response.isSuccessful()) {
                    adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "API not fetching news", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}