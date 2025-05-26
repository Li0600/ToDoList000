package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
   Button delete;
   ListView listView;
   Handler handler;
   private User nowuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        delete = view.findViewById(R.id.delete);
        delete.setOnClickListener(this);
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
                // 删除用户
                dbManager.delete(nowuser.getPhone());
                Toast.makeText(getActivity(), "账号已删除", Toast.LENGTH_SHORT).show();
                // 跳转到登录页面
                Intent intent=new Intent(getActivity(), enterActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        }
    }
}