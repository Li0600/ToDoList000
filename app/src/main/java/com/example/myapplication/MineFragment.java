package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.annotation.Nullable;
=======
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends Fragment implements View.OnClickListener{
<<<<<<< HEAD
   Button delete,reset_password,reset_username;
   ListView listView;
   private User nowuser;
   private static final String TAG="MineFragment";
=======
   Button delete;
   ListView listView;
   Handler handler;
   private User nowuser;
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        delete = view.findViewById(R.id.delete);
<<<<<<< HEAD
        reset_password = view.findViewById(R.id.change_password);
        reset_username = view.findViewById(R.id.change_username);
        delete.setOnClickListener(this);
        reset_username.setOnClickListener(this);
        reset_password.setOnClickListener(this);
=======
        delete.setOnClickListener(this);
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
        listView = view.findViewById(R.id.listview1);

        // 获取当前登录用户信息
        if (getActivity() instanceof MainActivity) {
            nowuser = ((MainActivity) getActivity()).getNowuser();
        }

        // 如果获取到用户信息，显示在ListView中
        if (nowuser != null) {
            List<String> list = new ArrayList<>();
            list.add("电话号码: " + nowuser.getPhone());
            list.add("用户名: " + nowuser.getUsername());
            list.add("密码: " + nowuser.getPassword());
            ListAdapter adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter2);
        }
        return view;

    }

    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            if (nowuser != null && getActivity() != null) {
                DBManager_user dbManager=new DBManager_user(getActivity());
<<<<<<< HEAD
                DBManager dbManager1=new DBManager(getActivity());
                // 删除用户
                dbManager.delete(nowuser.getPhone());
                //删除数据
                dbManager1.deleteAll();
=======
                // 删除用户
                dbManager.delete(nowuser.getPhone());
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
                Toast.makeText(getActivity(), "账号已删除", Toast.LENGTH_SHORT).show();
                // 跳转到登录页面
                Intent intent=new Intent(getActivity(), enterActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        }
<<<<<<< HEAD
        else if(v.getId()==R.id.change_password){
            Intent intent=new Intent(getActivity(), ResetPasswordActivity.class);
            intent.putExtra("phone",nowuser.getPhone());
            intent.putExtra("username",nowuser.getUsername());
            startActivityForResult(intent,1);

        }
        else if(v.getId()==R.id.change_username){
            Intent intent=new Intent(getActivity(), ResetUsernameActivity2.class);
            intent.putExtra("phone",nowuser.getPhone());
            intent.putExtra("password",nowuser.getPassword());
            startActivityForResult(intent,2);

        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            Bundle bundle1=data.getExtras();
            String phone=bundle1.getString("phone");
            String username=bundle1.getString("username");
            String new_password=bundle1.getString("new_password");
            User user=new User(phone,username,new_password);
            List<String> list1 = new ArrayList<>();
            list1.add("电话号码: " + user.getPhone());
            list1.add("用户名: " + user.getUsername());
            list1.add("密码: " + user.getPassword());
            ListAdapter adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
            listView.setAdapter(adapter3);
            Log.i(TAG, "onActivityResult: 从ResetPasswordActivity返回，刷新数据");
        }
        if (requestCode == 2 && resultCode == 4) {
            Bundle bundle1=data.getExtras();
            String phone=bundle1.getString("phone");
            String new_username=bundle1.getString("new_username");
            String password=bundle1.getString("password");
            User user=new User(phone,new_username,password);
            List<String> list2 = new ArrayList<>();
            list2.add("电话号码: " + user.getPhone());
            list2.add("用户名: " + user.getUsername());
            list2.add("密码: " + user.getPassword());
            ListAdapter adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
            listView.setAdapter(adapter4);
            Log.i(TAG, "onActivityResult: 从ResetPasswordActivity返回，刷新数据");
        }
=======
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
    }
}