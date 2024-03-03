package com.example.pronotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NoteListFragment extends Fragment {

    public static NoteListAdapter notes_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        // Fetch note list
        ListView notes = (ListView)view.findViewById(R.id.notes_list);

        // Create custom adapter
        ArrayList<Note> notes_list = new ArrayList<>();

        switch (MainActivity.currentFragment) {
            case "home":
                for (Note note : MainActivity.notes) {
                    if (note.getAuthor().equals(MainActivity.username) && !note.isArchived()) {
                        notes_list.add(note);
                    }
                }
                break;
            case "archive":
                for (Note note : MainActivity.notes) {
                    if (note.getAuthor().equals(MainActivity.username) && note.isArchived()) {
                        notes_list.add(note);
                    }
                }
                break;
            case "favorites":
                for (Note note : MainActivity.notes) {
                    if (note.getAuthor().equals(MainActivity.username) && note.isFavorite()) {
                        notes_list.add(note);
                    }
                }
                break;
            default:
                for (Note note : MainActivity.notes) {
                    if (note.getAuthor().equals(MainActivity.username)) {
                        notes_list.add(note);
                    }
                }
                break;
        }

        NoteListAdapter.titles_list = new ArrayList<String>(Arrays.asList(new String[notes_list.size()]));
        notes_adapter = new NoteListAdapter(this.getActivity(), NoteListAdapter.titles_list, notes_list);

        // Populate the list
        notes.setAdapter(notes_adapter);

        return view;

    }
}