package com.example.firsttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page_activity);

        Button SignUpButton = findViewById(R.id.sign_up_button);
        Button SignInButton = findViewById(R.id.sign_in_button);
        EditText EnterUsername = findViewById(R.id.username_input);
        EditText EnterPassword = findViewById(R.id.password_input);
        RequestQueue queue = Volley.newRequestQueue(this);

        String ip_address = "10.24.227.150";
        String port = "8080";

        //Edit conditions of "username" when text it changed
        EnterUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Gray out and make the SignInButton un-clickable when there is no text in one of the username and password boxes
                if (!EnterUsername.getText().toString().matches("") && !EnterPassword.getText().toString().matches("")) {
                    SignInButton.setBackgroundColor(getResources().getColor(R.color.gold));
                    SignInButton.setEnabled(true);
                }
                if (EnterUsername.getText().toString().matches("")) {
                    SignInButton.setBackgroundColor(getResources().getColor(R.color.gray));
                    SignInButton.setEnabled(false);
                }
            }
        });

        //Edit conditions of "password" when text is changed
        EnterPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Gray out and make the SignInButton un-clickable when there is no text in one of the username and password boxes
                if (!EnterUsername.getText().toString().matches("") && !EnterPassword.getText().toString().matches("")) {
                    SignInButton.setBackgroundColor(getResources().getColor(R.color.gold));
                    SignInButton.setEnabled(true);
                }
                if (EnterPassword.getText().toString().matches("")) {
                    SignInButton.setBackgroundColor(getResources().getColor(R.color.gray));
                    SignInButton.setEnabled(false);
                }
            }
        });

        //"Sign Up" button is clicked
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                startActivity(new Intent(view.getContext(), CreateAccountPage.class));
            }
        });

        //"Sign In" button is clicked
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where we write the logic for our button
                // Make a post request to the provided URL, adds to the RequestQueue
                String url = "http://" + ip_address + ":" + port + "/users/login?studentid=" + EnterUsername.getText().toString() + "&password=" + EnterPassword.getText().toString();
                queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            validateUserInformation(response.getString("firstname") + " " + response.getString("lastname"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (response.equals(""))
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else
                        {
                            try {
                                Toast toast = Toast.makeText(getApplicationContext(), "Welcome " + response.getString("firstname") + " " + response.getString("lastname"), Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(view.getContext(), HomePage.class);
                            intent.putExtra("currentUser", response.toString());
                            startActivity(intent);
                        }
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // Display error message: detailed error log available by searching for 'volley' in logcat
                        Toast toast = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }));
                validate(EnterUsername.getText().toString(),EnterPassword.getText().toString());
            }
        });
    }

    public String validate(String userName, String password)
    {
        if(userName.equals("user") && password.equals("user"))
            return "Login was successful";
        else
            return "Invalid login!";
    }

    public String validateUserInformation(String name) {

        return "";
    }
}