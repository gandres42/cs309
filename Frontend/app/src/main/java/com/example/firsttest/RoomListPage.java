package com.example.firsttest;

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

public class RoomListPage extends AppCompatActivity {

    private RecyclerViewRoomAdapter adapter;
    private RecyclerView recyclerView;
    private List<Room> rooms;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_list_page_actiivty);

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

        recyclerView = findViewById(R.id.recyclerview_rooms);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);

        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            String url = "http://10.24.227.150:8080/rooms/filter_by_studentid?studentid=" + user.getInt("studentid");
            rooms = new ArrayList<>();
            queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i <= response.length() - 1; ++i) {
                                    int id = response.getJSONObject(i).getInt("id");
                                    String name = response.getJSONObject(i).getString("name");
                                    if (response.getJSONObject(i).getJSONArray("users").length() > 2) {
                                        String users = response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getString("username") + ", " +
                                                       response.getJSONObject(i).getJSONArray("users").getJSONObject(1).getString("username") + ", " +
                                                       response.getJSONObject(i).getJSONArray("users").getJSONObject(2).getString("username") + ", etc.";

                                        ArrayList<String> usernames = new ArrayList<>();
                                        ArrayList<Integer> studentIds = new ArrayList<>();
                                        for (int j = 0; j <= response.getJSONObject(i).getJSONArray("users").length() - 1; ++j) {
                                            usernames.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(j).getString("username"));
                                            studentIds.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(j).getInt("studentid"));
                                        }
                                        rooms.add(new Room(id, name, users, studentIds, usernames));
                                    }
                                    else if (response.getJSONObject(i).getJSONArray("users").length() == 2) {
                                        String users = response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getString("username") + ", " +
                                                       response.getJSONObject(i).getJSONArray("users").getJSONObject(1).getString("username");
                                        ArrayList<String> usernames = new ArrayList<>();
                                        ArrayList<Integer> studentIds = new ArrayList<>();
                                        usernames.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getString("username"));
                                        usernames.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(1).getString("username"));
                                        studentIds.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getInt("studentid"));
                                        studentIds.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(1).getInt("studentid"));
                                        rooms.add(new Room(id, name, users, studentIds, usernames));
                                    }
                                    else {
                                        String users = response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getString("username");
                                        ArrayList<String> usernames = new ArrayList<>();
                                        ArrayList<Integer> studentIds = new ArrayList<>();
                                        usernames.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getString("username"));
                                        studentIds.add(response.getJSONObject(i).getJSONArray("users").getJSONObject(0).getInt("studentid"));
                                        rooms.add(new Room(id, name, users, studentIds, usernames));
                                    }
                                }
                                adapter = new RecyclerViewRoomAdapter(RoomListPage.this, rooms, getIntent());
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