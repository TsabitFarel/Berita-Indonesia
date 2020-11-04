package com.example.beritaindonesia.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.beritaindonesia.R;
import com.squareup.picasso.Picasso;

public class DetailBerita extends AppCompatActivity {

    TextView judulBerita, sumberBerita, tanggalBerita, deskripsiBerita;
    ImageView gambarBerita;
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);

        judulBerita = findViewById(R.id.judulBerita);
        sumberBerita = findViewById(R.id.sumberBerita);
        tanggalBerita = findViewById(R.id.tanggalBerita);
        deskripsiBerita = findViewById(R.id.deskripsiBerita);
        gambarBerita = findViewById(R.id.gambarBerita);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.webViewprogressbar);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String judul = intent.getStringExtra("judul");
        String sumber = intent.getStringExtra("sumber");
        String tanggal = intent.getStringExtra("tanggal");
        String deskripsi = intent.getStringExtra("deskripsi");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        judulBerita.setText(judul);
        sumberBerita.setText(sumber);
        tanggalBerita.setText(tanggal);
        deskripsiBerita.setText(deskripsi);

        Picasso.with(DetailBerita.this).load(imageUrl).into(gambarBerita);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if (webView.isShown()) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
