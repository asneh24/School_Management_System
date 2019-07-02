package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentFeedback extends AppCompatActivity {

    TextView txtviewstdname;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname,admissionno,sessionyr;
    EditText feedback;
    Button submit;
    Integer feedbackidd;
    Statement st;
    PreparedStatement preparedStatement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_feedback);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");
        feedback = findViewById(R.id.edittextremark);
        submit = findViewById(R.id.btnsubmit);

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        final Loginid loginid = new Loginid();
        loginid.execute("");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(feedback.getText()))
                {
                    feedback.setError("Please give feedback");
                }
                else
                {
                    Uploadfeedback uploadfeedback = new Uploadfeedback();
                    uploadfeedback.execute("");
                    Log.e("MyTag","kkkfeedbackid == "+feedbackidd);
                    Log.e("MyTag","kkkstudentid == "+admissionno);
                    Log.e("MyTag","kkksessionid == "+sessionyr);
                    Log.e("MyTag","kkkfeedback == "+feedback.getText());
                }
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
                    String query = "select first_name,last_name,AdmissionNo,sessyr_id from Course_Student_Info where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        firstname = rs.getString(1);
                        lastname = rs.getString(2);
                        admissionno = rs.getString(3);
                        sessionyr = rs.getString(4);
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
    public class Loginid extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected String doInBackground(String... strings) {
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
                    String query = "select  isnull(Max(FeedBackID),0)+1  as Colomn from Self_FeedbackMst;";
                    //String query = "select * from Online_Enquiry;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        feedbackidd = rs.getInt(1);
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

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {

        }
    }
    public class Uploadfeedback extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){

            Log.e("MyTag","kkkx== 2");
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            Log.e("MyTag","kkkx== 7");
            //progressBar.setVisibility(View.GONE);
            Toast.makeText(StudentFeedback.this, r, Toast.LENGTH_SHORT).show();
            Log.e("MyTag","kkkisAfterSuccess"+isSuccess);
            if(isSuccess)
            {
                Log.e("MyTag","kkkx== 8");
                Toast.makeText(StudentFeedback.this , "Your Request is Submitted" , Toast.LENGTH_LONG).show();
                Intent ii = new Intent(StudentFeedback.this,StudentHomePage.class);
                startActivity(ii);
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
                    Log.e("MyTag","kkkx== 5");
                    Log.e("MyTag","kkkisBeforeSuccess"+isSuccess);

                    st = con.createStatement();
                    preparedStatement = con.prepareStatement("insert into Self_FeedbackMst (FeedBackID,StuID,SessionID,FeedBack) values('"+feedbackidd+"','"+admissionno+"','"+sessionyr+"','"+feedback.getText().toString()+"');");
                    isSuccess = true;
                    Log.e("MyTag","kkkx== 6");
                    z = "Added Successfully";
                    preparedStatement.executeUpdate();
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