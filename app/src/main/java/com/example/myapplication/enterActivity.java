package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class enterActivity extends AppCompatActivity {
    private static final String TAG="enterActivity";

    EditText phone;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phone=findViewById(R.id.phone);
        username=findViewById(R.id.user);
        password=findViewById(R.id.password);
    }

    public void sign(View view) {
        Log.i(TAG, "sign:sign()");
        String n=phone.getText().toString();
        String u = username.getText().toString();
        String p = password.getText().toString();
        Log.i(TAG,"sig:phone:"+n);
        Log.i(TAG, "sign:username:" + u);
        Log.i(TAG, "sign:password:" + p);
        //判断内容是否为空
        if (TextUtils.isEmpty(n)||TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }
        // 手机号格式验证
        if (!isValidPhone(n)) {
            Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        // 密码长度验证
        if (p.length() < 6) {
            Toast.makeText(this, "密码长度至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User(n, u, p);
        DBManager_user dbManager=new DBManager_user(this);
        // 检查用户是否已存在
        if (dbManager.findByPhone(p) != null) {
            Toast.makeText(this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dbManager.add(user)) {
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "注册成功，已将用户信息添加到数据库");
            // 清空输入框
            phone.setText("");
            username.setText("");
            password.setText("");
        } else {
            Toast.makeText(this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "注册失败，数据库操作异常");
        }

    }
    public void login(View view){
        Log.i(TAG,"login:login()");
        String n=phone.getText().toString();
        String u=username.getText().toString();
        String p=password.getText().toString();
        if(TextUtils.isEmpty(n)||TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
            Toast.makeText(this, "电话号码，用户名和密码都不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        DBManager_user dbManager_user=new DBManager_user(enterActivity.this);
        User user=dbManager_user.findByPhone(n);
        if(user==null){
            Toast.makeText(this,"用户不存在，请先注册",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "User not found for username: " + n);
        }
        else if(!user.getUsername().equals(u) ||!user.getPassword().equals(p)){
            Toast.makeText(this,"密码错误或者用户名错误",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Password mismatch for user: " + n);
        }
        else{
            Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Login successful for user: " + " phone: " + user.getPhone()+",username"+user.getUsername()+",password"+user.getPassword());
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("phone",user.getPhone());
            intent.putExtra("username",user.getUsername());
            intent.putExtra("password",user.getPassword());
            startActivity(intent);
            finish();
        }
    }
    // 手机号格式验证
    private boolean isValidPhone(String phone) {
        // 简单的手机号格式验证，实际应用中可能需要更复杂的正则表达式
        return phone.matches("^1[3-9]\\d{9}$");
    }
}