package com.example.androidtodolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private String  id;
    private String title;
    private String description;
    private String category; // name of category
    private Date created;
    private Date doneDate;
    private Date toDoDate;
    private Boolean done;
    private Boolean notificationStatus; // on or off
    private Boolean hidden;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public Task(String id, String title, String description, Date toDoDate, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.toDoDate = toDoDate;
        this.category = category;
    }

    public Task() {
    }

    public Task(String  id ,String title, String description, String category, Date created, Date doneDate, Date toDoDate, Boolean done, Boolean notificationStatus, Boolean hidden) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.created = created;
        this.doneDate = doneDate;
        this.toDoDate = toDoDate;
        this.done = done;
        this.notificationStatus = notificationStatus;
        this.hidden = hidden;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDoneDate() {
        if(doneDate == null) return null;
        return sdf.format(doneDate);
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public String getToDoDate() {
        return sdf.format(toDoDate);
    }

    public void setToDoDate(Date toDoDate) {
        this.toDoDate = toDoDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
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

    public String getCreated() {
        return sdf.format(created);
    }

    public void setCreated(Date created) {
        this.created = created;
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
