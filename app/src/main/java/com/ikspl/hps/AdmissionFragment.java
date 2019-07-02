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
import java.sql.PreparedStatement;
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
public class AdmissionFragment extends Fragment {

    EditText edittextfirstname,edittextlastname,edittextfathername,edittextmothername,edittextdob,edittextacademicqualification,edittextaddress,edittextcity,edittextstate,edittextpincode,edittextmobileno,edittextemail,edittextremark;
    String spinnertext,gendertext,castetext,un,pass,db,ip,currentdate;
    Button btnsubmit;
    ProgressBar progressBar;
    Connection con;
    Integer enquiryidd,courseid = 19;
    PreparedStatement preparedStatement;
    Statement st;
    Date c,dobb;
    final Calendar myCalendar = Calendar.getInstance();


    public AdmissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admission, container, false);
        String [] values =
                {"---Admission Class---","Nursary","K.G.-I","K.G.-II","Class - Ist","Class - IInd","Class - IIIrd","Class - IVth","Class - Vth","Class - VIth","Class - VIIth","Class - VIIIth"};
        final Spinner spinnerclass = v.findViewById(R.id.spinnerclass);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerclass.setAdapter(adapter);

        spinnerclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertext = spinnerclass.getSelectedItem().toString();
                Log.e("MyTag","kkkclass== "+spinnertext);
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
                        Log.e("MyTag","kkkgender== "+gendertext);
                        break;
                    case R.id.radiobuttongirls :
                        gendertext = "Girl";
                        Log.e("MyTag","kkkgender== "+gendertext);
                        break;
                }

            }
        });

        final RadioGroup categoryradioGroup = (RadioGroup) v.findViewById(R.id.radiogroupcategory);
        categoryradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.radiobuttongeneral :
                        castetext = "general";
                        Log.e("MyTag","kkkcaste== "+castetext);
                        break;
                    case R.id.radiobuttonsc :
                        castetext = "sc";
                        Log.e("MyTag","kkkcaste== "+castetext);
                        break;
                    case R.id.radiobuttonst :
                        castetext = "st";
                        Log.e("MyTag","kkkcaste== "+castetext);
                        break;
                    case R.id.radiobuttonobc :
                        castetext = "obc";
                        Log.e("MyTag","kkkcaste== "+castetext);
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
                if(categoryradioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getContext(),"Please Select Category",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        if(edittextdob.getText().toString() != null)
                        {
                            dobb = new SimpleDateFormat().parse(edittextdob.getText().toString());
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
                    //String query = "select * from Online_Enquiry;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        enquiryidd = rs.getInt(1);
                        Log.e("Mytag","kkk1="+rs.getString(1));
                        Log.e("Mytag","kkk2="+rs.getString(2));
                        Log.e("Mytag","kkk3="+rs.getString(3));
                        Log.e("Mytag","kkk4="+rs.getString(4));
                        Log.e("Mytag","kkk5="+rs.getString(5));
                        Log.e("Mytag","kkk6="+rs.getString(6));
                        Log.e("Mytag","kkk7="+rs.getString(7));
                        Log.e("Mytag","kkk8="+rs.getString(8));
                        Log.e("Mytag","kkk9="+rs.getString(9));
                        Log.e("Mytag","kkk10="+rs.getString(10));
                        Log.e("Mytag","kkk11="+rs.getString(11));
                        Log.e("Mytag","kkk12="+rs.getString(12));
                        Log.e("Mytag","kkk13="+rs.getString(13));
                        Log.e("Mytag","kkk14="+rs.getString(14));
                        Log.e("Mytag","kkk15="+rs.getString(15));
                        Log.e("Mytag","kkk16="+rs.getString(16));
                        Log.e("Mytag","kkk17="+rs.getString(17));
                        Log.e("Mytag","kkk18="+rs.getString(18));

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
            // Toast.makeText(Selfcare.this, r, Toast.LENGTH_SHORT).show();
            Log.e("MyTag","kkkisAfterSuccess"+isSuccess);
            if(isSuccess)
            {
                Log.e("MyTag","kkkx== 8");
                Toast.makeText(getContext() , "Form Submit Successfull" , Toast.LENGTH_LONG).show();
                Intent ii = new Intent(getContext(),Dashboard.class);
                startActivity(ii);
            }
        }

        @SuppressLint("WrongThread")
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

                    @SuppressLint("WrongThread") String query = "INSERT INTO Online_Enquiry (EnquiryID,Course_id,FirstName,LastName,FatherName,Qualification,MotherName,address,city,State,PinCode,MobleNo,Email,Remark,Gender,Categoy) VALUES ('"+enquiryidd+"','"+courseid+"','"+edittextfirstname.getText().toString()+"','"+edittextlastname.getText().toString()+"','"+edittextfathername.getText().toString()+"','"+edittextacademicqualification.getText().toString()+"','"+edittextmothername.getText().toString()+"','"+edittextaddress.getText().toString()+"','"+edittextcity.getText().toString()+"','"+edittextstate.getText().toString()+"','"+edittextpincode.getText().toString()+"','"+edittextmobileno.getText().toString()+"','"+edittextemail.getText().toString()+"','"+edittextremark.getText().toString()+"','"+gendertext+"','"+castetext+"')";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    isSuccess = true;
                    Log.e("MyTag","kkkx== 6");
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
