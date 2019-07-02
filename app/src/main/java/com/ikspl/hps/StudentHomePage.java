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
import java.util.HashMap;

public class StudentHomePage extends AppCompatActivity {

    TextView txtviewstdname,txtviewselfrecord, txtviewattendance, txtviewnoticeboard, txtviewhomework, txtviewfee, txtviewleaverequest, txtviewevent, txtviewfeedback;
    ImageView imgselfrecord,imgattendance,imgnoticeboard,imghomework,imgfee,imgleaverequest,imgevent,imgfeedback,imglogout;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname;
    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        stopService(new Intent(getBaseContext(),Service1.class));

        session = new UserSessionManager(getApplicationContext());
        txtviewstdname = findViewById(R.id.txtviewstdname);
        txtviewselfrecord = findViewById(R.id.textViewselfrecord);
        txtviewattendance = findViewById(R.id.textViewattendance);
        txtviewnoticeboard = findViewById(R.id.textViewNoticeBoard);
        txtviewhomework = findViewById(R.id.textViewHomeWork);
        txtviewfee = findViewById(R.id.textViewFee);
        txtviewleaverequest = findViewById(R.id.textViewLeaveRequest);
        txtviewevent = findViewById(R.id.textViewEvent);
        txtviewfeedback = findViewById(R.id.textViewFeedBack);
        imgselfrecord = findViewById(R.id.imageViewselfrecord);
        imgattendance = findViewById(R.id.imageViewattendance);
        imgnoticeboard = findViewById(R.id.imageViewnoticeboard);
        imghomework = findViewById(R.id.imageViewHomeWork);
        imgfee = findViewById(R.id.imageViewFee);
        imgleaverequest = findViewById(R.id.imageViewLeaveRequest);
        imgevent = findViewById(R.id.imageViewEvent);
        imgfeedback = findViewById(R.id.imageViewFeedBack);
        imglogout = findViewById(R.id.logout);

        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016

        HashMap<String, String> user = session.getUserDetails();

        // get name
        username = user.get(UserSessionManager.KEY_NAME);



        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        imgselfrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,SelfRecord.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewselfrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,SelfRecord.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
        imgattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,AttendanceRecord.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,AttendanceRecord.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        imgnoticeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentNoticeBoard.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewnoticeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentNoticeBoard.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
        imghomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentHomeWork.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewhomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentHomeWork.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
        imgfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentFee.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentFee.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
        imgleaverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentLeaveRequest.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewleaverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentLeaveRequest.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
        imgfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentFeedback.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentFeedback.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        imgevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentEvent.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });

        txtviewevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentHomePage.this,StudentEvent.class);
                ii.putExtra("username",username);
                startActivity(ii);
            }
        });
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