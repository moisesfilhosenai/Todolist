package com.senai.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
        }
        Todo todo = todos.get(position);

        TextView todoTitle = (TextView) convertView.findViewById(R.id.textViewTodoTitle);
        TextView todoCreatedAt = (TextView) convertView.findViewById(R.id.textViewTodoCreateAt);

         if (todo.getStatus() == Status.DOING) {
            todoTitle.setTextColor(context.getColor(R.color.orange));
        } else if (todo.getStatus() == Status.DONE) {
             todoTitle.setTextColor(context.getColor(R.color.green));
        }

        todoTitle.setText(todo.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(todo.getCreatedAt());

        todoCreatedAt.setText(dataFormatada);

        return convertView;
    }
}
