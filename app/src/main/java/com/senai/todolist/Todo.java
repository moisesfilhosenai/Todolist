package com.senai.todolist;

import java.util.Date;

public class Todo {
    private String id;
    private String title;
    private String description;
    private Date createdAt;
    private Status status;

    public  Todo() {}

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.title + " | " + this.status;
    }
}
