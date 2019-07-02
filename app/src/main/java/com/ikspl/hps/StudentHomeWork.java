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
import android.widget.TextView;

import com.ikspl.hps.Adapters.HomeWorkAdapter;
import com.ikspl.hps.Models.HomeWorkModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentHomeWork extends AppCompatActivity {

    TextView txtviewstdname;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname,admissionno,classname;
    Integer classid;
    RecyclerView recyclelist;
    HomeWorkAdapter homeWorkAdapter;
    ArrayList<HomeWorkModel> arraylist;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_work);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");
        recyclelist = findViewById(R.id.recyclelist);
        arraylist = new ArrayList<HomeWorkModel>();
        progressBar = findViewById(R.id.progressbar);


        recyclelist.hasFixedSize();
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager( StudentHomeWork.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recyclelist.setLayoutManager(layoutManager);

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        ClassMaster classMaster = new ClassMaster();
        classMaster.execute("");

        FetchDetails fetchDetails = new FetchDetails();
        fetchDetails.execute("");
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
                    String query = "select first_name,last_name,AdmissionNo from Course_Student_Info where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        firstname = rs.getString(1);
                        lastname = rs.getString(2);
                        admissionno = rs.getString(3);
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
            Log.e("MyTag","kkkfetch = "+arraylist);
            homeWorkAdapter = new HomeWorkAdapter(StudentHomeWork.this,arraylist,classname,firstname);
            recyclelist.setAdapter(homeWorkAdapter);
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
                    String query = "select UploadDate,ClassID,StuID,HomeWorkDetails from Self_HomeWorkMst";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while(rs.next())
                    {
                        if(rs.getInt(2) == 0)
                        {
                            if(rs.getString(3) == null)
                            {
                                HomeWorkModel hwm = new HomeWorkModel();
                                hwm.setUploadDate(rs.getDate(1));
                                hwm.setClassID(rs.getInt(2));
                                hwm.setStuID(rs.getString(3));
                                hwm.setHomeWorkDetails(rs.getString(4));
                                arraylist.add(hwm);
                                Log.e("MyTag","kkkfetch1 = "+rs.getDate(1));
                                Log.e("MyTag","kkkfetch2 = "+rs.getInt(2));
                                Log.e("MyTag","kkkfetch3 = "+rs.getString(3));
                                Log.e("MyTag","kkkfetch4 = "+rs.getString(4));
                            }
                            else
                            if(rs.getString(3 ) == admissionno)
                            {
                                HomeWorkModel hwm = new HomeWorkModel();
                                hwm.setUploadDate(rs.getDate(1));
                                hwm.setClassID(rs.getInt(2));
                                hwm.setStuID(rs.getString(3));
                                hwm.setHomeWorkDetails(rs.getString(4));
                                arraylist.add(hwm);
                                Log.e("MyTag","kkkfetch1 = "+rs.getDate(1));
                                Log.e("MyTag","kkkfetch2 = "+rs.getInt(2));
                                Log.e("MyTag","kkkfetch3 = "+rs.getString(3));
                                Log.e("MyTag","kkkfetch4 = "+rs.getString(4));
                            }
                        }
                        else
                        if(rs.getInt(2) == classid)
                        {
                            if(rs.getString(3) == null)
                            {
                                HomeWorkModel hwm = new HomeWorkModel();
                                hwm.setUploadDate(rs.getDate(1));
                                hwm.setClassID(rs.getInt(2));
                                hwm.setStuID(rs.getString(3));
                                hwm.setHomeWorkDetails(rs.getString(4));
                                arraylist.add(hwm);
                                Log.e("MyTag","kkkfetch1 = "+rs.getDate(1));
                                Log.e("MyTag","kkkfetch2 = "+rs.getInt(2));
                                Log.e("MyTag","kkkfetch3 = "+rs.getString(3));
                                Log.e("MyTag","kkkfetch4 = "+rs.getString(4));
                            }
                            else
                           // if(rs.getString(3 ) == admissionno)
                            {
                                HomeWorkModel hwm = new HomeWorkModel();
                                hwm.setUploadDate(rs.getDate(1));
                                hwm.setClassID(rs.getInt(2));
                                hwm.setStuID(rs.getString(3));
                                hwm.setHomeWorkDetails(rs.getString(4));
                                arraylist.add(hwm);
                                Log.e("MyTag","kkkfetch1 = "+rs.getDate(1));
                                Log.e("MyTag","kkkfetch2 = "+rs.getInt(2));
                                Log.e("MyTag","kkkfetch3 = "+rs.getString(3));
                                Log.e("MyTag","kkkfetch4 = "+rs.getString(4));
                            }
                        }

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
