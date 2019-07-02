package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudentLeaveRequest extends AppCompatActivity {

    TextView txtviewstdname;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname,admissionnum,sessionyr,classnamee;
    EditText fromdate,todate,days,remark;
    Button btnsubmit;
    final Calendar myCalendar = Calendar.getInstance();
    int flg=0;
    Date fromdatee,todatee;
    Integer loginidd;
    Statement st;
    PreparedStatement preparedStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave_request);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        fromdate = findViewById(R.id.edittextfromdate);
        todate = findViewById(R.id.edittexttodate);
        days = findViewById(R.id.edittextdays);
        remark = findViewById(R.id.edittextremark);
        btnsubmit = findViewById(R.id.btnsubmit);

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flg = 1;

                new DatePickerDialog(StudentLeaveRequest.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flg = 2;

                new DatePickerDialog(StudentLeaveRequest.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(fromdate.getText()))
                {
                    fromdate.setError("Please Select Date");
                }
                else
                if(TextUtils.isEmpty(todate.getText()))
                {
                    todate.setError("Please Select Date");
                }
                else
                if(fromdatee.getTime() > todatee.getTime())
                {
                    Toast.makeText(StudentLeaveRequest.this,"You Enter Wrong Date. Please flip the date",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");
                    try {
                        fromdatee = dates.parse(fromdate.getText().toString());
                        todatee = dates.parse(todate.getText().toString());

                        long difference = Math.abs(fromdatee.getTime() - todatee.getTime());
                        long differenceDates = 1+(difference / (24 * 60 * 60 * 1000));

                        String dayDifference = Long.toString(differenceDates);
                        days.setText(dayDifference+" Days");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        final Loginid loginid = new Loginid();
        loginid.execute("");

        CourseName coursename = new CourseName();
        coursename.execute("");

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(fromdate.getText()))
                {
                    fromdate.setError("Please Select Date");
                }
                else
                if(TextUtils.isEmpty(todate.getText()))
                {
                    todate.setError("Please Select Date");
                }
                else
                if(TextUtils.isEmpty(days.getText()))
                {
                    days.setError("Please enter days");
                }
                else
                if(TextUtils.isEmpty(remark.getText()))
                {
                    remark.setError("Please enter reason for leave");
                }
                else
                {
                    Log.e("MyTag","kkkloginid == "+loginidd);
                    Log.e("MyTag","kkkfromdate == "+fromdate.getText());
                    Log.e("MyTag","kkktodate == "+todate.getText());
                    Log.e("MyTag","kkkdaycount == "+days.getText());
                    Log.e("MyTag","kkkremark == "+remark.getText());
                    Log.e("MyTag","kkkstudentname == "+firstname);
                    Log.e("MyTag","kkkclassname == "+classnamee);
                    Log.e("MyTag","kkkadmissionno == "+admissionnum);
                    Log.e("MyTag","kkksessionyr == "+sessionyr);
                    SaveData savedata = new SaveData();
                    savedata.execute("");
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
                        admissionnum = rs.getString(3);
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
                    String query = "select  isnull(Max(LgID),0)+1  as Colomn from LeaveRequest;";
                    //String query = "select * from Online_Enquiry;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        loginidd = rs.getInt(1);
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

    public class CourseName extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected void onPostExecute(String r)
        {

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
                    String query = "select CourseMaster.CourseName,Course_Student_Info.AdmissionNo from Course_Student_Info INNER JOIN CourseMaster ON dbo.Course_Student_Info.dygb_id = dbo.CourseMaster.Course_id where userid= '"+ username.toString()+"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        classnamee = rs.getString(1);
                        Log.e("MyTag","kkkclassname = "+classnamee);
                        Log.e("MyTag","kkkadmissionno = "+rs.getString(2));
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

    public class SaveData extends AsyncTask<String,String,String>
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
            Toast.makeText(StudentLeaveRequest.this, r, Toast.LENGTH_SHORT).show();
            Log.e("MyTag","kkkisAfterSuccess"+isSuccess);
            if(isSuccess)
            {
                Log.e("MyTag","kkkx== 8");
                Toast.makeText(StudentLeaveRequest.this , "Your Request is Submitted" , Toast.LENGTH_LONG).show();
                Intent ii = new Intent(StudentLeaveRequest.this,StudentHomePage.class);
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
                    preparedStatement = con.prepareStatement("insert into LeaveRequest (LgID,todate,fromdate,daycount,remark,StudName,ClassName,AdmissionNo,sessyr_id) values('"+loginidd+"','"+todate.getText().toString()+"','"+fromdate.getText().toString()+"','"+days.getText().toString()+"','"+remark.getText().toString()+"','"+firstname+"','"+classnamee+"','"+admissionnum+"','"+sessionyr+"');");
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

    private void updateLabel() {
        if(flg==1)
        {
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            fromdate.setText(sdf.format(myCalendar.getTime()));
            fromdatee = myCalendar.getTime();
            Log.e("MyTag","kkkFromdateee =="+fromdatee);

        }
        else
        if(flg==2)
        {
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            todate.setText(sdf.format(myCalendar.getTime()));
            todatee = myCalendar.getTime();
            Log.e("MyTag","kkktodateee =="+todatee);

        }

    }
}