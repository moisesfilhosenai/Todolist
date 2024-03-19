package com.senai.todolist;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class FirebaseApi {

    private static final String DATABASE_NAME = "/todolist";
    private final Activity activity;
    private ListView listViewTodo;
    private TodoAdapter adapter;
    private List<Todo> todos;

    public FirebaseApi(Activity activity, ListView listViewTodo, TodoAdapter adapter) {
        this.activity = activity;
        this.listViewTodo = listViewTodo;
        this.adapter = adapter;
    }

    public FirebaseApi(Activity activity) {
        this.activity = activity;
    }

    public void getAllTodos() {
        todos = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .addSnapshotListener((value, error) -> {
                    List<DocumentChange> documentChanges = value.getDocumentChanges();

                    for (DocumentChange doc: documentChanges) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            Todo todo = doc.getDocument().toObject(Todo.class);
                            todos.add(todo);
                        }
                    }
                    adapter = new TodoAdapter(todos, activity.getApplicationContext());
                    listViewTodo.setAdapter(adapter);
                });
    }

    public Todo getTodoByPosition(int position) {
        return todos.get(position);
    }

    public void createTodo(Todo todo, String message) {
        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .add(todo)
                .addOnSuccessListener(documentReference -> {

                    todo.setId(documentReference.getId());
                    updateId(todo);

                    Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    activity.finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity.getApplicationContext(), "Erro ao criar tarefa", Toast.LENGTH_LONG).show();
                });
    }

    private void updateId(Todo todo) {
        FirebaseFirestore.getInstance().collection(DATABASE_NAME)
                .document(todo.getId())
                .set(todo)
                .addOnSuccessListener(aVoid-> {});
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

}
