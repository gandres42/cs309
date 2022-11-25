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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemberListPage extends AppCompatActivity {

    private RecyclerViewMemberAdapter adapter;
    private RecyclerView recyclerView;
    private List<Member> members;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list_page_activity);

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);

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

        recyclerView = findViewById(R.id.recyclerview_members);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);

        String url = "http://10.24.227.150:8080/clubs/get_users?clubid=" + getIntent().getExtras().getInt("currentClub");
        members = new ArrayList<>();

        queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i <= response.length() - 1; ++i) {
                                String name = response.getJSONObject(i).getString("firstname") + " "  + response.getJSONObject(i).getString("lastname");
                                String username = response.getJSONObject(i).getString("username");
                                String email = "Email: " + response.getJSONObject(i).getString("email");
                                int id = response.getJSONObject(i).getInt("studentid");
                                members.add(new Member(name, username, email, id, R.drawable.profile_circle_vector));
                            }
                            adapter = new RecyclerViewMemberAdapter(MemberListPage.this, members, getIntent());
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