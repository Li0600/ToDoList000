package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private AlarmFragment alarmFragment;
    private MineFragment mineFragment;
    private User nowuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 获取从登录页面传递过来的用户信息
        Intent intent=getIntent();
        if (intent != null) {
            String phone=intent.getStringExtra("phone");
            String username=intent.getStringExtra("username");
            String password=intent.getStringExtra("password");

            nowuser=new User(phone, username, password);
        }

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        selcetedFragment(0);
        //点击切换事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("Navigation", "Item clicked: " + item.getItemId());
                if(item.getItemId()==R.id.home){
                    selcetedFragment(0);
                }
                else if(item.getItemId()==R.id.alarm){
                    selcetedFragment(1);
                }
                else if(item.getItemId()==R.id.mine){
                    selcetedFragment(2);
                }
                return true;
            }
        });


    }
    private void selcetedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if (position == 0) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.content, homeFragment);
            } else {
                fragmentTransaction.show(homeFragment);
            }
        }
        if (position == 1) {
            if (alarmFragment== null) {
                alarmFragment = new AlarmFragment();
                fragmentTransaction.add(R.id.content, alarmFragment);
            } else {
                fragmentTransaction.show(alarmFragment);
            }
        }
        if (position == 2) {
            if (mineFragment == null) {
                mineFragment = new MineFragment();
                fragmentTransaction.add(R.id.content, mineFragment);
            } else {
                fragmentTransaction.show(mineFragment);
            }

        }
        //一定要提交
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (alarmFragment != null) {
            fragmentTransaction.hide(alarmFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }
    // 获取当前登录用户
    public User getNowuser() {
        return nowuser;
    }
}