package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ikspl.hps.Adapters.GalleryImgAdapter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentGalleryImage extends AppCompatActivity {

    RecyclerView recycleviewimg;
    GalleryImgAdapter adapter;
    ArrayList<Blob> img;
    Connection con;
    PreparedStatement preparedStatement;
    Statement st;
    ProgressBar progressBar;
    String un,pass,db,ip;
    Integer classid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_gallery_image);
        stopService(new Intent(getBaseContext(),Service1.class));

        progressBar = findViewById(R.id.progressbar);
        recycleviewimg = findViewById(R.id.recycleviewimg);
        recycleviewimg.hasFixedSize();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(StudentGalleryImage.this,LinearLayoutManager.HORIZONTAL,false );
        recycleviewimg.setLayoutManager(layoutManager);
        img = new ArrayList<>();

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016


        FetchDetails fetchDetails = new FetchDetails();
        fetchDetails.execute("");

    }


    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+server+"/"+database+";user="+user+";password="+password;
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

    public class FetchDetails extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            // HomeWorkModel hwm = new HomeWorkModel();
            Log.e("MyTag","kkkfetch = "+img);
            adapter = new GalleryImgAdapter(StudentGalleryImage.this,img);
            recycleviewimg.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
            //Log.e("MyTag","kkkfetch2 = "+hwm.getStuID());
            //Log.e("MyTag","kkkfetch3 = "+hwm.getUploadDate());
            //Log.e("MyTag","kkkfetch4 = "+hwm.getHomeWorkDetails());
        }
        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null)
                {
                    z = "Check Your Internet Access!";
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select UploadDate,ClassID,StuID,GalleryImg from Self_GellaryMst";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);


                    while(rs.next())
                    {
                        img.add(rs.getBlob(4));
                    }



                    isSuccess=true;
                    con.close();

                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;
        }
    }

}
