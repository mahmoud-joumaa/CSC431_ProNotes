package com.example.pronotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Greet the user
        TextView username_text = (TextView)view.findViewById(R.id.username);
        String guest = getString(R.string.guest);
        String welcome = getString(R.string.welcome) + ", " + MainActivity.username;
        String welcome_back = getString(R.string.welcome_back) + ", " + MainActivity.username;
        if (MainActivity.username.equals(guest)) username_text.setText(welcome);
        else username_text.setText(welcome_back);

        // Handle the FAB
        FloatingActionButton add = (FloatingActionButton)view.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                MainActivity.notes.add(note);
                NoteListAdapter.notes.add(note);
                NoteListAdapter.titles_list.add("");
                NoteListFragment.notes_adapter.notifyDataSetChanged();
                FileHandler.writeToFile(getContext());
            }
        });

        return view;

    }
}