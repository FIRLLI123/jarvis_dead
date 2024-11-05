package com.example.barcode;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Splashmanual extends AppCompatActivity {

    private int waktu_loading=1000;
    LinearLayout splash1, swipe;
    TextView jam, hari, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_manual);

        splash1 = (LinearLayout) findViewById(R.id.splash);
        swipe = (LinearLayout) findViewById(R.id.swipe);
        jam = (TextView) findViewById(R.id.jam);
        hari = (TextView) findViewById(R.id.hari);
        tanggal = (TextView) findViewById(R.id.tanggal);
        //4000=4 detik
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);

        splash1.startAnimation(animation);

        //setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                //setelah loading maka akan langsung berpindah ke home activity
//                Intent home=new Intent(Splashmanual.this, Login.class);
//                startActivity(home);
//                finish();

            }
        },waktu_loading);

        tanggal.setText(getCurrentDate());
        jam.setText(jamotomatis());
        hari.setText(getCurrentDateHari());


        swipe.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {



                    Intent i = new Intent(getApplicationContext(), LoginV2.class);

                    startActivity(i);
                    //String b = namasf2.getText().toString();

                    // Intent i = new Intent(getApplicationContext(), Marsu1.class);
                    //i.putExtra("namasf", "" +a+ "");

                    // startActivity(i);

                }

        });

    }



    public String getCurrentDateHari() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, ", Locale.getDefault());
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //hari.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;

    }

    public String getCurrentDate() {
        //SimpleDateFormat contoh1 = new SimpleDateFormat("yyyy/MM/dd");
        //String hariotomatis = contoh1.format(c.getTime());
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat contoh1 = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
//        int year, month, day;
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DATE);
        //SimpleDateFormat contoh1 = new SimpleDateFormat("EEEE, yyyy/MM/dd");

        String hariotomatis = contoh1.format(c.getTime());
        //hari.setText(hariotomatis);
        return hariotomatis;
        //return day +"/" + (month+1) + "/" + year;
        //return (month+1) +"/" + day + "/" + year;
        //return year + "/" + (month + 1) + "/" + day;

    }

    public String jamotomatis(){
        Calendar c1 = Calendar.getInstance();
        //SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy h:m:s a");
        //SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        String strdate1 = sdf1.format(c1.getTime());

//        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
//        sdf1.setTimeZone(tzInAmerica);
//        String strdate1 = sdf1.format(c1.getTime());
        return strdate1;


    }
}