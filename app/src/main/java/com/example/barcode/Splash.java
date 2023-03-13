package com.example.barcode;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splash extends AppCompatActivity {
    private int waktu_loading=1000;
    ImageView splash1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splash1 = (ImageView) findViewById(R.id.splash);
        //4000=4 detik
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);

        splash1.startAnimation(animation);

            //setContentView(R.layout.splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {




                    //setelah loading maka akan langsung berpindah ke home activity
                    Intent home=new Intent(Splash.this, Splashmanual.class);
                    startActivity(home);
                    finish();

                }
            },waktu_loading);


    }
}