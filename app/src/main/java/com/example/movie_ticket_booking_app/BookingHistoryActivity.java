package com.example.movie_ticket_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.bookinghistorylist);

        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount( this );
        if(googleSignInAccount != null)
        {
            USERNAME = googleSignInAccount.getEmail();
        }

        //Query
        Query query = firebaseFirestore.collection("users/"+USERNAME+"/tickethistory");
        //recycleroptions
        FirestoreRecyclerOptions<TicketModel> options = new FirestoreRecyclerOptions.Builder<TicketModel>()
                .setQuery(query, TicketModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<TicketModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_card, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull TicketModel model) {
                holder.moviename.setText(model.getMoviename());
                holder.bookingid.setText(model.getBookingid());
                holder.showtime.setText(model.getShowtime());
                holder.seats.setText(model.getSeats());

            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder{

        private TextView moviename, bookingid, showtime, seats;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);



            moviename = itemView.findViewById(R.id.moviename);
            bookingid = itemView.findViewById(R.id.bookingid);
            showtime = itemView.findViewById(R.id.showtime);
            seats = itemView.findViewById(R.id.seats);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}