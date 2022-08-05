package com.example.sebinefrancis.addsonglyrics;

/**
 * Created by Sebine Francis on 26/08/2017.
 */

public class NotesBuilder {

    private String title,
            content;

    public NotesBuilder() {
    }

    public NotesBuilder(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
