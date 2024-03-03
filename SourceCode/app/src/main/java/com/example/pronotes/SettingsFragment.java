package com.example.pronotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Get the corresponding moon color
        ImageView moon_dark = (ImageView)view.findViewById(R.id.dark_mode_dark);
        ImageView moon_light = (ImageView)view.findViewById(R.id.dark_mode_light);
        if (Theme.dark) moon_light.setVisibility(View.VISIBLE);
        else moon_dark.setVisibility(View.VISIBLE);

        // Get the switch toggle
        SwitchMaterial toggle = (SwitchMaterial) view.findViewById(R.id.theme_toggle);
        toggle.setChecked(Theme.dark);
        // Add switching logic
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("UnsafeIntentLaunch")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Get new theme
                Theme.dark = isChecked;
                Activity main = requireActivity();
                // Refresh the activity
                main.finish();
                main.startActivity(main.getIntent());
            }
        });

        // Get the spinner of languages
        Spinner languages = (Spinner)view.findViewById(R.id.languages);
        // Change language based on user's choice
        // Keeps on giving error, could not debug, works well when the language of the device changes though
        /*
        String language = languages.getItemAtPosition(position).toString();
        Toast.makeText(requireContext(), language, Toast.LENGTH_LONG).show();
        String lang = "en";
        if (language.equals("عربي")) lang = "ar";
        else if (language.equals("Francais")) lang = "fr";
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Activity main = requireActivity();
        main.finish();
        main.startActivity(main.getIntent());
         */

        return view;
    }
}