package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentLogin extends AppCompatActivity {
    EditText edittextusername, edittextpassword;
    Spinner spinnersession;
    Button btnsignin;
    ProgressBar progressBar;
    TextView texthome;
    Connection con;
    String un, pass, db, ip, username, password, admissionno;
    ArrayList<String> sessionid = new ArrayList<>();

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        stopService(new Intent(getBaseContext(),Service1.class));

        session = new UserSessionManager(getApplicationContext());
        session = new UserSessionManager(getApplicationContext());
        edittextusername = findViewById(R.id.edittextusername);
        edittextpassword = findViewById(R.id.edittextpassword);
        spinnersession = findViewById(R.id.spinnersession);
        btnsignin = findViewById(R.id.btnsignin);
        progressBar = findViewById(R.id.progbar);
        texthome = findViewById(R.id.txthome);
        sessionid.add(0, "Select your Session");
        final ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sessionid);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersession.setAdapter(spinneradapter);

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016

        Spinnerfetch spinnerfetch = new Spinnerfetch();
        spinnerfetch.execute("");

        texthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(StudentLogin.this, Dashboard.class);
                startActivity(ii);
                finish();
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edittextusername.getText().toString();
                password = edittextpassword.getText().toString();
                edittextpassword.setText("");
                edittextusername.setText("");
                Log.e("MyTag", "kkkusername = " + username);
                Log.e("MyTag", "kkkpassword = " + password);
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    public class Spinnerfetch extends AsyncTask<String, String, String> {
        String z = "";

        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {


//            progressBar.setVisibility(View.GONE);
//            Toast.makeText(Selfcare.this, r, Toast.LENGTH_SHORT).show();
//            if(isSuccess)
//            {
//                Toast.makeText(Selfcare.this , "Login Successfull" , Toast.LENGTH_LONG).show();
//                //finish();
//            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";

                } else {
                    // Change below query according to your own database.
                    String query = "select session_desc from session_mst;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        sessionid.add(rs.getString(1));
                    }
                    Log.e("Mytag", "kkksessionid=" + sessionid.toString());
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

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            // Toast.makeText(Selfcare.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                session.createUserLoginSession(username, admissionno);
                Toast.makeText(StudentLogin.this, "Login Successfull", Toast.LENGTH_LONG).show();
                Intent ii = new Intent(StudentLogin.this, StudentHomePage.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.putExtra("username", username);
                startActivity(ii);
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String usernam = username;
            String passwordd = password;
            if (usernam.trim().equals("") || passwordd.trim().equals(""))
                z = "Please enter Username and Password";
            else {
                try {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        // Change below query according to your own database.
                        String query = "select * from Course_Student_Info where userid= '" + usernam.toString() + "' and password = '" + passwordd.toString() + "'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            admissionno = rs.getString(1);
                            z = "Login successful";
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
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user + ";password=" + password;
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
}