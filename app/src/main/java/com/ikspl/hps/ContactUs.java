package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactUs extends AppCompatActivity {
    TextView phoneno,address,emailidd;
    Connection con;
    String un,pass,db,ip,phonenum1,location,city,state,pincode,emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        stopService(new Intent(getBaseContext(),Service1.class));

        phoneno = findViewById(R.id.phoneno);
        address = findViewById(R.id.address);
        emailidd = findViewById(R.id.emailid);

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016

        Getdata dataa = new Getdata();
        dataa.execute("");


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
                Log.e("MyTag","kkkdata11");
                Log.e("MyTag","kkkphonenum == "+phonenum1);
                Log.e("MyTag","kkkaddress == "+location+"\n"+city+"\n"+state+"\n"+pincode);
                Log.e("MyTag","kkkemail == "+emailid);
                phoneno.setText(phonenum1);
                address.setText(location+"\n"+city+"\n"+state+"\n"+pincode);
                emailidd.setText(emailid);




            }
            else
            {
                phoneno.setText(" ");
                address.setText(" ");
                emailidd.setText(" ");
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
                    String query = "select * from fullcompany ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        phonenum1 = rs.getString(12);
                        location = rs.getString(5);
                        city = rs.getString(6);
                        state = rs.getString(7);
                        pincode = rs.getString(8);
                        emailid = rs.getString(10);

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
