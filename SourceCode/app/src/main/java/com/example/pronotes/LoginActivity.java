package com.example.pronotes;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends Theme {

    private EditText name_input;
    private EditText pass_input;
    private Button signup;
    private Button login;
    private Button guest;

    public static ArrayList<String> names = new ArrayList<String>();
    public static ArrayList<String> passwords = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize different views
        initViews();
        initCredentials();

        // Configure the signup button to validate new users

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the value of each input
                String name = name_input.getText().toString();
                String pass = pass_input.getText().toString();

                if (!validateUsername(name)) {
                    Toast.makeText(v.getContext(), R.string.username_already_exists, Toast.LENGTH_LONG).show();
                    return;
                }
                if (!validatePassword(pass)) {
                    Toast.makeText(v.getContext(), R.string.your_password_should_be_at_least_8_characters_long, Toast.LENGTH_LONG).show();
                    return;
                }

                names.add(name);
                passwords.add(pass);
                FileHandler.writeToFile(v.getContext(), name, pass);
                Toast.makeText(v.getContext(), getString(R.string.sign_up_successful), Toast.LENGTH_LONG).show();

            }
        });

        // Configure the login buttons to redirect to the main activity

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the value of each input
                String name = name_input.getText().toString();
                String pass = pass_input.getText().toString();

                if (validateLogin(name, pass)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
                else {
                     Snackbar.make(v, R.string.invalid_credentials, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             clearInputs();
                         }
                     }).show();
                }
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", getString(R.string.guest));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity(); // prevent the user from going back after logging out
    }

    private void initViews() {
        name_input = (EditText)findViewById(R.id.name_input);
        pass_input = (EditText)findViewById(R.id.pw_input);
        signup = (Button)findViewById(R.id.btn_sign_up);
        login = (Button)findViewById(R.id.btn_login);
        guest = (Button)findViewById(R.id.btn_login_guest);
    }

    private void initCredentials() {
        // Constant users
        names.add("admin"); passwords.add("admin");
        // Reading other users
        FileHandler.readFromFile(this, "users.txt");
    }

    private boolean validateLogin(String name, String pass) {
        int ind = -1;
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(name)) {
                ind = i;
                break;
            }
        }
        if (ind == -1) return false;
        return passwords.get(ind).equals(pass);
    }

    private void clearInputs() {
        name_input.setText("");
        pass_input.setText("");
    }

    private boolean validateUsername(String user) {
        return !names.contains(user);
    }
    private boolean validatePassword(String pass) {
        return (pass.length() >= 8);
    }

}