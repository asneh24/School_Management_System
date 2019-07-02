package com.ikspl.hps;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ikspl.hps.Adapters.EnquiryViewPagerAdapter;

public class Enquiry extends AppCompatActivity {

    ViewPager vp;
    private EnquiryViewPagerAdapter mpagerviewadapter;
    TextView admission,career;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        stopService(new Intent(getBaseContext(),Service1.class));

        vp = findViewById(R.id.viewpagerr);
        mpagerviewadapter = new EnquiryViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mpagerviewadapter);
        admission = findViewById(R.id.admission);
        career = findViewById(R.id.career);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0);
                admission.setTypeface(admission.getTypeface(), Typeface.BOLD);
            }
        });
        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });
    }
    private void changeTabs(int position) {

        if(position == 0){

            admission.setTypeface(admission.getTypeface(), Typeface.BOLD);
            career.setTypeface(career.getTypeface(), Typeface.NORMAL);
            career.setTextSize(16);
            admission.setTextSize(20);

        }

        if(position == 1){

            career.setTypeface(career.getTypeface(), Typeface.BOLD);
            admission.setTypeface(admission.getTypeface(), Typeface.NORMAL);
            admission.setTextSize(16);
            career.setTextSize(20);

        }
    }
}
