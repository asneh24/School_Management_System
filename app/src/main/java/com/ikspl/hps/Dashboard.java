package com.ikspl.hps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Dashboard extends AppCompatActivity {

    ViewFlipper simpleflipper;
    int[] images = {R.drawable.preschool, R.drawable.preschool5, R.drawable.preschool2, R.drawable.preschool3, R.drawable.preschool4};     // array of images
    ImageView imgabout,imgacademics,imggallery,imgenquiry,imgstudentlogin,imgcontactus;
    TextView txtabout,txtacademics,txtgallery,txtenquiry,txtstudentlogin,txtcontactus;
    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        stopService(new Intent(getBaseContext(),Service1.class));

        session = new UserSessionManager(getApplicationContext());
        simpleflipper = findViewById(R.id.flipper);

        for (int i = 0; i < images.length; i++) {
            // create the object of ImageView
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]); // set image in ImageView
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            simpleflipper.addView(imageView);
        }
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        simpleflipper.setInAnimation(in);
        simpleflipper.setOutAnimation(out);
        simpleflipper.setFlipInterval(2000);
        simpleflipper.setAutoStart(true);

        imgabout = findViewById(R.id.imageViewabout);
        imgacademics = findViewById(R.id.imageViewacademics);
        imggallery = findViewById(R.id.imageViewgallery);
        imgenquiry = findViewById(R.id.imageViewenquiry);
        imgstudentlogin = findViewById(R.id.imageViewlogin);
        imgcontactus = findViewById(R.id.imageViewcontactus);
        txtabout = findViewById(R.id.textViewabout);
        txtacademics = findViewById(R.id.textViewacademics);
        txtgallery = findViewById(R.id.textViewgallery);
        txtenquiry = findViewById(R.id.textViewenquiry);
        txtstudentlogin = findViewById(R.id.textViewlogin);
        txtcontactus = findViewById(R.id.textViewcontactus);

        imgabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Aboutt.class);
                startActivity(ii);

            }
        });
        txtabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Aboutt.class);
                startActivity(ii);

            }
        });

        imgacademics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Academic.class);
                startActivity(ii);

            }
        });
        txtacademics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Academic.class);
                startActivity(ii);

            }
        });

        imggallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Gallery.class);
                startActivity(ii);

            }
        });
        txtgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Gallery.class);
                startActivity(ii);

            }
        });

        imgenquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Enquiry.class);
                startActivity(ii);

            }
        });
        txtenquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,Enquiry.class);
                startActivity(ii);

            }
        });

        imgstudentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.checkLogin())
                {
                    Intent ii = new Intent(Dashboard.this,StudentLogin.class);
                    startActivity(ii);
                }
                else
                {
                    Intent ii = new Intent(Dashboard.this,StudentHomePage.class);
                    startActivity(ii);
                }


            }
        });
        txtstudentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.checkLogin())
                {
                    Intent ii = new Intent(Dashboard.this,StudentLogin.class);
                    startActivity(ii);
                }




            }
        });

        imgcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,ContactUs.class);
                startActivity(ii);

            }
        });
        txtcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Dashboard.this,ContactUs.class);
                startActivity(ii);

            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startService(new Intent(getBaseContext(),Service1.class));//start service
                        Dashboard.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}