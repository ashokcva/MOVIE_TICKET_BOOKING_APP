package com.example.movie_ticket_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void navMovieListHomePage(View v)
    {
        Intent intent = new Intent(this,MovieListHomeActivity.class);
        startActivity(intent);
    }
}