package com.senai.todolist;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Database {

    private static final String DATABASE_NAME = "/todolist";
    private final Activity activity;

    public Database(Activity activity) {
        this.activity = activity;
    }

    public void createTodo(Todo todo, String message) {
        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .add(todo)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    activity.finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity.getApplicationContext(), "Erro ao criar tarefa", Toast.LENGTH_LONG).show();
                });
    }

    public void removeTodo(Todo todo, String message) {
        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .document(todo.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity.getApplicationContext(), "Erro ao remover tarefa", Toast.LENGTH_LONG).show();
                });
    }

    private List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(activity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        if (value != null) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();

                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String documentId = doc.getDocument().getId();
                                    Todo todo = doc.getDocument().toObject(Todo.class);
                                    todo.setId(documentId);
                                    todos.add(todo);
                                }
                            }
                        }
                    }
                });
        return todos;
    }
}
