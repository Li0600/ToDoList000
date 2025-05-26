package com.example.myapplication;

import android.content.Intent;
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

public class editActivity extends AppCompatActivity {
    EditText editText;
    private static final String TAG="editActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText=findViewById(R.id.amending);
        Intent intent=getIntent();
        String detail=intent.getStringExtra("detail");;
        editText.setText(detail);
        editText.setSelection((detail.length()));

    }
    public void amend(View view){
        Log.i(TAG,"amend:amend()");
        Todo t=new Todo();
        String inputStr=editText.getText().toString();
        Log.i(TAG,"amend:editText"+inputStr);
        if(TextUtils.isEmpty(inputStr)){
            Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
            return;
        }
        t.detail=inputStr;
        int id = getIntent().getIntExtra("id", -1);
        t.id = id;
        DBManager dbManager=new DBManager(editActivity.this);
        dbManager.update(t);

        Intent relsutintent = getIntent();
        relsutintent.putExtra("detail",inputStr);
        setResult(6, relsutintent);
        finish();

    }
}