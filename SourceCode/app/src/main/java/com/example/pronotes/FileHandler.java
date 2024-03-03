package com.example.pronotes;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class FileHandler {

    private static String cleanNote(Note note) {
        return note.getAuthor()+";"+note.getTitle()+";"+note.getDescription()+";"+ note.getPriority()+";"+note.isArchived()+";"+note.isFavorite()+";"+note.getReminder()+";"+note.getCreated()+";"+note.getEdited();
    }

    public static void writeToFile(Context context) {
        try {
            for (Note note : MainActivity.notes) {
                String data = cleanNote(note);
                FileOutputStream fos = context.openFileOutput("notes.txt", Context.MODE_PRIVATE);
                fos.write((data + "\n").getBytes());
                fos.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(Context context, String user, String pass) {
        try {
            String data = user + ";" + pass;
            FileOutputStream fos = context.openFileOutput("users.txt", Context.MODE_APPEND);
            fos.write((data + "\n").getBytes());
            fos.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(Context context, String name) {
        try {
            FileInputStream fis = context.openFileInput(name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            if (name.equals("users.txt")) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    LoginActivity.names.add(data[0]);
                    LoginActivity.passwords.add(data[1]);
                }
            }
            else if (name.equals("notes.txt")) {
                while ((line = br.readLine()) != null) {
                    // note.getAuthor()+";"+note.getTitle()+";"+note.getDescription()+";"+ note.getPriority()+";"+note.isArchived()+";"+note.isFavorite()+";"+note.getReminder()+";"+note.getCreated()+";"+note.getEdited()
                    String[] data = line.split(";");
                    String author = data[0];
                    Log.d("debugging username", author);
                    Log.d("debugging username", MainActivity.username);
                    Log.d("debugging username", Boolean.toString(author.equals(MainActivity.username)));
                    String title = data[1];
                    String description = data[2];
                    int priority = Integer.parseInt(data[3]);
                    boolean isArchived = Boolean.parseBoolean(data[4]);
                    boolean isFavorite = Boolean.parseBoolean(data[5]);
//                      LocalDateTime reminder = LocalDateTime.parse(data[6]);
                    LocalDateTime created = LocalDateTime.parse(data[7]);
                    LocalDateTime edited = LocalDateTime.parse(data[8]);
                    // Create a new note
                    Note file_note = new Note(author, title, description, priority, isArchived, isFavorite, null, created, edited);
                    // Check if this note is new or edited
                    for (Note note : MainActivity.notes) {
                        if (note.isEqualTo(file_note)) {
                            MainActivity.notes.remove(note);
                            break;
                        }
                    }
                    MainActivity.notes.add(file_note);
                }
            }
            fis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
