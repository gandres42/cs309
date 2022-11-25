package com.example.volleyrequestexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonRequests extends JsonParsing {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_requests_activity);

        //initialize objects on page
        Button BackButton = findViewById(R.id.back_button);
        Button JsonObjectRequest = findViewById(R.id.json_object_request_button);
        Button JsonArrayRequest = findViewById(R.id.json_array_request_button);
        TextView result = findViewById(R.id.result);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Send user to VolleyRequests page when clicked
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), VolleyRequests.class));
            }
        });

        //Request for a Json array when clicked
        JsonArrayRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                // Make a post request to the provided URL, adds to the RequestQueue
                String url = "http://10.24.227.150:8080/users/all";
                queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response)
                            {
                                result.setText(arrayToString(jsonParseArray(response)));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        result.setText(result + "Didn't work :(");
                    }
                }));
            }
        });

        //Request for a Json object when clicked
        JsonObjectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://web.postman.co/workspace/My-Workspace~4e0d7934-5a12-4fae-8cf7-" +
                        "d80ae9976164/request/17781624-2d4b3aa7-1991-48f8-a77a-418c3e1fb28a";
                queue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                result.setText(jsonParseObject(response));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                result.setText(result + "Didn't work :(");
                            }
                        }));
            }
        });
    }
}