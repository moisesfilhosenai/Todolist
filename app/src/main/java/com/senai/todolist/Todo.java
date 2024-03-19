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
        this.status = Status.TODO;
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

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.title + " | " + this.status;
    }
}
