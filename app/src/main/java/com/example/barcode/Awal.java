package com.example.barcode;

import static junit.framework.Assert.assertEquals;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class Awal extends AppCompatActivity {
ScrollView scroll1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal);

        scroll1 = (ScrollView) findViewById(R.id.scroll);
        //scroll1.fullScroll(View.FOCUS_DOWN);
        scroll1.setSmoothScrollingEnabled(true);
        scroll1.smoothScrollTo(0,0);
        scroll1.smoothScrollBy(0,5);

        ValueAnimator realSmoothScrollAnimation =
                ValueAnimator.ofInt(scroll1.getScrollY(), 0);
        realSmoothScrollAnimation.setDuration(500);
        realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int scrollTo = (Integer) animation.getAnimatedValue();
                scroll1.scrollTo(0, scrollTo);
            }
        });

        realSmoothScrollAnimation.start();




    }




}