package com.senai.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Todo> todos = new ArrayList<>();
    private ListView listViewTodo;
    private ArrayAdapter<Todo> adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        this.listViewTodo = findViewById(R.id.listViewTodo);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.getAllTodos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_adicionar) {
            startActivity(new Intent(this, CreateTodoActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllTodos() {
        FirebaseFirestore.getInstance().collection("/todolist")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        if (value != null) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();

                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Todo todo = doc.getDocument().toObject(Todo.class);
                                    todos.add(todo);
                                }
                            }

                            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, todos);
                            listViewTodo.setAdapter(adapter);
                        }
                    }
                });
    }


}