package com.example.pronotes;

import android.widget.ArrayAdapter;

import java.time.LocalDateTime;

public class Note {

    // Attributes =================================================================================
    private String author;
    private String title;
    private String description;
    private int priority; // From (more urgent) 1 --> 3 (less urgent)
    private boolean archived;
    private boolean favorite;
    private LocalDateTime reminder;
    private LocalDateTime created;
    private LocalDateTime edited;

    // Constructors ===============================================================================
    public Note() {
        setAuthor(MainActivity.username);
        setTitle("");
        setDescription("");
        setPriority(2);
        setArchived(false);
        setFavorite(false);
        setCreated(LocalDateTime.now());
    }

    public Note(String author, String title, String description, int priority, boolean archived, boolean favorite, LocalDateTime reminder, LocalDateTime created, LocalDateTime edited) {
        setAuthor(author);
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setArchived(archived);
        setFavorite(favorite);
        setReminder(reminder);
        setCreated(created);
        setEdited(edited);
    }

    // Getters ====================================================================================
    public int getPriority() {
        return priority;
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isArchived() {
        return archived;
    }
    public boolean isFavorite() {
        return favorite;
    }
    public LocalDateTime getReminder() {
        return reminder;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public LocalDateTime getEdited() {
        return edited;
    }

    // Setters ====================================================================================
    public void setPriority(int priority) {
        this.priority = priority;
        setEdited(LocalDateTime.now());
    }
    public void setAuthor(String author) {
        this.author = author;
        setEdited(LocalDateTime.now());
    }
    public void setTitle(String title) {
        this.title = title;
        setEdited(LocalDateTime.now());
    }
    public void setDescription(String description) {
        this.description = description;
        setEdited(LocalDateTime.now());
    }
    public void setArchived(boolean archived) {
        this.archived = archived;
        setEdited(LocalDateTime.now());
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        setEdited(LocalDateTime.now());
    }
    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
        setEdited(LocalDateTime.now());
    }
    private void setCreated(LocalDateTime created) {
        this.created = created;
        setEdited(LocalDateTime.now());
    }
    public void setEdited(LocalDateTime edited) {
        this.edited = edited;
    }

    public boolean isEqualTo(Note note) {
        return this.getCreated().isEqual(note.getCreated());
    }

}
