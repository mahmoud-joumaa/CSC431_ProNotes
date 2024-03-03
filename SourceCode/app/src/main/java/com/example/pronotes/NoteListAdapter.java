package com.example.pronotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class NoteListAdapter extends ArrayAdapter {

    private final Activity context;
    public static ArrayList<Note> notes;
    public static ArrayList<String> titles_list;

    public NoteListAdapter(Activity context, ArrayList<String> titles_list, ArrayList<Note> notes) {
        super(context, R.layout.notecard, titles_list);
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        // Inflate View of NoteCard
        LayoutInflater inflater = this.context.getLayoutInflater();
        View note = inflater.inflate(R.layout.notecard, null, true);
        // Initialize different views
        RadioButton note_high = (RadioButton)note.findViewById(R.id.note_high);
        RadioButton note_mid = (RadioButton)note.findViewById(R.id.note_mid);
        RadioButton note_low = (RadioButton)note.findViewById(R.id.note_low);
        EditText title = (EditText)note.findViewById(R.id.note_title);
        TextView created = (TextView)note.findViewById(R.id.note_created);
        TextView edited = (TextView)note.findViewById(R.id.note_edited);
        EditText description = (EditText)note.findViewById(R.id.note_desc);
        ImageButton favorite = (ImageButton)note.findViewById(R.id.btn_favorite);
        ImageButton alarm = (ImageButton)note.findViewById(R.id.btn_alarm);
        ImageButton archive = (ImageButton)note.findViewById(R.id.btn_archive);
        ImageButton delete = (ImageButton)note.findViewById(R.id.btn_delete);
        ImageButton share = (ImageButton)note.findViewById(R.id.btn_share);
        // Set the different views
        Note currentNote = notes.get(position);
        // Priority
        switch (currentNote.getPriority()) {
            case 1:
                note_low.setChecked(true);
                break;
            case 2:
                note_mid.setChecked(true);
                break;
            case 3:
                note_high.setChecked(true);
                break;
            default:
                break;
        }
        // Title & Description
        title.setText(currentNote.getTitle());
        description.setText(currentNote.getDescription());
        // Created & Edited time
        created.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(currentNote.getCreated()));
        edited.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(currentNote.getEdited()));
        // Button booleans
        if (currentNote.isFavorite()) changeColor(favorite, R.color.orange);
        else changeColor(favorite, R.color.silver);
        if (currentNote.isArchived()) changeColor(archive, R.color.green_sea);
        else changeColor(archive, R.color.silver);
        changeColor(alarm, R.color.wisteria);
        changeColor(delete, R.color.pomegranate);
        changeColor(share, R.color.belize_hole);
        // Add Listeners
        note_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setPriority(1);
                note_low.setChecked(true);
                FileHandler.writeToFile(getContext());
            }
        });
        note_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setPriority(2);
                note_mid.setChecked(true);
                FileHandler.writeToFile(getContext());
            }
        });
        note_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setPriority(3);
                note_high.setChecked(true);
                FileHandler.writeToFile(getContext());
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentNote.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentNote.setEdited(LocalDateTime.now());
                FileHandler.writeToFile(getContext());
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentNote.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentNote.setEdited(LocalDateTime.now());
                FileHandler.writeToFile(getContext());
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setFavorite(!currentNote.isFavorite());
                if (currentNote.isFavorite())
                    changeColor(favorite, R.color.orange);
                else
                    changeColor(favorite, R.color.silver);
                FileHandler.writeToFile(getContext());
            }
        });
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setArchived(!currentNote.isArchived());
                if (currentNote.isArchived())
                    changeColor(archive, R.color.green_sea);
                else
                    changeColor(archive, R.color.silver);
                FileHandler.writeToFile(getContext());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Note note : MainActivity.notes) {
                    if (note.isEqualTo(currentNote)) {
                        MainActivity.notes.remove(note);
                        break;
                    }
                }
                notes.remove(currentNote);
                titles_list.remove(position);
                NoteListFragment.notes_adapter.notifyDataSetChanged();
                FileHandler.writeToFile(getContext());
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, currentNote.getTitle());
                context.startActivity(alarmIntent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, currentNote.getTitle() + "\n" + currentNote.getDescription());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, v.getContext().getResources().getText(R.string.send_to)));
            }
        });
        // Return the view
        return note;
    }

    private void changeColor(ImageButton btn, int color) {
        btn.setBackgroundColor(context.getResources().getColor(color));
    }

}
