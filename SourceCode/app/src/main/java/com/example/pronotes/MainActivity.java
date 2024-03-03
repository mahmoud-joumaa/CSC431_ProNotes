package com.example.pronotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Theme implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView nav;

    public static String username = "Guest";

    public static ArrayList<Note> notes = new ArrayList<>();
    public static String currentFragment = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Intent Data
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        if (username == null) username = "Guest";
        Log.d("logging username", username);

        // Initialize the nav drawer
        initNavDrawer();

        // Check if the activity is newly created (i.e. activity lifecycle created state)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            nav.setCheckedItem(R.id.nav_home);
            currentFragment = "home";
        }

        // Read from file to initialize notes
        FileHandler.readFromFile(this, "notes.txt");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            currentFragment = "home";
        }
        else if (id == R.id.nav_all) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllNotesFragment()).commit();
            currentFragment = "all";
        }
        else if (id == R.id.nav_archive) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArchiveFragment()).commit();
            currentFragment = "archive";
        }
        else if (id == R.id.nav_favorites) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
            currentFragment = "favorites";
        }
        else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
            currentFragment = "about";
        }
        else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
            currentFragment = "settings";
        }
        else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Log Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    private void initNavDrawer() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.drawer);

        nav = (NavigationView)findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

}