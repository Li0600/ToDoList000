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


<<<<<<< HEAD
public class HomeFragment extends Fragment implements AdapterView.OnItemLongClickListener,TodoAdapter.OnDeleteClickListener{
    ListView listView;
    Handler handler;
//    ArrayAdapter<String> adapter;
    TodoAdapter adapter;
=======
public class HomeFragment extends Fragment implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{
    ListView listView;
    Handler handler;
    ArrayAdapter<String> adapter;
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
    private String detailStr;
    private static final String TAG=" HomeFragment ";
    private final List<String> list1=new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载视图
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        listView= view.findViewById(R.id.listView);

        // 绑定 Button 的点击事件
        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(v -> {
            Intent add = new Intent(getActivity(), addActivity.class);
            startActivityForResult(add, 3);
        });
<<<<<<< HEAD
        // 使用自定义适配器
        adapter = new TodoAdapter(getActivity(), R.layout.list_item, list1);
        adapter.setOnDeleteClickListener(this);  // 设置删除按钮监听
        listView.setAdapter(adapter);


=======
        for(int i=1;i<100;i++) {//循环向list1中添加了99个条目("item1"到"item99")
            list1.add("item" + i);
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        listView.setAdapter(adapter);

>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==6) {
                    List<String> data = (List<String>) msg.obj;
                    list1.clear();
                    list1.addAll(data);
                    adapter.notifyDataSetChanged();

<<<<<<< HEAD
=======
//                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list1);
//                    listView.setAdapter(adapter);
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
                }

            }
        };
        // 加载数据
        loadDataFromDatabase();
<<<<<<< HEAD
        listView.setOnItemLongClickListener(this);
=======
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        // Inflate the layout for this fragment
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
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

<<<<<<< HEAD


=======
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item=list1.get(position);
        int todoid=position+1;
        Log.i(TAG,"onItemClick:单击了:"+id+item);

        Intent edit=new Intent(getActivity(),editActivity.class);
        edit.putExtra("detail",item);
        edit.putExtra("id",todoid);
        startActivityForResult(edit,4);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // 实现长按删除等功能
        String item = list1.get(position);
        Log.i(TAG, "onItemLongClick: 长按了: " + item);
        // 从数据库中删除
        Thread t = new Thread(() -> {
            DBManager dbManager = new DBManager(getActivity());
            int todoId = position + 1;
            dbManager.delete(todoId);
            loadDataFromDatabase();
        });
        t.start();
        return true;
    }
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
    public void onClick(View view){
        Intent add=new Intent(getActivity(),addActivity.class);
        startActivityForResult(add,3);
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
<<<<<<< HEAD

    @Override
    public void onDeleteClick(int position) {
        // 处理删除逻辑
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
//        int todoid=position+1;
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
=======
}
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
