package com.example.beritaindonesia.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beritaindonesia.API.ApiClient;
import com.example.beritaindonesia.AdapterBerita.Adapter;
import com.example.beritaindonesia.POJO.ArticlesItem;
import com.example.beritaindonesia.POJO.ResponseBerita;
import com.example.beritaindonesia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edtQuery;
    Button btnSearch;
    final String API_KEY = "960dd0a3cbb14fabb0f58c3790b80637";
    Adapter adapter;
    List<ArticlesItem> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.rvBerita);
        edtQuery = findViewById(R.id.edtQuery);
        btnSearch = findViewById(R.id.btnSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = "id";

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                menerimaJson("", country, API_KEY);
            }
        });
        menerimaJson("",country,API_KEY);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtQuery.getText().toString().equals("")) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            menerimaJson(edtQuery.getText().toString(), country, API_KEY);
                        }
                    });
                    menerimaJson(edtQuery.getText().toString(),country,API_KEY);
                    }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            menerimaJson("", country, API_KEY);
                        }
                    });
                    menerimaJson("",country,API_KEY);
                }

            }
        });
    }

    public void  menerimaJson(String query, String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);
        Call<ResponseBerita> call;
        if (!edtQuery.getText().toString().equals("")){
            call= ApiClient.getInstance().getApi().getSpecificData(query, apiKey);
        } else {
            call= ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        }

        call.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
