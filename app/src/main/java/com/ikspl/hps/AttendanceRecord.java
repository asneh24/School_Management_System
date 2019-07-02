package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.ikspl.hps.Adapters.AttendanceAdapter;
import com.ikspl.hps.Models.AttendanceModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AttendanceRecord extends AppCompatActivity {

    TextView txtviewstdname,txtviewjan,txtviewfeb,txtviewmar,txtviewapr,txtviewmay,txtviewjun,txtviewjul,txtviewaug,txtviewsep,txtviewoct,txtviewnov,txtviewdec;
    Connection con;
    String un, pass, db, ip, username, firstname, lastname,admissionno;
    Integer totaldaysjan = 0,totaldaysfeb = 0,totaldaysmar = 0,totaldaysapr = 0,totaldaysmay = 0,totaldaysjun = 0,totaldaysjul = 0,totaldaysaug = 0,totaldayssep = 0,totaldaysoct = 0,totaldaysnov = 0,totaldaysdec = 0;
    Integer absentjan = 0,absentfeb = 0,absentmar = 0,absentapr = 0,absentmay = 0,absentjun = 0,absentjul = 0,absentaug = 0,absentsep = 0,absentoct = 0,absentnov = 0,absentdec = 0;
    Spinner spinnerjan,spinnerfeb,spinnermar,spinnerapr,spinnermay,spinnerjun,spinnerjul,spinneraug,spinnersep,spinneroct,spinnernov,spinnerdec;
    ArrayList<AttendanceModel> arrayjan,arrayfeb,arraymar,arrayapr,arraymay,arrayjun,arrayjul,arrayaug,arraysep,arrayoct,arraynov,arraydec;
    AttendanceAdapter attendanceAdapterjan,attendanceAdapterfeb,attendanceAdaptermar,attendanceAdapterapr,attendanceAdaptermay,attendanceAdapterjun,attendanceAdapterjul,attendanceAdapteraug,attendanceAdaptersep,attendanceAdapteroct,attendanceAdapternov,attendanceAdapterdec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");

        txtviewjan = findViewById(R.id.txtviewjan);
        txtviewfeb = findViewById(R.id.txtviewfeb);
        txtviewmar = findViewById(R.id.txtviewmar);
        txtviewapr = findViewById(R.id.txtviewapr);
        txtviewmay = findViewById(R.id.txtviewmay);
        txtviewjun = findViewById(R.id.txtviewjun);
        txtviewjul = findViewById(R.id.txtviewjul);
        txtviewaug = findViewById(R.id.txtviewaug);
        txtviewsep = findViewById(R.id.txtviewsep);
        txtviewoct = findViewById(R.id.txtviewoct);
        txtviewnov = findViewById(R.id.txtviewnov);
        txtviewdec = findViewById(R.id.txtviewdec);

        arrayjan = new ArrayList<>();
        arrayfeb = new ArrayList<>();
        arraymar = new ArrayList<>();
        arrayapr = new ArrayList<>();
        arraymay = new ArrayList<>();
        arrayjun = new ArrayList<>();
        arrayjul = new ArrayList<>();
        arrayaug = new ArrayList<>();
        arraysep = new ArrayList<>();
        arrayoct = new ArrayList<>();
        arraynov = new ArrayList<>();
        arraydec = new ArrayList<>();

        spinnerjan = findViewById(R.id.spinnerjan);
        spinnerfeb = findViewById(R.id.spinnerfeb);
        spinnermar = findViewById(R.id.spinnermar);
        spinnerapr = findViewById(R.id.spinnerapr);
        spinnermay = findViewById(R.id.spinnermay);
        spinnerjun = findViewById(R.id.spinnerjun);
        spinnerjul = findViewById(R.id.spinnerjul);
        spinneraug = findViewById(R.id.spinneraug);
        spinnersep = findViewById(R.id.spinnersep);
        spinneroct = findViewById(R.id.spinneroct);
        spinnernov = findViewById(R.id.spinnernov);
        spinnerdec = findViewById(R.id.spinnerdec);

        AttendanceModel famjan = new AttendanceModel();
        famjan.setDateee("January");
        famjan.setStatus(" ");
        arrayjan.add(0,famjan);
        AttendanceModel famfeb = new AttendanceModel();
        famfeb.setDateee("February");
        famfeb.setStatus(" ");
        arrayfeb.add(0,famfeb);
        AttendanceModel fammar = new AttendanceModel();
        fammar.setDateee("March");
        fammar.setStatus(" ");
        arraymar.add(0,fammar);
        AttendanceModel famapr = new AttendanceModel();
        famapr.setDateee("April");
        famapr.setStatus(" ");
        arrayapr.add(0,famapr);
        AttendanceModel fammay = new AttendanceModel();
        fammay.setDateee("May");
        fammay.setStatus(" ");
        arraymay.add(0,fammay);
        AttendanceModel famjun = new AttendanceModel();
        famjun.setDateee("June");
        famjun.setStatus(" ");
        arrayjun.add(0,famjun);
        AttendanceModel famjul = new AttendanceModel();
        famjul.setDateee("July");
        famjul.setStatus(" ");
        arrayjul.add(0,famjul);
        AttendanceModel famaug = new AttendanceModel();
        famaug.setDateee("August");
        famaug.setStatus(" ");
        arrayaug.add(0,famaug);
        AttendanceModel famsep = new AttendanceModel();
        famsep.setDateee("September");
        famsep.setStatus(" ");
        arraysep.add(0,famsep);
        AttendanceModel famoct = new AttendanceModel();
        famoct.setDateee("October");
        famoct.setStatus(" ");
        arrayoct.add(0,famoct);
        AttendanceModel famnov = new AttendanceModel();
        famnov.setDateee("November");
        famnov.setStatus(" ");
        arraynov.add(0,famnov);
        AttendanceModel famdec = new AttendanceModel();
        famdec.setDateee("December");
        famdec.setStatus(" ");
        arraydec.add(0,famdec);


        attendanceAdapterjan = new AttendanceAdapter(getApplicationContext(),arrayjan);
        spinnerjan.setAdapter(attendanceAdapterjan);
        attendanceAdapterfeb = new AttendanceAdapter(getApplicationContext(),arrayfeb);
        spinnerfeb.setAdapter(attendanceAdapterfeb);
        attendanceAdaptermar = new AttendanceAdapter(getApplicationContext(),arraymar);
        spinnermar.setAdapter(attendanceAdaptermar);
        attendanceAdapterapr = new AttendanceAdapter(getApplicationContext(),arrayapr);
        spinnerapr.setAdapter(attendanceAdapterapr);
        attendanceAdaptermay = new AttendanceAdapter(getApplicationContext(),arraymay);
        spinnermay.setAdapter(attendanceAdaptermay);
        attendanceAdapterjun = new AttendanceAdapter(getApplicationContext(),arrayjun);
        spinnerjun.setAdapter(attendanceAdapterjun);
        attendanceAdapterjul = new AttendanceAdapter(getApplicationContext(),arrayjul);
        spinnerjul.setAdapter(attendanceAdapterjul);
        attendanceAdapteraug = new AttendanceAdapter(getApplicationContext(),arrayaug);
        spinneraug.setAdapter(attendanceAdapteraug);
        attendanceAdaptersep = new AttendanceAdapter(getApplicationContext(),arraysep);
        spinnersep.setAdapter(attendanceAdaptersep);
        attendanceAdapteroct = new AttendanceAdapter(getApplicationContext(),arrayoct);
        spinneroct.setAdapter(attendanceAdapteroct);
        attendanceAdapternov = new AttendanceAdapter(getApplicationContext(),arraynov);
        spinnernov.setAdapter(attendanceAdapternov);
        attendanceAdapterdec = new AttendanceAdapter(getApplicationContext(),arraydec);
        spinnerdec.setAdapter(attendanceAdapterdec);

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        Stdattendance atten = new Stdattendance();
        atten.execute("");
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {

            if (isSuccess) {
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
        protected String doInBackground(String... params) {

            try {
                con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "select first_name,last_name,AdmissionNo from Course_Student_Info where userid= '" + username.toString() + "'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        firstname = rs.getString(1);
                        lastname = rs.getString(2);
                        admissionno = rs.getString(3);
                        Log.e("MyTag","kkkadmissionno = "+admissionno);
                        isSuccess = true;
                        con.close();
                    } else {
                        z = "Invalid Credentials!";
                        isSuccess = false;
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+server+"/" + database + ";user=" + user + ";password=" + password;
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

    public class Stdattendance extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {

            txtviewjan.setText("Working Days :- "+totaldaysjan + "\n" + "Absent Days :- "+absentjan);
            txtviewfeb.setText("Working Days :- "+totaldaysfeb + "\n" + "Absent Days :- "+absentfeb);
            txtviewmar.setText("Working Days :- "+totaldaysmar + "\n" + "Absent Days :- "+absentmar);
            txtviewapr.setText("Working Days :- "+totaldaysapr + "\n" + "Absent Days :- "+absentapr);
            txtviewmay.setText("Working Days :- "+totaldaysmay + "\n" + "Absent Days :- "+absentmay);
            txtviewjun.setText("Working Days :- "+totaldaysjun + "\n" + "Absent Days :- "+absentjun);
            txtviewjul.setText("Working Days :- "+totaldaysjul + "\n" + "Absent Days :- "+absentjul);
            txtviewaug.setText("Working Days :- "+totaldaysaug + "\n" + "Absent Days :- "+absentaug);
            txtviewsep.setText("Working Days :- "+totaldayssep + "\n" + "Absent Days :- "+absentsep);
            txtviewoct.setText("Working Days :- "+totaldaysoct + "\n" + "Absent Days :- "+absentoct);
            txtviewnov.setText("Working Days :- "+totaldaysnov + "\n" + "Absent Days :- "+absentnov);
            txtviewdec.setText("Working Days :- "+totaldaysdec + "\n" + "Absent Days :- "+absentdec);

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "select Dateatt,Status,StuID from HR_AttendanceMst_Students where StuID= '" + admissionno + "'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while(rs.next())
                    {
                        String fdFormat = "dd-MM-yyyy"; //In which you need put here
                        SimpleDateFormat fdsdf = new SimpleDateFormat(fdFormat);

                        String yearFormat = "yyyy"; //In which you need put here
                        SimpleDateFormat yearsdf = new SimpleDateFormat(yearFormat);

                        String monthFormat = "MMM"; //In which you need put here
                        SimpleDateFormat monthsdf = new SimpleDateFormat(monthFormat);
                        Log.e("MyTag","kkkcol1== "+monthsdf.format(rs.getDate(1)));
                        Log.e("MyTag","kkkcol2 == "+rs.getString(2));
                        Log.e("MyTag","kkkcol3 == "+rs.getString(3));
                        if(monthsdf.format(rs.getDate(1)).equals("Jan"))
                        {
                            if(rs.getString(2).equals("P"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayjan.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentjan++;
                                arrayjan.add(am);
                            }
                            totaldaysjan++;


                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Feb"))
                        {
                            if(rs.getString(2).equals("P"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayfeb.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentfeb++;
                                arrayfeb.add(am);
                            }
                            totaldaysfeb++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Mar"))
                        {
                            if(rs.getString(2).equals("P"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arraymar.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {


                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentmar++;
                                arraymar.add(am);
                            }
                            totaldaysmar++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Apr"))
                        {
                            if(rs.getString(2).equals("P"))
                            {

                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayapr.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {

                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentapr++;
                                arrayapr.add(am);
                            }
                            totaldaysapr++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("May"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arraymay.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentmay++;
                                arraymay.add(am);
                            }
                            totaldaysmay++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Jun"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayjun.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentjun++;
                                arrayjun.add(am);
                            }
                            totaldaysjun++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Jul"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayjul.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentjul++;
                                arrayjul.add(am);
                            }
                            totaldaysjul++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Aug"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayaug.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentaug++;
                                arrayaug.add(am);
                            }
                            totaldaysaug++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Sep"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arraysep.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentsep++;
                                arraysep.add(am);
                            }
                            totaldayssep++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Oct"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arrayoct.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentoct++;
                                arrayoct.add(am);
                            }
                            totaldaysoct++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Nov"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arraynov.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentnov++;
                                arraynov.add(am);
                            }
                            totaldaysnov++;

                        }else
                        if(monthsdf.format(rs.getDate(1)).equals("Dec"))
                        {
                            if(rs.getString(2).equals("P"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("P");
                                arraydec.add(am);
                            }
                            else
                            if(rs.getString(2).equals("ABS"))
                            {
                                AttendanceModel am = new AttendanceModel();
                                am.setDateee(fdsdf.format(rs.getDate(1)));
                                am.setStatus("A");
                                absentdec++;
                                arraydec.add(am);
                            }
                            totaldaysdec++;

                        }

                    }

                    isSuccess = true;
                    con.close();

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;
        }
    }
}
