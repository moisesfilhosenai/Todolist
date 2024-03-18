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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTodo;
    private ArrayAdapter<Todo> adapter;
    private FirebaseApi firebaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        listViewTodo = findViewById(R.id.listViewTodo);
        firebaseApi = new FirebaseApi(this, listViewTodo, adapter);
        firebaseApi.getAllTodos();
        configureLongClick();
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

    private void configureLongClick() {
        listViewTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = firebaseApi.getTodoByPosition(position);
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
                firebaseApi.removeTodo(todo, "Tarefa removida com sucesso");
                firebaseApi.getAllTodos();
                dialog.dismiss();
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