package com.example.notesforu.Models;

public class UserModel {
    private String Id;
    private String Username;
    private String Fullname;
    String Email;

    public UserModel(String id, String username, String fullname, String email) {
        Id = id;
        Username = username;
        Fullname = fullname;
        Email = email;
    }

    public UserModel() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }
}
