package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CreateClubPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_club_page_activity);

        EditText CreateClubName = findViewById(R.id.edit_club_name);
        EditText CreateClubDescription = findViewById(R.id.edit_club_description);
        TextView BackButton = findViewById(R.id.back_button);
        Button CreateClubButton = findViewById(R.id.create_club_button);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Send user back to the home page when clicked
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = getIntent().getExtras();
                String userInfo = user.getString("currentUser");
                Intent intent = new Intent(view.getContext(), HomePage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
            }
        });

        CreateClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreateClubName.getText().toString().equals("") || CreateClubDescription.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill out all of the information", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    String url = "http://10.24.227.150:8080/clubs/create?description=" + CreateClubDescription.getText().toString() + "&name=" + CreateClubName.getText().toString();
                    queue.add(new JsonObjectRequest(Request.Method.PUT, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Created Club: " + CreateClubName.getText().toString(), Toast.LENGTH_SHORT);
                                    toast.show();
                                    BackButton.performClick();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());
                        }
                    }));
                }
            }
        });
    }
}