package com.senai.todolist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CreateTodoActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerOptions;
    private Todo todo = new Todo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        spinnerOptions = findViewById(R.id.spinnerOptions);

        Toolbar toolbar = findViewById(R.id.toolbarCreate);
        setSupportActionBar(toolbar);

        configureSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_criar) {
            todo.setTitle(editTextTitle.getText().toString().toUpperCase());
            todo.setDescription(editTextDescription.getText().toString().toUpperCase());
            todo.setCreatedAt(new Date());

            FirebaseApi firebaseApi = new FirebaseApi(this);
            firebaseApi.createTodo(todo, "Tarefa criada com sucesso");
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureSpinner() {
        List<Status> options = new ArrayList<>();
        options.add(Status.TODO);
        options.add(Status.DOING);
        options.add(Status.DONE);

        ArrayAdapter<Status> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status selectedItem = (Status) parent.getItemAtPosition(position);
                todo.setStatus(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação a ser realizada quando nada for selecionado
            }
        });
    }

}