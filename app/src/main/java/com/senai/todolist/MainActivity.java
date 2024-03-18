package com.senai.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Todo> todos;
    private ListView listViewTodo;
    private ArrayAdapter<Todo> adapter;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        database = new Database(this);

        this.listViewTodo = findViewById(R.id.listViewTodo);
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
        todos = new ArrayList<>();

        FirebaseFirestore.getInstance().collection("/todolist")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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

                            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, todos);
                            listViewTodo.setAdapter(adapter);
                            configureLongClick();
                        }
                    }
                });
    }

    private void configureLongClick() {
        listViewTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Todo todo = todos.get(position);
                openDialog(todo);

                return true;
            }
        });
    }

    private void openDialog(Todo todo) {
        String mensagem = String.format("A tarefa %s será removida, deseja continuar ?", todo.getTitle());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover tarefa ?");
        builder.setMessage(mensagem);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                database.removeTodo(todo, "Tarefa removida com sucesso");
                dialog.dismiss();
                getAllTodos();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}