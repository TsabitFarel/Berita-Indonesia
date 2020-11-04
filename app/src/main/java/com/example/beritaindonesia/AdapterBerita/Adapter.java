package com.example.beritaindonesia.AdapterBerita;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beritaindonesia.Main.DetailBerita;
import com.example.beritaindonesia.POJO.ArticlesItem;
import com.example.beritaindonesia.R;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<ArticlesItem> articles;

    public Adapter(Context context, List<ArticlesItem> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ArticlesItem dataBerita = articles.get(position);
        holder.judulBerita.setText(dataBerita.getTitle());
        holder.sumberBerita.setText(dataBerita.getSource().getName());
        holder.tanggalBerita.setText("\u2022"+dateTime(dataBerita.getPublishedAt()));
        String imageUrl = dataBerita.getUrlToImage();
        String getUrl = dataBerita.getUrl();
        Picasso.with(context).load(imageUrl).into(holder.gambarBerita);

        holder.cardviewBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBerita.class);
                intent.putExtra("judul",dataBerita.getTitle());
                intent.putExtra("sumber",dataBerita.getSource().getName());
                intent.putExtra("tanggal",dateTime(dataBerita.getPublishedAt()));
                intent.putExtra("deskripsi",dataBerita.getDescription());
                intent.putExtra("imageUrl",dataBerita.getUrlToImage());
                intent.putExtra("url",dataBerita.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judulBerita, sumberBerita, tanggalBerita;
        ImageView gambarBerita;
        CardView cardviewBerita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judulBerita = itemView.findViewById(R.id.judulBerita);
            sumberBerita = itemView.findViewById(R.id.sumberBerita);
            tanggalBerita = itemView.findViewById(R.id.tanggalBerita);
            gambarBerita = itemView.findViewById(R.id.gambarBerita);
            cardviewBerita = itemView.findViewById(R.id.cardviewBerita);
        }
    }

    public String dateTime(String t) {
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
