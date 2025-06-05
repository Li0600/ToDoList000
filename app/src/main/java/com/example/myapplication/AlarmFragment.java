package com.example.myapplication;

<<<<<<< HEAD
import android.media.MediaPlayer;
=======
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
import android.os.Bundle;

import androidx.fragment.app.Fragment;

<<<<<<< HEAD
import android.os.CountDownTimer;
import android.text.TextUtils;
=======
>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
import android.widget.Toast;


public class AlarmFragment extends Fragment implements View.OnClickListener{
    private Button begin;
    private Button end;
    private EditText editText;
    private CountDownTimer timer;
    private MediaPlayer mediaPlayer;//用来播放音频
    private CountDownTimer timer1;
    private static final String TAG="AlarmFragment";
=======
import android.widget.Toast;
import java.util.Calendar;

public class AlarmFragment extends Fragment implements View.OnClickListener{

    private PendingIntent pi;//用于指定时间触发activity
    private AlarmManager alarmManager;//用于设置和取消闹钟
    private static final String TAG="AlarmFragment";


>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarm, container, false);
<<<<<<< HEAD
         begin=view.findViewById(R.id.begin);
         end=view.findViewById(R.id.end);
         editText=view.findViewById(R.id.time);
         begin.setOnClickListener(this);
         end.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        String timeStr=editText.getText().toString();
        if(id==R.id.begin){
            if(TextUtils.isEmpty(timeStr)){
                Toast.makeText(getActivity(),"请输入计时的时长",Toast.LENGTH_SHORT).show();
                return;
            }
            int time=Integer.parseInt(timeStr);
            timer=new CountDownTimer(time*60000,60000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    editText.setText("还剩"+millisUntilFinished/60000+"分钟");
                }

                @Override
                public void onFinish() {
                    editText.setText(" ");
                    Toast.makeText(getActivity(),"计时已经结束",Toast.LENGTH_SHORT).show();
                    playAlarmSound();

                }
            };
            timer.start();//开始计时器
            Toast.makeText(getActivity(),"计时已经开始",Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.end){
            if(TextUtils.isEmpty(timeStr)){
                Toast.makeText(getActivity(),"请输入计时的时长",Toast.LENGTH_SHORT).show();
                 return;
            }
            if(timer!=null){
                timer.cancel();
                timer=null;
                editText.setText(" ");
                Toast.makeText(getActivity(),"计时已经停止",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void playAlarmSound(){
        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(getActivity(),R.raw.alarm_sound);
            Log.i(TAG,"AlarmFragment:获取音频文件");
            mediaPlayer.setLooping(true);//设置循环播放
            mediaPlayer.start();
            timer1=new CountDownTimer(30000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Toast.makeText(getActivity(),"铃声还要响："+millisUntilFinished/1000+"秒",Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFinish() {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();  // 停止播放
                        mediaPlayer.release();  // 释放资源
                        mediaPlayer = null;
                    }
                    Toast.makeText(getActivity(),"闹铃已经停止",Toast.LENGTH_SHORT).show();
                }
            };
        }
        timer1.start();
    }
=======
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

>>>>>>> 88101029d059b0dc075f7947d1cdef9bb4092e6c
}