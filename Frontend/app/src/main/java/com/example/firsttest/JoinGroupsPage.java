package com.example.firsttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupsPage extends AppCompatActivity {

    private RecyclerViewClubAdapter adapter;
    private RecyclerView recyclerView;
    private List<Club> clubs;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_groups_page_activity);

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);
        TextView NoMatches = findViewById(R.id.error_response);
        EditText SearchBar = findViewById(R.id.search_bar);

        //Send user back to the HomePage when clicked
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

        recyclerView = findViewById(R.id.recyclerview_clubs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);
        clubs = new ArrayList<>();

        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String searchText = SearchBar.getText().toString().toLowerCase();
                clubs = new ArrayList<>();

                //Volley Request to grab the name, id, and members of all the clubs
                String url = "http://10.24.227.150:8080/clubs/all";
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
                                    }
                                    else {
                                        NoMatches.setVisibility(View.INVISIBLE);
                                    }
                                    adapter = new RecyclerViewClubAdapter(JoinGroupsPage.this, clubs, getIntent());
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
            }
        });

        //Volley Request to grab the name, id, and members of all the clubs
        String url = "http://10.24.227.150:8080/clubs/all";
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
                            adapter = new RecyclerViewClubAdapter(JoinGroupsPage.this, clubs, getIntent());
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
    }
}
