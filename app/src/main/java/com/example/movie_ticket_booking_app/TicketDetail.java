package com.example.movie_ticket_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie_ticket_booking_app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TicketDetail extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference tickethistoryref;
    String USERNAME = "";

    String bookingid = "BMT";
    String moviename = "";
    String ticketcount = "";
    String seats = "";
    String showtime = "";
    String showdate = "";

    TextView movienametv, seatstv, showdatetv, showtimetv, billvaluetv;
    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount( this );
        if(googleSignInAccount != null)
        {
            USERNAME = googleSignInAccount.getEmail();
        }

        firebaseFirestore = FirebaseFirestore.getInstance();
        tickethistoryref = firebaseFirestore.collection("users/"+USERNAME+"/tickethistory");

        movienametv = findViewById(R.id.moviename);
        showdatetv = findViewById(R.id.showdate);
        showtimetv = findViewById(R.id.showtime);
        seatstv = findViewById(R.id.seats);
        billvaluetv = findViewById(R.id.billvalue);
        buy = findViewById(R.id.buy);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            moviename = bundle.getString("moviename");
            ticketcount = bundle.getString("ticketcount");
            seats = bundle.getString("seats");
            showtime = bundle.getString("showtime");
            showdate = bundle.getString("showdate");

            movienametv.setText(moviename);
            showdatetv.setText(showdate);
            showtimetv.setText(showtime);
            seatstv.setText(seats);

            bookingid = bookingid + ticketcount + showtime + showdate;
            int bill = Integer.parseInt(ticketcount);
            bill *=200;
            billvaluetv.setText(bill+"");
        }
        //Toast.makeText(this,moviename+"-"+ticketcount+"-"+seats+"-"+showtime+"-"+showdate,Toast.LENGTH_LONG).show();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketModel productModel = new TicketModel(moviename, bookingid, showdate+" "+showtime,seats);
                tickethistoryref.add(productModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TicketDetail.this, "Booking confirmed "+documentReference.getId(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(TicketDetail.this, MovieListHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}