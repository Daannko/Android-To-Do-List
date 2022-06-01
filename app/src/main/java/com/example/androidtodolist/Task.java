package com.example.androidtodolist;

import java.util.Date;

public class Task {

    private String title;
    private String description;
    private String category; // name of category
    private Date created;
    private Date doneDate;
    private Date finishTime;  // date of expi
    private Boolean done;
    private Boolean notificationStatus; // on or off
    private Boolean hidden;

    public Task(String title, String description, Date finishTime, String category) {
        this.title = title;
        this.description = description;
        this.finishTime = finishTime;
        this.category = category;
    }

    public Task() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
