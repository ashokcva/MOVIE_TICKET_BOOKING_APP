package com.example.movie_ticket_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    TextView username, bookinghistory, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);
        username = findViewById(R.id.username);
        bookinghistory = findViewById(R.id.bookinghistory);

        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount( this );
        if(googleSignInAccount != null)
        {
            username.setText( "Hello, "+googleSignInAccount.getDisplayName()+"!" );
        }

        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        } );

        bookinghistory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookingHistoryActivity.class);
                startActivity(intent);
            }
        } );

    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        MainActivity.mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// Google Sign In failed, update UI appropriately
                        Toast.makeText(getApplicationContext(),"Signed out of google",Toast.LENGTH_SHORT).show();
                    }
                });

        Intent intent = new Intent( getApplicationContext(),MainActivity.class );
        startActivity(intent);
    }
}