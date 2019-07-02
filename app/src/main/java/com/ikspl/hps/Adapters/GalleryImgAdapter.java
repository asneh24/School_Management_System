package com.ikspl.hps.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ikspl.hps.R;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class GalleryImgAdapter extends RecyclerView.Adapter<GalleryImgAdapter.MyViewHolder> {
    Context context;
    ArrayList<Blob> imgarray;
    Blob b;
    boolean isImageFitToScreen = false;
    public GalleryImgAdapter(Context context, ArrayList<Blob> arrayimg)
    {
        this.context = context;
        imgarray = arrayimg;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.galleryimgadapter,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        Log.e("MyTag","kkkfinalimg = "+imgarray.get(i));
        b = imgarray.get(i);
        int blobLength = 0;
        try {
            blobLength = (int) b.length();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        byte[] blobAsBytes = new byte[0];
        try {
            blobAsBytes = b.getBytes(1, blobLength);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
        myViewHolder.img.setImageBitmap(btm);

        myViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgarray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.academicsimg);
        }
    }
}
