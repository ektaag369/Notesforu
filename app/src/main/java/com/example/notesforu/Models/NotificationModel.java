package com.example.notesforu.Models;

public class NotificationModel {
    String notify;

    public NotificationModel(String notify) {
        this.notify = notify;
    }

    public NotificationModel() {
    }


    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
}
