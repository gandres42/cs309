package com.example.volleyrequestexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class VolleyRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_requests_activity);

        Button StringRequest = findViewById(R.id.string_request_button);
        Button JsonRequest = findViewById(R.id.json_request_button);
        Button ImageRequest = findViewById(R.id.image_request_button);
        Button MenuExample = findViewById(R.id.menu_button);
        TextView date = findViewById(R.id.date);

        Date currentTime = Calendar.getInstance().getTime();
        date.setText(currentTime.toString());

        //Send user to StringRequests page when clicked
        StringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), StringRequests.class));
            }
        });

        //Send user to JsonRequests page when clicked
        JsonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), JsonRequests.class));
            }
        });

        //Send user to ImageRequests page when clicked
        ImageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), ImageRequests.class));
            }
        });

        //Send user to MenuExample page when clicked
        MenuExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), MenuExample.class));
            }
        });

    }
}