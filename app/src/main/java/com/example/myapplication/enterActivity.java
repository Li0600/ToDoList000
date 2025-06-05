package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class enterActivity extends AppCompatActivity {
    private static final String TAG="enterActivity";

    private static final String prefs_name="UserPrefs";
    private static final String key_logged_in="isLoggedIn";
    private static final String key_password="password";
    private static final String key_username="username";
    private static final String key_phone="phone";

    EditText phone;
    EditText username;
    EditText password;
    SharedPreferences sharedPreferences;

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
        sharedPreferences=getSharedPreferences(prefs_name, Activity.MODE_PRIVATE);
        checkLoggedIn();
    }

    public void sign(View view) {
        Log.i(TAG, "sign:sign()");
        String n=phone.getText().toString();
        String u = username.getText().toString();
        String p = password.getText().toString();
        Log.i(TAG,"sig:phone:"+n);
        Log.i(TAG, "sign:username:" + u);
        Log.i(TAG, "sign:password:" + p);

        if (TextUtils.isEmpty(n)||TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }

        if (!isValidPhone(n)) {
            Toast.makeText(this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (p.length() < 6) {
            Toast.makeText(this, "密码长度至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User(n, u, p);
        DBManager_user dbManager=new DBManager_user(this);
        if (dbManager.findByPhone(p) != null) {
            Toast.makeText(this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dbManager.add(user)) {
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "注册成功，已将用户信息添加到数据库");
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
            saveLoggedState(user.getPhone(),user.getUsername(),user.getPassword());
            ToMainActivity(user.getPhone(),user.getUsername(),user.getPassword());

        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^1[3-9]\\d{9}$");
    }
    private void saveLoggedState(String phone,String username,String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key_logged_in,true);
        editor.putString(key_phone,phone);
        editor.putString(key_username,username);
        editor.putString(key_password,password);
        editor.apply();
        Log.i(TAG,"登陆信息已经保存");
    }
    private void checkLoggedIn(){
        boolean isLoggedIn=sharedPreferences.getBoolean(key_logged_in,false);
        if(isLoggedIn){
            String savePhone=sharedPreferences.getString(key_phone,"");
            String saveUsername=sharedPreferences.getString(key_username,"");
            String savePassword=sharedPreferences.getString(key_password,"");
            Log.i(TAG, "自动登录: phone=" + savePhone + ", username=" + saveUsername+",password");
            ToMainActivity(savePhone,saveUsername,savePassword);
        }
    }

    private void ToMainActivity(String phone,String username,String password){
        Intent intent1=new Intent(this,MainActivity.class);
        intent1.putExtra("phone",phone);
        intent1.putExtra("username",username);
        intent1.putExtra("password",password);
        startActivity(intent1);
        finish();
    }
}