package com.example.barcode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;


public class TesCoding2 extends AppCompatActivity {
    private EditText timeTxt;
    private Button starBtn, cancelBtn;
    AlarmManager alarmManager;
    PendingIntent pi;
    private Button button2;

    private AlarmHandler alarmHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tes_coding2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        initializeAlarmManager();

        go();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });

//        alarmHandler = new AlarmHandler(this);
//        alarmHandler.setAlarmManager(); // Hanya mengatur alarm tanpa membatalkannya
//        Toast.makeText(this, "Alarm Set!", Toast.LENGTH_SHORT).show();
    }

    private void initializeViews() {
        timeTxt = (EditText) findViewById(R.id.timeTxt);
        starBtn = (Button) findViewById(R.id.starBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();

            }

        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarmManager != null) {
                    alarmManager.cancel(pi);
                }

            }

        });

    }


    private void initializeAlarmManager() {
        Intent intent = new Intent(this, ExecutableService.class);
        pi = PendingIntent.getService(this, 0, intent, 0);
        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
    }



    private void go() {
        int time = Integer.parseInt(timeTxt.getText().toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (02 * 1000), time * 1000, pi);
            Toast.makeText(TesCoding2.this, "Alarm set in " + time + " seconds", Toast.LENGTH_SHORT).show();
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + (02 * 1000), time * 1000, pi);
            Toast.makeText(TesCoding2.this, "Yes Alarm set in " + time + " seconds", Toast.LENGTH_SHORT).show();
        }
    }

}





//






