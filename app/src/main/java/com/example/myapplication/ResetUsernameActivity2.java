package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResetUsernameActivity2 extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_username2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText=findViewById(R.id.new_username);
        button=findViewById(R.id.sure2);
    }
    public void sure2(View view){
        Intent intent=getIntent();
        String phone=intent.getStringExtra("phone");
        String password=intent.getStringExtra("password");
        String new_username=editText.getText().toString();
        User user=new User(phone,new_username,password);
        DBManager_user dbManager_user=new DBManager_user(this);
        dbManager_user.update(user);
        Intent relsutintent = getIntent();
        Bundle bundle=new Bundle();
        bundle.putString("phone",user.getPhone());
        bundle.putString("new_uername",user.getUsername());
        bundle.putString("password",user.getPassword());
        relsutintent.putExtras(bundle);
        setResult(4, relsutintent);
        finish();
    }
}