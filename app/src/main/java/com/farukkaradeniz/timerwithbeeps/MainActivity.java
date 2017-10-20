package com.farukkaradeniz.timerwithbeeps;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

public class MainActivity extends AppCompatActivity implements CountdownView.OnCountdownIntervalListener {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private final DecimalFormat decimalFormat = new DecimalFormat("00");
    @BindView(R.id.countdownView) CountdownView countdownView;
    @BindView(R.id.edt_minute) EditText editMinute;
    @BindView(R.id.edt_freq) EditText editFreq;
    @BindView(R.id.btn_pause_resume) ToggleButton pauseResume;
    @BindView(R.id.btn_start_reset) ToggleButton startReset;
    private long time;
    private int frequence;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pauseResume.setEnabled(false);
        startReset.setEnabled(false);
    }

    @OnCheckedChanged(R.id.btn_start_reset)
    public void startReset(){
        countdownView.start(time);
        pauseResume.setChecked(false);
    }

    @OnCheckedChanged(R.id.btn_pause_resume)
    public void pauseResume(){
        if (pauseResume.isChecked()) {
            countdownView.pause();
        }else{
            countdownView.restart();
        }
    }

    @OnClick(R.id.btn_set_timer)
    public void setTimer(){
        String minute = editMinute.getText().toString();
        String freq = editFreq.getText().toString();

        try {
            int min = Integer.parseInt(minute);
            Date date = simpleDateFormat.parse(decimalFormat.format(min) + ":" + "00");
            time = date.getTime();
            frequence = Integer.parseInt(freq) * 1000 - 30;
            countdownView.setOnCountdownIntervalListener(frequence, this);
            countdownView.updateShow(time);
            pauseResume.setEnabled(true);
            startReset.setEnabled(true);
        } catch (ParseException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInterval(CountdownView cv, long remainTime){
        player = MediaPlayer.create(this, R.raw.beep);
        player.start();
    }
}
