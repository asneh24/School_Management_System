package com.ikspl.hps.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ikspl.hps.AdmissionFragment;
import com.ikspl.hps.CareerFragment;

public class EnquiryViewPagerAdapter extends FragmentPagerAdapter {
    public EnquiryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                AdmissionFragment admissionfragment = new AdmissionFragment();
                return admissionfragment;
            case 1:
                CareerFragment careerfragment = new CareerFragment();
                return careerfragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}