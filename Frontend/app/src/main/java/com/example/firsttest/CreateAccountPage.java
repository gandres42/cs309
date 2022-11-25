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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CreateAccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_page_activity);

        // Initialize objects in activity
        Button GoBackButton = findViewById(R.id.cancel_button);
        Button createAccount = findViewById(R.id.create_button);
        EditText EnterID = findViewById(R.id.university_id_input);
        EditText EnterUsername = findViewById(R.id.username_input);
        EditText EnterEmail = findViewById(R.id.email_account_input);
        EditText EnterFirstName = findViewById(R.id.first_name_input);
        EditText EnterLastName = findViewById(R.id.last_name_input);
        EditText EnterPassword = findViewById(R.id.password_input);

        RequestQueue queue = Volley.newRequestQueue(this);

        String ip_address = "10.24.227.150";
        String port = "8080";

        createAccount.setBackgroundColor(getResources().getColor(R.color.gray));
        createAccount.setEnabled(false);

        //edit conditions of "username" when password is changed
        TextWatcher CreateEnabledWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (EnterID.getText().toString().equals("") || EnterPassword.getText().toString().equals("") ||
                EnterUsername.getText().toString().equals("") || EnterFirstName.getText().toString().equals("") ||
                EnterLastName.getText().toString().equals("") || EnterEmail.getText().toString().equals("")) {
                    createAccount.setBackgroundColor(getResources().getColor(R.color.gray));
                    createAccount.setEnabled(false);
                }
                else
                {
                    createAccount.setBackgroundColor(getResources().getColor(R.color.maroon));
                    createAccount.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        EnterPassword.addTextChangedListener(CreateEnabledWatcher);
        EnterUsername.addTextChangedListener(CreateEnabledWatcher);
        EnterEmail.addTextChangedListener(CreateEnabledWatcher);
        EnterID.addTextChangedListener(CreateEnabledWatcher);
        EnterFirstName.addTextChangedListener(CreateEnabledWatcher);
        EnterLastName.addTextChangedListener(CreateEnabledWatcher);

        //create account
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fnIsAlpha = "true";
                String lnIsAlpha = "true";

                for (int i = 0; i < EnterFirstName.getText().toString().length(); i++) {
                    if (!Character.isLetter(EnterFirstName.getText().toString().charAt(i))) {
                        fnIsAlpha = "false";
                    }
                }

                for (int i = 0; i < EnterLastName.getText().toString().length(); i++) {
                    if (!Character.isLetter(EnterLastName.getText().toString().charAt(i))) {
                        lnIsAlpha = "false";
                    }
                }

                if (EnterID.getText().toString().length() < 9) {
                    Toast toast = Toast.makeText(getApplicationContext(), "University ID must be 9 digits long", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (fnIsAlpha == "false") {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please adjust first name to being only alphabetic.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (lnIsAlpha == "false") {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please adjust last name to being only alphabetic.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (EnterPassword.getText().toString().length() <= 7) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Password must be greater than 7 characters", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // Make a post request to the provided URL, adds to the RequestQueue
                    String url = "http://" + ip_address + ":" + port + "/users/create?studentid=" + EnterID.getText().toString() +
                            "&password=" + EnterPassword.getText().toString() + "&username=" + EnterUsername.getText().toString() +
                            "&email=" + EnterEmail.getText().toString() + "&firstname=" + EnterFirstName.getText().toString() +
                            "&lastname=" + EnterLastName.getText().toString();

                    queue.add(new StringRequest(Request.Method.PUT, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    startActivity(new Intent(view.getContext(), LogInPage.class));
                                    // Display the response string.
                                    Toast toast = Toast.makeText(getApplicationContext(), "User " + response, Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Display error message: detailed error log available by searching for 'volley' in logcat
                            Toast toast = Toast.makeText(getApplicationContext(), "Creation Failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }));
                }
            }
        });


        //cancel account creation
        GoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LogInPage.class));
            }
        });
    }
}