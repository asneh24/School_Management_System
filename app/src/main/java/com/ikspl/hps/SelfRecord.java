package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfRecord extends AppCompatActivity {

    TextView txtviewdata,txtviewstdname;
    Connection con;
    ImageView imgpic;
    Blob b;
    String un,pass,db,ip,username,firstname,lastname;
    Date admissiondate,dateofbirth;
    Integer classid;
    String classs,admissionnum,gender,category,medium,fathername,mothername,status,presentaddress,contactnum,permanentaddress,percontactnum,classname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_record);
        txtviewdata = findViewById(R.id.data);
        txtviewstdname = findViewById(R.id.txtviewstdname);
        imgpic = findViewById(R.id.imgpic);
        stopService(new Intent(getBaseContext(),Service1.class));

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        ClassMaster classMaster = new ClassMaster();
        classMaster.execute("");


        Getdata dataa = new Getdata();
        dataa.execute("");
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

    public class ClassMaster extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected void onPostExecute(String r)
        {
            Log.e("MyTag","kkkclassid = "+classid);
            Log.e("MyTag","kkkclassname = "+classname);
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
                    String query = "select CourseMaster.Course_id,CourseMaster.CourseName from Course_Student_Info INNER JOIN CourseMaster ON dbo.Course_Student_Info.dygb_id = dbo.CourseMaster.Course_id where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        classid = rs.getInt(1);
                        classname = rs.getString(2);
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

    public class Getdata extends AsyncTask<String,String,String>
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
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                if(lastname == null )
                {
                    txtviewdata.setText("Name :- "+firstname+" \n \n"+"Class :- "+classname+" \n \n"+"Admission Date :- "+sdf.format(admissiondate)+"\n \n"+
                            "Admission Number :- "+admissionnum+"\n \n"+"Date of Birth :- "+sdf.format(dateofbirth)+"\n \n"+"Gender :- "+gender+"\n \n"+"Category :- "+category
                            +"\n \n"+"Medium :- "+medium+"\n \n"+"Father Name :- "+fathername+"\n \n"+"Mother Name :- "+mothername+"\n \n"+"Status :- "+status
                            +"\n \n"+"Present Address :- "+presentaddress+"\n \n"+"Contact Number :- "+contactnum+"\n \n"+"Permanent Address :- "+permanentaddress
                            +"\n \n"+"Contact Number :- "+percontactnum);
                }
                else
                {
                    txtviewdata.setText("Name :- "+firstname+" "+lastname+"\n \n"+"Class :- "+classname+"\n \n"+"Admission Date :- "+sdf.format(admissiondate)+"\n \n"+
                            "Admission Number :- "+admissionnum+"\n \n"+"Date of Birth :- "+sdf.format(dateofbirth)+"\n \n"+"Gender :- "+gender+"\n \n"+"Category :- "+category
                            +"\n \n"+"Medium :- "+medium+"\n \n"+"Father Name :- "+fathername+"\n \n"+"Mother Name :- "+mothername+"\n \n"+"Status :- "+status
                            +"\n \n"+"Present Address :- "+presentaddress+"\n \n"+"Contact Number :- "+contactnum+"\n \n"+"Permanent Address :- "+permanentaddress
                            +"\n \n"+"Contact Number :- "+percontactnum);
                }


                Log.e("MyTag","kkkB = "+b);
                if(b == null)
                {
                    imgpic.setBackgroundResource(R.drawable.hpslogo);
                }
                else
                {
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
                    imgpic.setImageBitmap(btm);
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
                    String query = "select * from Course_Student_Info where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        firstname = rs.getString(6);
                        lastname = rs.getString(8);
                        classs = rs.getString(42);
                        admissiondate = rs.getDate(2);
                        admissionnum = rs.getString(71);
                        dateofbirth = rs.getDate(10);
                        gender = rs.getString(9);
                        category = rs.getString(20);
                        medium="";
                        fathername = rs.getString(11);
                        mothername = rs.getString(12);
                        status = rs.getString(41);
                        presentaddress = rs.getString(17);
                        contactnum = rs.getString(15);
                        permanentaddress = rs.getString(18);
                        percontactnum = rs.getString(16);
                        b = rs.getBlob(19);
                        Log.e("MyTag","kkkimagepic = "+rs.getBlob(19));

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
}