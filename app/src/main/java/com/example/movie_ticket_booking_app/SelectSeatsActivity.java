package com.example.movie_ticket_booking_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectSeatsActivity extends AppCompatActivity {

    final static int SEATCOUNT = 101;

    String showtime = "", showdate = "";

    ToggleButton[] buttons = new ToggleButton[SEATCOUNT];
    Button calcbtn;
    String value = "";
    StringBuilder seats = new StringBuilder();
    RadioGroup rgshowtime;
    RadioGroup rgshowdate;
    RadioButton rbshowtime;
    RadioButton rbshowdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seats);
        rgshowtime = findViewById(R.id.rgshowtime);
        rgshowdate = findViewById(R.id.rgshowdate);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("moviename");
        }

        calcbtn = findViewById(R.id.calcbtn);

        createSeats();

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ticketcount = 0;
                seats.delete(0, seats.length());
                for (int i = 1; i < SEATCOUNT; i++) {
                    if (buttons[i].isChecked()) {
                        ticketcount++;
                        seats.append(i + ",");
                    }
                }

                if (ticketcount > 0 && showdate.length() != 0 && showtime.length() != 0) {

                    seats.deleteCharAt(seats.lastIndexOf(","));

                    Intent intent = new Intent(getApplicationContext(), TicketDetail.class);

                    intent.putExtra("moviename", value);
                    intent.putExtra("ticketcount", String.valueOf(ticketcount));
                    intent.putExtra("seats", seats.toString());
                    intent.putExtra("showdate", showdate);
                    intent.putExtra("showtime", showtime);
                    startActivity(intent);

                } else
                    Toast.makeText(getApplicationContext(), "Select seats, show date and time.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.morning:
                if (checked)
                    showtime = "9:30AM";
                break;

            case R.id.afternoon:
                if (checked)
                    showtime = "12:30PM";
                break;

            case R.id.evening:
                if (checked)
                    showtime = "06:30PM";
                break;

            case R.id.night:
                if (checked)
                    showtime = "09:30PM";
                break;

            case R.id.today:
                if (checked) {
                    Calendar calendar = Calendar.getInstance();
                    Date today = calendar.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    showdate = df.format(today);
                    break;
                }


            case R.id.tomorrow:
                if (checked) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    showdate = df.format(tomorrow);
                    break;
                }


        }
    }

    private void createSeats() {
        for (int i = 1; i < SEATCOUNT; i++) {
            ToggleButton button = new ToggleButton(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = getResources().getDisplayMetrics().widthPixels / 8;
            button.setLayoutParams(params);
            button.setId(i);
            button.setText(String.valueOf(i));
            button.setTextOn(String.valueOf(i));
            button.setTextOff(String.valueOf(i));
            button.setMinWidth(100);
            buttons[i] = button;
        }

        GridLayout layout = findViewById(R.id.gridLayout);
        for (int i = 1; i < SEATCOUNT; i++) {
            layout.addView(buttons[i]);
        }
    }
}