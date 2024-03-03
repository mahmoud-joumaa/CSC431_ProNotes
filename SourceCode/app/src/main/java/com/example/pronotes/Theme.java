package com.example.pronotes;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Theme extends AppCompatActivity {

    protected static boolean dark = true;

    protected void onCreate(Bundle savedInstanceState) {

        // Initialize the activity
        super.onCreate(savedInstanceState);
        if (dark)
            setTheme(com.google.android.material.R.style.Theme_Material3_Dark_NoActionBar);
        else
            setTheme(com.google.android.material.R.style.Theme_Material3_Light_NoActionBar);
    }

}
