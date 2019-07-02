package com.ikspl.hps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    ImageView logo;
    TextView heading,subheading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.heading);
        subheading = findViewById(R.id.subheading);
        stopService(new Intent(getBaseContext(),Service1.class));

        AnimationSet mset = new AnimationSet(false);
        Animation fadein = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_in);
        Animation fadeout = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_out);
        mset.addAnimation(fadein);
        mset.addAnimation(fadeout);
        mset.addAnimation(fadein);
        logo.startAnimation(mset);
        heading.startAnimation(fadein);
        subheading.startAnimation(fadein);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
