package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MineFragment extends Fragment implements View.OnClickListener{
   Button delete,reset_password,reset_username,out;
   ListView listView;
   private User nowuser;
   private static final String TAG="MineFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        delete = view.findViewById(R.id.delete);
        reset_password = view.findViewById(R.id.change_password);
        reset_username = view.findViewById(R.id.change_username);
        out=view.findViewById(R.id.out);
        delete.setOnClickListener(this);
        reset_username.setOnClickListener(this);
        reset_password.setOnClickListener(this);
        out.setOnClickListener(this);
        listView = view.findViewById(R.id.listview1);

        if (getActivity() instanceof MainActivity) {
            nowuser = ((MainActivity) getActivity()).getNowuser();
        }

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
                DBManager dbManager1=new DBManager(getActivity());
                // 删除用户
                dbManager.delete(nowuser.getPhone());
                //删除数据
                dbManager1.deleteAll();
                logout();
                Toast.makeText(getActivity(), "账号已删除", Toast.LENGTH_SHORT).show();
//

            }
        }
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
        else if(v.getId()==R.id.out){
            logout();
            Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();

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
    }
    // 退出登录
    private void logout() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                "UserPrefs", Activity.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false); // 清除登录标记
        editor.remove("phone");        // 清除用户信息
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        Intent intent = new Intent(getActivity(), enterActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish(); // 关闭 MainActivity，避免返回
        }

    }
}
