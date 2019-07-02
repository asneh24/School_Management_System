package com.ikspl.hps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Gallery extends AppCompatActivity {
    ImageView img,vdo;
    TextView txtimg,txtvdo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtimg = findViewById(R.id.textViewimg);
        txtvdo = findViewById(R.id.textViewvdo);

        img = findViewById(R.id.imageViewimg);
        vdo = findViewById(R.id.imageViewvdo);

        txtimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(Gallery.this,StudentGalleryImage.class);
                startActivity(ii);

            }
        });
        txtvdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(Gallery.this,StudentGalleryVideo.class);
                startActivity(ii);

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(Gallery.this,StudentGalleryImage.class);
                startActivity(ii);

            }
        });
        vdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(Gallery.this,StudentGalleryVideo.class);
                startActivity(ii);

            }
        });
    }
}