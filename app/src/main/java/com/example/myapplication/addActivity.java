package com.example.myapplication;

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
import androidx.lifecycle.ViewTreeViewModelKt;

import java.util.ArrayList;
import java.util.List;

public class addActivity extends AppCompatActivity {
    EditText editText;
    private static final String TAG="addActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void save(View view){
        Log.i(TAG,"save:save()");
        Todo t=new Todo();
        editText=findViewById(R.id.edit);
        String inputStr=editText.getText().toString();
        Log.i(TAG,"save:editText"+inputStr);
        if(TextUtils.isEmpty(inputStr)){
            Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
            return;
        }
        t.detail=inputStr;
        DBManager dbManager=new DBManager(addActivity.this);
        dbManager.add(t);

        Intent intent = getIntent();
//        intent.putExtra("detail",inputStr);
        setResult(5, intent);
        finish();

    }
}