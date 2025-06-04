package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import androidx.annotation.Nullable;



public class TodoAdapter extends ArrayAdapter<String> {
    private int resourceId;
    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public TodoAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.todoText = convertView.findViewById(R.id.todo_text);
            holder.deleteButton = convertView.findViewById(R.id.delete_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String todoItem = getItem(position);
        if (todoItem != null) {
            holder.todoText.setText(todoItem);
        }
        holder.deleteButton.setFocusable(false); // 关键：允许父布局接收点击事件

        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView todoText;
        ImageButton deleteButton;
    }
}
