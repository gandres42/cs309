package com.example.firsttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page_activity);

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);
        TextView ProfileSettings = findViewById(R.id.profile_settings);
        TextView SettingsHeader = findViewById(R.id.settings_header);
        ConstraintLayout SettingsBackground = findViewById(R.id.settings_background);

        // initiate a Switch
        Switch darkModeSwitch = (Switch) findViewById(R.id.dark_mode_toggle);
        Switch annoucementSwitch = (Switch) findViewById(R.id.notification_toggle);

        // check current state of a Switch (true or false).
        Boolean switchState = false;

        darkModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1;
                if (darkModeSwitch.isChecked()) {
                    SettingsHeader.setBackgroundResource(R.drawable.black_box);
                    SettingsBackground.setBackgroundResource(R.drawable.black_box);
                    ProfileSettings.setTextColor(getResources().getColor(R.color.gold));
                    darkModeSwitch.setTextColor(getResources().getColor(R.color.gold));
                    annoucementSwitch.setTextColor(getResources().getColor(R.color.gold));
                    Toast.makeText(getApplicationContext(), "Dark Mode Enabled", Toast.LENGTH_LONG).show();
                }
                else {
                    SettingsHeader.setBackgroundResource(R.drawable.maroon_header_box);
                    SettingsBackground.setBackgroundResource(R.drawable.white_box);
                    ProfileSettings.setTextColor(getResources().getColor(R.color.black));
                    darkModeSwitch.setTextColor(getResources().getColor(R.color.black));
                    annoucementSwitch.setTextColor(getResources().getColor(R.color.black));
                    Toast.makeText(getApplicationContext(), "Light Mode Enabled", Toast.LENGTH_LONG).show(); // display the current state for switch's
                }
                }
        });

        //Sends user back to the HomePage when clicked
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



        //Sends user to the ProfileSettingsPage when clicked
        ProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = getIntent().getExtras();
                String userInfo = user.getString("currentUser");
                Intent intent = new Intent(view.getContext(), ProfileSettingsPage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
            }
        });
    }

    Boolean darkModeEnabled(Switch switch1){
        return switch1.isChecked();
    }

    void enableDarkMode(Switch switch1){
        switch1.performClick();
    }

    Switch getSwitch(){
        Switch darkModeSwitch = (Switch) findViewById(R.id.dark_mode_toggle);
        return darkModeSwitch;
    }

    /*Switch getSwitch(){
        return darkModeSwitch;
    }*/
}