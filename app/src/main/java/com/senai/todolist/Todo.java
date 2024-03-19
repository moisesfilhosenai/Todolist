package com.senai.todolist;

public class Todo {
    private String id;
    private String title;
    private String description;

    public  Todo() {}

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
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


    @Override
    public String toString() {
        return this.title;
    }
}
