package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;

public class AlarmFragment extends Fragment implements View.OnClickListener{

    private PendingIntent pi;//用于指定时间触发activity
    private AlarmManager alarmManager;//用于设置和取消闹钟
    private static final String TAG="AlarmFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarm, container, false);
        bindView(view);
        return view;
    }

    public void bindView(View view){
        Button set=view.findViewById(R.id.set_alarm);
        Button cancel=view.findViewById(R.id.cancel_alarm);
        alarmManager=(AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getActivity(),ClockActivity.class);
        pi = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        set.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id=getId();
        if (id==R.id.set_alarm) {
            Calendar nowTime = Calendar.getInstance();
            //对话框
            new TimePickerDialog(getActivity(), 0, (view, hourOfDay, minute) -> {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                Log.i(TAG, "闹钟设置完毕");
                Toast.makeText(getActivity(), "闹钟设置完毕:" + c.getTimeInMillis(), Toast.LENGTH_SHORT).show();
            }, nowTime.get(Calendar.HOUR_OF_DAY), nowTime.get(Calendar.MINUTE), true).show();

        }else if(id==R.id.cancel_alarm){
                alarmManager.cancel(pi);
                Toast.makeText(getActivity(),"闹钟已取消",Toast.LENGTH_SHORT).show();
        }

    }

}