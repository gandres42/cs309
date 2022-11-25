package com.example.firsttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerViewClubAdapter adapter;
    private RecyclerView recyclerView;
    private List<Club> clubs;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        TextView MenuButton = findViewById(R.id.menu_button);
        TextView JoinGroupsPlusButton = findViewById(R.id.add_groups_button);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.nav_view);
        Menu nav_menu = navigation.getMenu();
        TextView NoMatches = findViewById(R.id.error_response);
        EditText SearchBar = findViewById(R.id.search_bar);

        navigation.bringToFront();
        navigation.setNavigationItemSelectedListener(this);

        //Open menu when button is clicked
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //Check if user is allowed to create clubs
        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            if (!(user.getString("permissionlevel").equals("CLUB_BOARD"))) {
                nav_menu.findItem(R.id.nav_create_club).setVisible(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Volley Request to grab the name, id, and members of all the clubs
                try {
                    JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
                    String url = "http://10.24.227.150:8080/users/get_clubs?studentid=" + user.getInt("studentid");
                    final String searchText = SearchBar.getText().toString().toLowerCase();
                    clubs = new ArrayList<>();
                    queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        for (int i = 0; i <= response.length() - 1; ++i) {
                                            if (response.getJSONObject(i).getString("name").toLowerCase().contains(searchText)) {
                                                String name = response.getJSONObject(i).getString("name");
                                                int members = response.getJSONObject(i).getJSONArray("users").length();
                                                int id = response.getJSONObject(i).getInt("id");
                                                clubs.add(new Club(name, members, id));
                                            }
                                        }
                                        if (clubs.size() == 0) {
                                            NoMatches.setVisibility(View.VISIBLE);
                                        } else {
                                            NoMatches.setVisibility(View.INVISIBLE);
                                        }
                                        adapter = new RecyclerViewClubAdapter(HomePage.this, clubs, getIntent());
                                        recyclerView.setAdapter(adapter);
                                        clubs = new ArrayList<>();
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
        });

        //Send user to the JoinGroupsPage when clicked
        JoinGroupsPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = getIntent().getExtras();
                String userInfo = user.getString("currentUser");
                Intent intent = new Intent(view.getContext(), JoinGroupsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerview_clubs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);

        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            String url = "http://10.24.227.150:8080/users/get_clubs?studentid=" + user.getInt("studentid");
            clubs = new ArrayList<>();
            queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i <= response.length() - 1; ++i) {
                                String name = response.getJSONObject(i).getString("name");
                                int members = response.getJSONObject(i).getJSONArray("users").length();
                                int id = response.getJSONObject(i).getInt("id");
                                clubs.add(new Club(name, members, id));
                            }
                            adapter = new RecyclerViewClubAdapter(HomePage.this, clubs, getIntent());
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle user = getIntent().getExtras();
        String userInfo = user.getString("currentUser");
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_announcements:
                intent = new Intent(HomePage.this, AnnouncementsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            case R.id.nav_rooms:
                intent = new Intent(HomePage.this, RoomListPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            case R.id.nav_meetings:
                intent = new Intent(HomePage.this, MeetingsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            case R.id.nav_settings:
                intent = new Intent(HomePage.this, SettingsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            case R.id.nav_create_club:
                intent = new Intent(HomePage.this, CreateClubPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                intent = new Intent(HomePage.this, LogInPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}