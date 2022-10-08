package com.example.notesforu.Models;

public class CommentModel {
    String comments;
    String email;

    public CommentModel(String comments, String email) {
        this.comments = comments;
        this.email = email;
    }

    public CommentModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
