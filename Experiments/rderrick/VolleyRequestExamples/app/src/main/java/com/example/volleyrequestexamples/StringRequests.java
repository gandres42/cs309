package com.example.volleyrequestexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class StringRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.string_requests_activity);

        //initialize objects on page
        Button BackButton = findViewById(R.id.back_button);
        Button StringRequest = findViewById(R.id.string_request_button);
        TextView result = findViewById(R.id.result);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Send user to VolleyRequests page when clicked
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), VolleyRequests.class));
            }
        });

        //Request for a String when clicked
        StringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                // Make a post request to the provided URL, adds to the RequestQueue
                String url = "http://10.24.227.150:8080/users/all";
                queue.add(new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                result.setText("Result: ");
                                result.setText(result.getText() + response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("tag", error.getMessage());
                        result.setText("Didn't work :(");                    }
                }));
            }
        });
    }
}