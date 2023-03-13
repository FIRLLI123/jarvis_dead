package com.example.barcode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class FAQ extends AppCompatActivity {
Button dropdown1;
boolean isup;
LinearLayout satu1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        dropdown1 = (Button) findViewById(R.id.dropdown);
        satu1 = (LinearLayout) findViewById(R.id.satu);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        //dropdown1.setText("drop up");
        //isup= false;




        dropdown1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if(satu1.getVisibility() == View.GONE) {
                    //isup = false;
                    satu1.animate()
                            .translationYBy(120)
                            .translationY(0)
                            .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
                } else {
                    //isup = true;
                    satu1.animate()
                            .translationYBy(0)
                            .translationY(120)
                            .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
                }


//                if (satu1.getVisibility() == View.GONE) {
//                    satu1.animate()
//                            .translationY(satu1.getHeight()).alpha(1.0f)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationStart(Animator animation) {
//                                    super.onAnimationStart(animation);
//                                    satu1.setVisibility(View.VISIBLE);
//                                    //satu1.setAlpha(0.0f);
//                                }
//                            });
//                } else {
//                    satu1.animate()
//                            .translationY(0).alpha(0.0f)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    satu1.setVisibility(View.GONE);
//                                }
//                            });
//                }



//                if(dropdown1.getText().toString().equals("DROP DOWN")){
//                    slideDown(satu1);
//                    dropdown1.setText("DROP UP");
//                }else{
//                    slideUp(satu1);
//                    dropdown1.setText("DROP DOWN");
//                }


            }

        });


//        if (satu1.getVisibility() == View.GONE) {
//            satu1.animate()
//                    .translationY(satu1.getHeight()).alpha(1.0f)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            satu1.setVisibility(View.VISIBLE);
//                            satu1.setAlpha(0.0f);
//                        }
//                    });
//        } else {
//            satu1.animate()
//                    .translationY(0).alpha(0.0f)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            satu1.setVisibility(View.GONE);
//                        }
//                    });
//        }



    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


}