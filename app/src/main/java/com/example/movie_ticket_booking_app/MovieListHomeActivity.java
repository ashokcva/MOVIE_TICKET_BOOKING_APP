package com.example.movie_ticket_booking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListHomeActivity extends AppCompatActivity implements MovieAdapter.onMovieClickListener {

    public static String BASEURL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "d518bb45a768d3b7662b74449edf5a51";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "now_playing";

    RecyclerView recyclerView;
    private List<MovieResults.ResultsBean> movielist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_home);

        recyclerView = findViewById(R.id.mvlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new MovieAdapter(this,movielist,this));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieResults> call = apiInterface.getMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                movielist = results.getResults();
                setAdapterr();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapterr() {
        recyclerView.setAdapter(new MovieAdapter(this,movielist,this));
    }

    @Override
    public void onMovieCardClicked(int position) {
        //Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();

        MovieResults.ResultsBean selectedMovie = movielist.get(position);
        Intent intent = new Intent(this,SelectSeatsActivity.class);

        intent.putExtra("moviename",selectedMovie.getTitle());
        startActivity(intent);
    }
}