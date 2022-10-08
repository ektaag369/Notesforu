package com.example.notesforu.Models;

public class FileInfoModel {

    String filename, fileurl,fileabout,filedescription;
    public boolean isVisible;

    public FileInfoModel() {
    }

    public FileInfoModel(String filename, String fileurl, String fileabout, String filedescription) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.fileabout = fileabout;
        this.filedescription = filedescription;
        this.isVisible = isVisible;
    }

    public String getFileabout() {
        return fileabout;
    }

    public void setFileabout(String fileabout) {
        this.fileabout = fileabout;
    }

    public String getFiledescription() {
        return filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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
