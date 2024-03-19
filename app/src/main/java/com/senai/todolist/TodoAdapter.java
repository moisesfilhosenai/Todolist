package com.senai.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    private final List<Todo> todos;
    private final Context context;

    public TodoAdapter(List<Todo> todos, Context context) {
        this.todos = todos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
            Toast.makeText(context.getApplicationContext(), "Inflando", Toast.LENGTH_LONG).show();
        }
        Todo todo = todos.get(position);

        TextView todoTitle = (TextView) convertView.findViewById(R.id.textViewTodoTitle);
        TextView todoStatus = (TextView) convertView.findViewById(R.id.textViewTodoStatus);

        todoTitle.setText(todo.getTitle());
        todoStatus.setText(todo.getStatus().toString());

        return convertView;
    }
}
