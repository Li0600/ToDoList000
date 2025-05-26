package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText=findViewById(R.id.new_password);
        button=findViewById(R.id.sure);
    }
    public void sure1(View view){
        Intent intent=getIntent();
        String phone=intent.getStringExtra("phone");
        String username=intent.getStringExtra("username");
        String new_password=editText.getText().toString();
        // 密码长度验证
        if (new_password.length() < 6) {
            Toast.makeText(this, "密码长度至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User(phone,username,new_password);
        DBManager_user dbManager_user=new DBManager_user(this);
        dbManager_user.update(user);
        Intent relsutintent = getIntent();
        Bundle bundle=new Bundle();
        bundle.putString("phone",user.getPhone());
        bundle.putString("uername",user.getUsername());
        bundle.putString("new_password",user.getPassword());
        relsutintent.putExtras(bundle);
        setResult(3, relsutintent);
        finish();
    }
}