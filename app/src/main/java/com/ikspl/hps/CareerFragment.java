package com.ikspl.hps;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareerFragment extends Fragment {

    EditText edittextfirstname,edittextlastname,edittextfathername,edittextmothername,edittextdob,edittextacademicqualification,edittextaddress,edittextcity,edittextstate,edittextpincode,edittextmobileno,edittextemail,edittextremark;
    String spinnertext,gendertext,un,pass,db,ip,currentdate;
    Button btnsubmit;
    ProgressBar progressBar;
    Connection con;
    Integer enquiryidd;
    Date c,dobb;
    final Calendar myCalendar = Calendar.getInstance();


    public CareerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_career, container, false);
        String [] values =
                {"---Select Category---","Teaching","Non-Teaching"};
        final Spinner spinnerclass = v.findViewById(R.id.spinnerclass);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerclass.setAdapter(adapter);
        spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertext = spinnerclass.getSelectedItem().toString();
                Log.e("MyTag","kkkcategory=="+spinnertext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnsubmit = v.findViewById(R.id.buttonsubmit);
        edittextfirstname = v.findViewById(R.id.edittextfirstname);
        edittextlastname = v.findViewById(R.id.edittextlastname);
        edittextfathername = v.findViewById(R.id.edittextfathername);
        edittextmothername = v.findViewById(R.id.edittextmothername);
        edittextdob = v.findViewById(R.id.edittextdob);
        edittextacademicqualification = v.findViewById(R.id.edittextacademicqualification);
        edittextaddress = v.findViewById(R.id.edittextaddress);
        edittextcity = v.findViewById(R.id.edittextcity);
        edittextstate = v.findViewById(R.id.edittextstate);
        edittextpincode = v.findViewById(R.id.edittextpincode);
        edittextmobileno = v.findViewById(R.id.edittextmobilenumber);
        edittextemail = v.findViewById(R.id.edittextemail);
        edittextremark = v.findViewById(R.id.edittextremark);
        progressBar = v.findViewById(R.id.progbar);

        final RadioGroup genderradioGroup = (RadioGroup) v.findViewById(R.id.radiogroupgender);
        genderradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.radiobuttonboys :
                        gendertext = "boy";
                        break;
                    case R.id.radiobuttongirls :
                        gendertext = "Girl";
                        break;
                }

            }
        });

        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentdate = df.format(c);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittextdob.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016

        Enquiryid enquiryid = new Enquiryid();
        enquiryid.execute("");

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edittextfirstname.getText()))
                {
                    edittextfirstname.setError("Please enter the first name");
                }else
                if(TextUtils.isEmpty(edittextlastname.getText()))
                {
                    edittextlastname.setError("Please enter the Last name");
                }else
                if(TextUtils.isEmpty(edittextmobileno.getText()))
                {
                    edittextmobileno.setError("Please enter the Mobile No");
                }else
                if(genderradioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getContext(),"Please Select Gender",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    try {
                        if (edittextdob.getText().toString() != null)
                        {
                            dobb = new SimpleDateFormat("dd/MM/yyyy").parse(edittextdob.getText().toString());

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e("MyTag","kkkdob== "+edittextdob.getText().toString());
                    Log.e("MyTag","kkkcurrentdate== "+currentdate);
                    Log.e("MyTag","kkkx== 1");
                    FormFill formfill = new FormFill();
                    formfill.execute("");
                }

            }
        });

        return v;
    }

    public class Enquiryid extends AsyncTask<String,String,String>
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
                    String query = "select  isnull(Max(EnquiryID),0)+1  as Colomn from Online_Enquiry;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        enquiryidd = rs.getInt(1);
                        Log.e("Mytag","kkkenquiryid="+enquiryidd);
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

    public class FormFill extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {
            Log.e("MyTag","kkkx== 2");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            Log.e("MyTag","kkkx== 7");
            progressBar.setVisibility(View.GONE);
            Log.e("MyTag","kkkisAfterSuccess"+isSuccess);
            if(isSuccess)
            {
                Log.e("MyTag","kkkx== 8");
                Toast.makeText(getContext() , "Form Submit Successful" , Toast.LENGTH_LONG).show();
                Intent ii = new Intent(getContext(),Dashboard.class);
                startActivity(ii);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                Log.e("MyTag","kkkx== 3");
                con = connectionclass(un, pass, db, ip);        // Connect to database
                if (con == null)
                {
                    Log.e("MyTag","kkkx== 4");
                    z = "Check Your Internet Access!";
                }
                else
                {
                    Log.e("MyTag","kkkx== 5");
                    Log.e("MyTag","kkkisBeforeSuccess"+isSuccess);
                    String query = "INSERT INTO Online_Job (EnquiryID,Job_id,FirstName,LastName,FatherName,Qualification,MotherName,address,city,State,PinCode,MobleNo,Email,Remark,Gender) VALUES ('"+enquiryidd+"','"+spinnertext+"','"+edittextfirstname.getText().toString()+"','"+edittextlastname.getText().toString()+"','"+edittextfathername.getText().toString()+"','"+edittextacademicqualification.getText().toString()+"','"+edittextmothername.getText().toString()+"','"+edittextaddress.getText().toString()+"','"+edittextcity.getText().toString()+"','"+edittextstate.getText().toString()+"','"+edittextpincode.getText().toString()+"','"+edittextmobileno.getText().toString()+"','"+edittextemail.getText().toString()+"','"+edittextremark.getText().toString()+"','"+gendertext+"')";

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    Log.e("MyTag","kkkx== 6");
                    isSuccess = true;
                    z = "Added Successfully";

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
