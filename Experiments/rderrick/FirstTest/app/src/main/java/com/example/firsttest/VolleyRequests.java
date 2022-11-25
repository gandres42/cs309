package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VolleyRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_requests_activity);

        Button StringRequest = findViewById(R.id.string_request_button);
        Button JsonRequest = findViewById(R.id.json_request_button);
        Button ImageRequest = findViewById(R.id.image_request_button);

        StringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), StringRequest.class));
            }
        });

        JsonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), JsonRequest.class));
            }
        });

        ImageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), ImageRequest.class));
            }
        });

    }
}