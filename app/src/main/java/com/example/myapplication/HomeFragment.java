package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements AdapterView.OnItemLongClickListener,TodoAdapter.OnDeleteClickListener{
    ListView listView;
    Handler handler;
    TodoAdapter adapter;
    private String detailStr;
    private static final String TAG=" HomeFragment ";
    private final List<String> list1=new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        listView= view.findViewById(R.id.listView);


        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(v -> {
            Intent add = new Intent(getActivity(), addActivity.class);
            startActivityForResult(add, 3);
        });
        adapter = new TodoAdapter(getActivity(), R.layout.list_item, list1);
        adapter.setOnDeleteClickListener(this);
        listView.setAdapter(adapter);


        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==6) {
                    List<String> data = (List<String>) msg.obj;
                    list1.clear();
                    list1.addAll(data);
                    adapter.notifyDataSetChanged();

                }

            }
        };
        loadDataFromDatabase();
        listView.setOnItemLongClickListener(this);
        return view;
    }

    // 从数据库加载数据
    private void loadDataFromDatabase() {
        Thread t = new Thread(() -> {
            List<String> list = new ArrayList<>();
            DBManager dbManager = new DBManager(getActivity());
            for (Todo todo : dbManager.listAll()) {
                list.add(todo.getDetail());
            }
            Message msg = handler.obtainMessage(6, list);
            handler.sendMessage(msg);
        });
        t.start();
        Log.i(TAG, "loadDataFromDatabase: 开启子线程获取数据库中的数据");
    }




    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 5) {
            loadDataFromDatabase();
            Log.i(TAG, "onActivityResult: 从addActivity返回，刷新数据");
        }
        if (requestCode == 4 && resultCode == 6) {
            loadDataFromDatabase();
            Log.i(TAG, "onActivityResult: 从editActivity返回，刷新数据");
        }
}

    @Override
    public void onDeleteClick(int position) {
        String item = list1.get(position);
        Thread t = new Thread(() -> {
            DBManager dbManager = new DBManager(getActivity());
            List<Todo> todos=dbManager.listAll();
            int todoId = todos.get(position).getId();
            dbManager.delete(todoId);
            loadDataFromDatabase();
            Log.i(TAG, "onDeleteClick: 删除了: " + item);
        });
        t.start();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String item=list1.get(position);
        Log.d(TAG, "ListView item clicked");
        DBManager dbManager = new DBManager(getActivity());
        List<Todo> todos=dbManager.listAll();
        int todoid = todos.get(position).getId();
        Log.i(TAG,"onItemClick:单击了:"+id+item);
        Intent edit=new Intent(getActivity(),editActivity.class);
        edit.putExtra("detail",item);
        edit.putExtra("id",todoid);
        startActivityForResult(edit,4);
        return true;
    }
}
