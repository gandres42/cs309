package com.example.volleyrequestexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ImageRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_requests_activity);

        //initialize objects on page
        Button BackButton = findViewById(R.id.back_button);
        Button ImageRequest = findViewById(R.id.image_request_button);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Send user to VolleyRequests page when clicked
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), VolleyRequests.class));
            }
        });

        //Request for an image when clicked
        ImageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}