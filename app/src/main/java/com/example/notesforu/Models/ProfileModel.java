package com.example.notesforu.Models;

public class ProfileModel {
    String filename, fileurl;

    public ProfileModel(String filename, String fileurl) {
        this.filename = filename;
        this.fileurl = fileurl;
    }

    public ProfileModel() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
