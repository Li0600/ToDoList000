package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alarm, container, false);
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
}