package com.senai.todolist;

import java.util.Date;

public class Todo {
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
        return this.title;
    }
}
