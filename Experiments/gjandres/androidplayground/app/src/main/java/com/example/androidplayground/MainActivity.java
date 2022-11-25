package com.example.androidplayground;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/getTest?key=message";

        // Request a string response from the provided URL, adds to the RequestQueue
        queue.add(new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    // Display the response string.
                    textView.setText("Response is: "+ response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // Display error message: detailed error log available by searching for 'volley' in logcat
                    textView.setText("Request failed: check logcat for detailed info");
                }
            })
        );

        url ="http://10.0.2.2:8080/postTest?message=greetings professor falken";
        // Make a post request to the provided URL, adds to the RequestQueue
        queue.add(new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    // Display the response string.
                    textView.setText("Response is: "+ response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // Display error message: detailed error log available by searching for 'volley' in logcat
                    textView.setText("Request failed: check logcat for detailed info");
                }
            })
        );

        url ="http://10.0.2.2:8080/getTest?key=message";

        // Request a string response from the provided URL, adds to the RequestQueue
        queue.add(new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    // Display the response string.
                    textView.setText("Response is: "+ response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    // Display error message: detailed error log available by searching for 'volley' in logcat
                    textView.setText("Request failed: check logcat for detailed info");
                }
            })
        );
    }
}