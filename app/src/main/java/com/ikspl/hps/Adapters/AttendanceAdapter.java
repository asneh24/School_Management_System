package com.ikspl.hps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ikspl.hps.Models.AttendanceModel;
import com.ikspl.hps.R;

import java.util.ArrayList;

public class AttendanceAdapter extends BaseAdapter {
    Context context;
    ArrayList<AttendanceModel> arraylist;
    LayoutInflater inflater;

    public AttendanceAdapter(Context applicationContext,ArrayList<AttendanceModel> arraylist)
    {
        this.context = applicationContext;
        this.arraylist = arraylist;
        this.inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.attendanceadapter,null);
        TextView datee = convertView.findViewById(R.id.datess);
        TextView status = convertView.findViewById(R.id.status);

        datee.setText(arraylist.get(position).getDateee());
        status.setText(arraylist.get(position).getStatus());
        return convertView;
    }
}
