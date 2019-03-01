package com.amairovi.dto;

import com.amairovi.model.FeedAuthor;

public class Author {
    private String email;
    private String name;
    private String uri;

    public Author() {
    }

    public Author(FeedAuthor author) {
        email = author.getEmail();
        name = author.getName();
        uri = author.getUri();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
