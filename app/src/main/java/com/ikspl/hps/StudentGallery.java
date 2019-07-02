package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentGallery extends AppCompatActivity {

    TextView txtviewstdname,txtimg,txtvdo;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname;
    ImageView img,vdo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_gallery);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");

        txtimg = findViewById(R.id.textViewimg);
        txtvdo = findViewById(R.id.textViewvdo);

        img = findViewById(R.id.imageViewimg);
        vdo = findViewById(R.id.imageViewvdo);

        txtimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(StudentGallery.this,StudentGalleryImage.class);
                ii.putExtra("username",username);
                startActivity(ii);

            }
        });
        txtvdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(StudentGallery.this,StudentGalleryVideo.class);
                ii.putExtra("username",username);
                startActivity(ii);

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentGallery.this,StudentGalleryImage.class);
                ii.putExtra("username",username);
                startActivity(ii);

            }
        });
        vdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentGallery.this,StudentGalleryVideo.class);
                ii.putExtra("username",username);
                startActivity(ii);

            }
        });


        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");
    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected void onPostExecute(String r)
        {

            if(isSuccess)
            {
                if(lastname == null)
                {
                    txtviewstdname.setText("Welcome "+firstname);
                }
                else
                {
                    txtviewstdname.setText("Welcome "+firstname+" "+lastname);
                }
            }
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
                    String query = "select first_name,last_name from Course_Student_Info where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        firstname = rs.getString(1);
                        lastname = rs.getString(2);
                        isSuccess=true;
                        con.close();
                    }
                    else
                    {
                        z = "Invalid Credentials!";
                        isSuccess = false;
                    }
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
}