package com.senai.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;


public class CreateTodoActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        Toolbar toolbar = findViewById(R.id.toolbarCreate);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_criar) {

            Todo todo = new Todo(
                    editTextTitle.getText().toString(),
                    editTextDescription.getText().toString()
            );
            createTodo(todo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void createTodo(Todo todo) {
        FirebaseFirestore.getInstance().collection("/todolist")
                .add(todo)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Tarefa criada com sucesso", Toast.LENGTH_LONG).show();
                    finish();
                });
    }

}