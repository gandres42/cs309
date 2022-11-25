package com.example.volleyrequestexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MenuExample extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_example_activity);

        TextView MenuButton = findViewById(R.id.access_menu);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.nav_view);

        navigation.bringToFront();
        navigation.setNavigationItemSelectedListener(this);

        //Open menu when button is clicked
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(MenuExample.this, VolleyRequests.class));
                return true;
            case R.id.nav_profile:
                // Handle settings click
                return true;
            case R.id.nav_logout:
                // Handle logout click
                return true;
            default:
                return false;
        }
    }
}