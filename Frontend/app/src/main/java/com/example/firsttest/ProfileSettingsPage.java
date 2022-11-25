package com.example.firsttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileSettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings_page_activity);

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);
        Button SubmitChanges = findViewById(R.id.submit_changes_button);
        EditText EditUsername = findViewById(R.id.edit_username);
        EditText EditEmail = findViewById(R.id.edit_email);
        RequestQueue queue = Volley.newRequestQueue(this);

        String ip_address = "10.24.227.150";
        String port = "8080";

        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            EditUsername.setText(user.getString("username"));
            EditEmail.setText(user.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //On click, change users data to specified input and then send user back to HomePage
        SubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean changedUsername = false;
                boolean changedEmail = false;
                String url = "";
                String update = "update_username?";
                try {
                    //Grab current user's id for the url
                    JSONObject userInfo = new JSONObject(getIntent().getExtras().getString("currentUser"));
                    url = "http://" + ip_address + ":" + port + "/users/update_username?studentid=" +
                        userInfo.getString("studentid") + "&value=" + EditUsername.getText().toString();

                    //Check if username information has been changed
                    if(!EditUsername.getText().toString().equals(userInfo.getString("username"))) {

                        //Change user's username and display new username to user
                        queue.add(new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the new user.
                                        Toast toast = Toast.makeText(getApplicationContext(), "Username has been changed to " + response, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Display if url error.
                                Toast toast = Toast.makeText(getApplicationContext(), "Could not create new username", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }));
                        changedUsername = true;
                    }

                    url = "http://" + ip_address + ":" + port + "/users/update_email?studentid=" +
                        userInfo.getString("studentid") + "&value=" + EditEmail.getText().toString();

                    //Check if email information has been changed
                    if(!EditEmail.getText().toString().equals(userInfo.getString("email"))) {

                        //Change user's email and display new email to user
                        queue.add(new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the new email.
                                        Toast toast = Toast.makeText(getApplicationContext(), "Email has been changed to " + response, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Display if url error.
                                Toast toast = Toast.makeText(getApplicationContext(), "Could not create new email", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }));
                        changedEmail = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Update intent's current user information with the new updated current user information
                Bundle user = getIntent().getExtras();
                JSONObject userInfo = null;
                try {
                    userInfo = new JSONObject(user.getString("currentUser"));
                    if (changedUsername) {
                        userInfo.put("username", EditUsername.getText().toString());
                    }
                    if (changedEmail) {
                        userInfo.put("email", EditEmail.getText().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(view.getContext(), SettingsPage.class);
                intent.putExtra("currentUser", userInfo.toString());
                startActivity(intent);
            }
        });

        //Sends user back to the SettingsPage when clicked
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = getIntent().getExtras();
                String userInfo = user.getString("currentUser");
                Intent intent = new Intent(view.getContext(), SettingsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
            }
        });
    }
}