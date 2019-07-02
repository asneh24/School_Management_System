package com.ikspl.hps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ikspl.hps.Adapters.AcademicsAdapter;

import java.util.ArrayList;

public class Academic extends AppCompatActivity {
    RecyclerView recycle;
    AcademicsAdapter adapter;
    ArrayList<Integer> arrayimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);
        stopService(new Intent(getBaseContext(),Service1.class));

        arrayimg = new ArrayList<>();
        arrayimg.add(0,R.drawable.primary);
        arrayimg.add(1,R.drawable.middle);
        arrayimg.add(2,R.drawable.secondary);
        arrayimg.add(3,R.drawable.senior);

        recycle = findViewById(R.id.recycle);
        recycle.hasFixedSize();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( Academic.this,LinearLayoutManager.VERTICAL,false );
        recycle.setLayoutManager(layoutManager);

        adapter = new AcademicsAdapter(Academic.this,arrayimg);
        recycle.setAdapter(adapter);
    }
}
