package com.senai.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;


public class CreateTodoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        createTodo();
    }

    private void createTodo() {
        FirebaseFirestore.getInstance().collection("/todolist")
                .add(new Todo("Estudar programação", "Estudar programação para criar apps"))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "Todo criado", Toast.LENGTH_LONG).show();
                });
    }

}