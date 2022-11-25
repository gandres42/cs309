package com.example.firsttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsPage extends AppCompatActivity {

    private RecyclerViewAnnouncementAdapter adapter;
    private RecyclerView recyclerView;
    private List<Announcement> announcements;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements_page_activity);

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);

        //Send user back to the HomePage on click
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

        recyclerView = findViewById(R.id.recyclerview_announcements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);

        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            String url = "http://10.24.227.150:8080/users/get_clubs?studentid=" + user.getInt("studentid");
            announcements = new ArrayList<>();
            queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i <= response.length() - 1; ++i) {
                                    int clubId = response.getJSONObject(i).getInt("id");
                                    for (int j = 0; j <= response.getJSONObject(i).getJSONArray("annoucements").length() - 1; ++j) {
                                        JSONObject announcement = response.getJSONObject(i).getJSONArray("annoucements").getJSONObject(j);
                                        String header = announcement.getString("title");
                                        String text = announcement.getString("text");

                                        String date = announcement.getString("date");
                                        date = date.substring(0, Math.min(date.length(), 10));
                                        int day = Integer.parseInt(date.substring(8, 10));
                                        int month = Integer.parseInt(date.substring(5, 7));
                                        int year = Integer.parseInt(date.substring(0, 4));
                                        announcements.add(new Announcement(header, text, day, month, year, clubId));
                                    }
                                }
                                adapter = new RecyclerViewAnnouncementAdapter(AnnouncementsPage.this, announcements, getIntent());
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}