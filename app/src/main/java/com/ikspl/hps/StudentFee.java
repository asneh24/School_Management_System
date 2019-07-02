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

import com.ikspl.hps.Adapters.StudentFullFeesAdapter;
import com.ikspl.hps.Adapters.StudentInstallmentAdapter;
import com.ikspl.hps.Models.FeesDetailsModel;
import com.ikspl.hps.Models.InstallmentDetailsModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentFee extends AppCompatActivity {

    TextView txtviewstdname;
    Connection con;
    String un,pass,db,ip,username,firstname,lastname,admissionno,sessionyr,classname,formattedDate;
    Integer classid;

    RecyclerView recyclefees,recycleinstallment;
    ArrayList<FeesDetailsModel> arrayfees;
    ArrayList<InstallmentDetailsModel> arrayinstallment;
    ProgressBar progressBar;
    StudentFullFeesAdapter sffa;
    StudentInstallmentAdapter sia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fee);
        stopService(new Intent(getBaseContext(),Service1.class));

        txtviewstdname = findViewById(R.id.txtviewstdname);
        ip = getString(R.string.server);
        db = getString(R.string.database); //admin_EDU_ass //admin_demoEdusoft
        un = getString(R.string.username); //ASSDATABASE //DemoeduDATABASE
        pass = getString(R.string.password); //ASSDATA@2016 //DemoeduDATA@2016
        Intent ii = getIntent();
        username = ii.getStringExtra("username");
        progressBar = findViewById(R.id.progressbar);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        arrayfees = new ArrayList<>();
        arrayinstallment = new ArrayList<>();
        recyclefees = findViewById(R.id.recyclerfeesdetails);

        recyclefees.hasFixedSize();
        RecyclerView.LayoutManager feeslayoutManager = new LinearLayoutManager( StudentFee.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recyclefees.setLayoutManager(feeslayoutManager);

        recycleinstallment = findViewById(R.id.recyclerinstallmentdetails);

        recycleinstallment.hasFixedSize();
        RecyclerView.LayoutManager installmentlayoutManager = new LinearLayoutManager( StudentFee.this,LinearLayoutManager.VERTICAL,false )  ;//3
        recycleinstallment.setLayoutManager(installmentlayoutManager);

        CheckLogin spinnerfetch = new CheckLogin();
        spinnerfetch.execute("");

        ClassMaster classMaster = new ClassMaster();
        classMaster.execute("");

        FeesDetails feesDetails = new FeesDetails();
        feesDetails.execute("");

        InstallmentDetails installmentDetails = new InstallmentDetails();
        installmentDetails.execute("");
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

    public  class FeesDetails extends AsyncTask<String,String,String>
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

            Log.e("MyTag","kkkarrayfull = "+arrayfees);
            sffa = new StudentFullFeesAdapter(StudentFee.this,arrayfees);
            recyclefees.setAdapter(sffa);
            progressBar.setVisibility(View.INVISIBLE);
        }

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
                    String query = "declare @AdmNo nvarchar(50)\n" +
                            "                        declare @SessYr int\n" +
                            "                        declare @Courseid int\n" +
                            "                        set @AdmNo = '"+admissionno+"'\n" +
                            "                        set @SessYr = '"+sessionyr+"'\n" +
                            "                        set @Courseid = '"+classid+"'\n" +
                            "                        SELECT     dbo.Course_Fee_HeadName.Fees_HeadName,\n" +
                            "                        dbo.Course_wise_Amount.HeadAmounts, dbo.Course_wise_Amount.installments, \n" +
                            "                        ISNULL(DiscountDetails.Discount, 0) \n" +
                            "                        AS Discount, dbo.Course_wise_Amount.HeadAmounts - ISNULL(DiscountDetails.Discount, 0) AS Total\n" +
                            "                        FROM         dbo.Course_Fee_HeadName INNER JOIN\n" +
                            "                        dbo.Course_wise_Amount ON dbo.Course_Fee_HeadName.CourseFeeHead_id = dbo.Course_wise_Amount.CourseFeeHead_id AND \n" +
                            "                        dbo.Course_wise_Amount.StudentType = dbo.GetStdType(@AdmNo, @SessYr) AND dbo.Course_wise_Amount.SessionYearid = @SessYr  LEFT OUTER JOIN\n" +
                            "                        (SELECT     AdmissionNo, FeesHead_ID, FeesSubHead_ID, DiscType, SUM(Discount) AS Discount, SessionYearID\n" +
                            "                        FROM          dbo.Course_Student_DiscountDetails where AdmissionNo=@AdmNo and SessionYearID=@Sessyr \n" +
                            "                        GROUP BY AdmissionNo, FeesHead_ID, FeesSubHead_ID, DiscType, SessionYearID) AS DiscountDetails ON \n" +
                            "                        dbo.Course_wise_Amount.FeesType = DiscountDetails.FeesHead_ID AND dbo.Course_wise_Amount.CourseFeeHead_id = DiscountDetails.FeesSubHead_ID\n" +
                            "                        WHERE     (dbo.Course_wise_Amount.Course_id = @Courseid) AND (dbo.Course_wise_Amount.FeesType NOT IN\n" +
                            "                        (SELECT     FeeTypeID\n" +
                            "                        FROM          dbo.Course_Fee_Mappings)) OR\n" +
                            "                        (dbo.Course_wise_Amount.FeesType IN\n" +
                            "                        (SELECT     FeeTypeID\n" +
                            "                        FROM          (SELECT     a.StudId, b.FeeTypeID\n" +
                            "                        FROM          dbo.Course_HostelDetails AS a INNER JOIN\n" +
                            "                        dbo.Course_Fee_Mappings AS b ON a.HostalName = b.itemID AND b.itemTypeCode = 'HOS'\n" +
                            "                        UNION ALL\n" +
                            "                        SELECT     a.StudId, b.FeeTypeID\n" +
                            "                        FROM         dbo.Course_TrasportationDetails AS a INNER JOIN\n" +
                            "                        dbo.Course_Fee_Mappings AS b ON a.BusRootName = b.itemID AND b.itemTypeCode = 'TRA') AS tmp\n" +
                            "                        WHERE      (StudId = @AdmNo) AND (dbo.Course_wise_Amount.Course_id = @Courseid) AND (dbo.Course_wise_Amount.SessionYearid = @SessYr)))";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while(rs.next())
                    {
                        FeesDetailsModel fdm = new FeesDetailsModel();
                        fdm.setFees_HeadName(rs.getString(1));
                        fdm.setHeadAmounts(rs.getFloat(2));
                        fdm.setInstallments(rs.getInt(3));
                        fdm.setDiscount(rs.getFloat(4));
                        fdm.setTotal(rs.getFloat(5));
                        arrayfees.add(fdm);

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

    public  class InstallmentDetails extends AsyncTask<String,String,String>
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

            sia = new StudentInstallmentAdapter(StudentFee.this,arrayinstallment);
            recycleinstallment.setAdapter(sia);
            progressBar.setVisibility(View.INVISIBLE);
        }

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
                    String query = "declare @AdmNo nvarchar(50)\n" +
                            "                    declare @SessYr int\n" +
                            "                    declare @CourseIDvr int\n" +
                            "                    declare @TodayDate datetime\n" +
                            "                    set @AdmNo = '"+admissionno+"'\n" +
                            "                    set @SessYr = '" + sessionyr + "'\n" +
                            "                    set @CourseIDvr ='" + classid + "'\n" +
                            "                    set @TodayDate = '"+formattedDate+"'\n" +
                            "                    if(@AdmNo ='' or @SessYr='' or @CourseIDvr ='')\n" +
                            "                    SELECT    '' CourseFeeHead_id, '' FeesType, '' Fees_HeadName, '' InstallmentNo, '' InstallmentAmt, '' Discount, \n" +
                            "                    0 PayableAmt, 0 PaidAmt, 0 DueAmt,null PaidDate, '' Stu_AdmissionNo,'' FeeSetSessID,'' RecSessYrID\n" +
                            "                    else\n" +
                            "                    select CourseFeeHead_id,FeesType,Fees_HeadName,InstallmentNo,InstallmentAmt,Discount,\n" +
                            "                    case when (PayableAmt+isnull(LFA,0))<=PaidAmt then isnull(LFA,0) else isnull(LFA2,0) end LateFees,\n" +
                            "                    PayableAmt + case when (PayableAmt+isnull(LFA,0))=PaidAmt then isnull(LFA,0) else isnull(LFA2,0) end  as PayableAmt ,\n" +
                            "                    PaidAmt,\n" +
                            "                    (DueAmt+case when (PayableAmt+isnull(LFA,0))=PaidAmt then isnull(LFA,0) else isnull(LFA2,0) end )DueAmt,\n" +
                            "                    \n" +
                            "                   --isnull(LFA,0) LateFees,\n" +
                            "                   -- PayableAmt+isnull(LFA,0) as PayableAmt ,PaidAmt,(DueAmt+isnull(LFA,0))DueAmt,\n" +
                            "                    \n" +
                            "                   LateFeeDueDate,DueDate,PaidDate,Stu_AdmissionNo,AdmissionNo,FeeSetSessID,RecSessYrID,ActivationDate from (\n" +
                            "                    \n" +
                            "                    select CourseFeeHead_id,FeesType,Fees_HeadName,InstallmentNo,InstallmentAmt,Discount,\n" +
                            "                    case when IsPerDayCharge='YES' then sum(ApplicableDays*convert(decimal,LateFeeAmount)) else sum(convert(decimal,LateFeeAmount)) end LFA2,\n" +
                            "                    case when IsPerDayCharge='YES' then sum(ApplicableDays2*convert(decimal,LateFeeAmount)) else sum(convert(decimal,LateFeeAmount)) end LFA,\n" +
                            "                    PayableAmt,PaidAmt,DueAmt,LateFeeDueDate,DueDate,PaidDate,Stu_AdmissionNo,AdmissionNo,FeeSetSessID,RecSessYrID,ActivationDate from (\n" +
                            "                    --select * from (\n" +
                            "                    SELECT     SetFees.CourseFeeHead_id, SetFees.FeesType, SetFees.Fees_HeadName, SetFees.InstallmentNo, SetFees.InstallmentAmt, \n" +
                            "                    ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) AS Discount, \n" +
                            "                    case when ISNULL(Rec.PAmount, 0) <= SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0)  then \n" +
                            "\t\t\t\t\t\t\tcase when  (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End))<0 then 0 else\n" +
                            "\t\t\t\t\t\t\t\t\t   (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End)) end \n" +
                            "\t\t\t\t\t\t\t--################################--\n" +
                            "\t\t\t\t\t\t\twhen (ISNULL(Rec.PAmount, 0) >= SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0)) AND (rec.ReceiptDate>LateFeeDueDate)\n" +
                            "\t\t\t\t\t\t\t\t\tAND (DATEDIFF(DAY ,LateFeeDueDate,Rec.ReceiptDate)>(DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= Rec.ReceiptDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End)))\n" +
                            "\t\t\t\t\t\t\t\t\t\n" +
                            "\t\t\t\t\t\t\tthen \n" +
                            "\t\t\t\t\t\t\t\t\tcase when  (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End))<0 then 0 else\n" +
                            "\t\t\t\t\t\t\t\t\t   (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) end \t\t\t\t\t\t\t\t\n" +
                            "\t\t\t\t\t\t\t\t\t   \n" +
                            "\t\t\t\t\t\t\t--################################--\n" +
                            "\t\t\t\t\t\t\telse  \n" +
                            "\t\t\t\t\t\t\tcase when  (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= Rec.ReceiptDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End))<0 then 0 else\n" +
                            "\t\t\t\t\t\t\t\t\t   (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= Rec.ReceiptDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End)) end \n" +
                            "\t\t\t\t\t\t\tend ApplicableDays,\n" +
                            "\t\t\t\t\t\t\t\n" +
                            "\t\t\t\t\t\t\tCase when ISNULL(Rec.PAmount, 0) <= SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0)  then \n" +
                            "\t\t\t\t\t\t\tcase when  (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End))<0 then 0 else\n" +
                            "\t\t\t\t\t\t\t\t\t   (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) end \n" +
                            "\t\t\t\t\t\t\telse  \n" +
                            "\t\t\t\t\t\t\tcase when  (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= Rec.ReceiptDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End))<0 then 0 else\n" +
                            "\t\t\t\t\t\t\t\t\t   (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= Rec.ReceiptDate then Rec.ReceiptDate else Course_fee_Latefees.ToDate End)) end \n" +
                            "\t\t\t\t\t\t\tend ApplicableDays2,\n" +
                            "\t\t\t\t\t\t\t\n" +
                            "                    Course_Fee_LateFees.LateFeeAmount,IsPerDayCharge,\n" +
                            "                    SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) AS PayableAmt,\n" +
                            "                    ISNULL(Rec.PAmount, 0) AS PaidAmt, SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) - ISNULL(Rec.PAmount, 0) AS DueAmt,\n" +
                            "                    SetFees.LateFeeDueDate,SetFees.DueDate, Rec.ReceiptDate AS PaidDate, Rec.Stu_AdmissionNo, SetFees.AdmissionNo, SetFees.SessionYearid AS FeeSetSessID, \n" +
                            "                    Rec.SessionYearID AS RecSessYrID, SetFees.ActivationDate\n" +
                            "                    FROM         (SELECT     dbo.Course_wise_Amount_Details.CourseFeeHead_id, dbo.Course_wise_Amount_Details.FeesType, (dbo.Course_Fee_HeadName.Fees_HeadName +' '+ ISNULL(dbo.Course_wise_Amount_Details.Remark ,'')) as Fees_HeadName,\n" +
                            "                    dbo.Course_wise_Amount_Details.InstallmentNo, dbo.Course_wise_Amount_Details.InstallmentAmt, 0 AS Discount, 0 AS PayableAmt, 0 AS PaidAmt, \n" +
                            "                    dbo.Course_wise_Amount_Details.InstallmentAmt AS DueAmt,dbo.Course_wise_Amount_Details.LateFeeDueDate, dbo.Course_wise_Amount_Details.DefaultDueDate AS DueDate, NULL AS PaidDate, \n" +
                            "                    dbo.Course_wise_Amount_Details.SessionYearid, @admNo AS AdmissionNo, dbo.Course_wise_Amount_Details.ActivationDate\n" +
                            "                    FROM          dbo.Course_Fee_HeadName INNER JOIN\n" +
                            "                    dbo.Course_wise_Amount_Details ON dbo.Course_Fee_HeadName.CourseFeeHead_id = dbo.Course_wise_Amount_Details.CourseFeeHead_id AND \n" +
                            "                    dbo.Course_wise_Amount_Details.StudentType = dbo.GetStdType(@AdmNo, @SessYr)\n" +
                            "                    WHERE      (dbo.Course_wise_Amount_Details.Course_id = @CourseIDvr) AND (dbo.Course_wise_Amount_Details.FeesType NOT IN\n" +
                            "                    (SELECT     FeeTypeID\n" +
                            "                    FROM          dbo.Course_Fee_Mappings)) OR\n" +
                            "                    (dbo.Course_wise_Amount_Details.FeesType IN\n" +
                            "                    (SELECT     FeeTypeID\n" +
                            "                    FROM          (SELECT     a.StudId, b.FeeTypeID\n" +
                            "                    FROM          dbo.Course_HostelDetails AS a INNER JOIN\n" +
                            "                    dbo.Course_Fee_Mappings AS b ON a.HostalName = b.itemID AND b.itemTypeCode = 'HOS'\n" +
                            "                    UNION ALL\n" +
                            "                    SELECT     a.StudId, b.FeeTypeID\n" +
                            "                    FROM         dbo.Course_TrasportationDetails AS a INNER JOIN\n" +
                            "                    dbo.Course_Fee_Mappings AS b ON a.BusRootName = b.itemID AND b.itemTypeCode = 'TRA') AS tmp\n" +
                            "                    WHERE      (StudId = @AdmNo) AND (dbo.Course_wise_Amount_Details.Course_id = @CourseIDvr) AND \n" +
                            "                    (dbo.Course_wise_Amount_Details.SessionYearid = @SessYr)))) AS SetFees LEFT OUTER JOIN\n" +
                            "                    dbo.Course_Student_DiscountDetails ON SetFees.AdmissionNo = dbo.Course_Student_DiscountDetails.AdmissionNo AND \n" +
                            "                    SetFees.InstallmentNo = dbo.Course_Student_DiscountDetails.InstallmentNo AND SetFees.FeesType = dbo.Course_Student_DiscountDetails.FeesHead_ID AND \n" +
                            "                    SetFees.CourseFeeHead_id = dbo.Course_Student_DiscountDetails.FeesSubHead_ID \n" +
                            "                    \n" +
                            "                     LEFT OUTER JOIN  Course_Fee_LateFees\n" +
                            "                    on SetFees.FeesType = Course_Fee_LateFees.FeeType and setfees.CourseFeeHead_id = Course_Fee_LateFees.FeesHead \n" +
                            "                    and SetFees .InstallmentNo = Course_Fee_LateFees .Installment and SetFees .SessionYearid = Course_Fee_LateFees.sessionYearID\n" +
                            "                    and  Course_Fee_LateFees.course_id = @CourseIDvr  and Course_Fee_LateFees .stdType = dbo.GetStdType(@AdmNo, @SessYr) \n" +
                            "                    and  SetFees.LateFeeDueDate < @TodayDate \n" +
                            "                                        \n" +
                            "                    Inner JOIN --yha krna hai\n" +
                            "                    (SELECT     Stu_AdmissionNo, Head_id, SubHead_id, InstallmentNo, PAmount, ReceiptDate, SessionYearID\n" +
                            "                    FROM          (SELECT     dbo.Course_Fee_Collection.Stu_AdmissionNo, dbo.Course_Fee_CollecionChild.Head_id, dbo.Course_Fee_CollecionChild.SubHead_id, \n" +
                            "                    dbo.Course_Fee_CollecionChild.InstallmentNo, SUM(dbo.Course_Fee_CollecionChild.PAmount) AS PAmount, \n" +
                            "                    dbo.Course_Fee_Collection.SessionYearID,\n" +
                            "                    (SELECT     TOP (1) hd.ReceiptDate\n" +
                            "                    FROM          dbo.Course_Fee_CollecionChild AS dt INNER JOIN\n" +
                            "                    dbo.Course_Fee_Collection AS hd ON dt.CollectionID = hd.CollectionID\n" +
                            "                    WHERE      (hd.SessionYearID = dbo.Course_Fee_Collection.SessionYearID) AND (dt.Head_id = dbo.Course_Fee_CollecionChild.Head_id)  AND (hd.Stu_AdmissionNo = dbo.Course_Fee_Collection.Stu_AdmissionNo) \n" +
                            "                    AND (dt.SubHead_id = dbo.Course_Fee_CollecionChild.SubHead_id) AND \n" +
                            "                    (dt.InstallmentNo = dbo.Course_Fee_CollecionChild.InstallmentNo)\n" +
                            "                    ORDER BY hd.CollectionID DESC) AS ReceiptDate\n" +
                            "                    FROM          dbo.Course_Fee_CollecionChild INNER JOIN\n" +
                            "                    dbo.Course_Fee_Collection ON dbo.Course_Fee_CollecionChild.CollectionID = dbo.Course_Fee_Collection.CollectionID\n" +
                            "                    GROUP BY dbo.Course_Fee_Collection.Stu_AdmissionNo, dbo.Course_Fee_CollecionChild.Head_id, dbo.Course_Fee_CollecionChild.SubHead_id, \n" +
                            "                    dbo.Course_Fee_CollecionChild.InstallmentNo, dbo.Course_Fee_Collection.SessionYearID) AS Receipts) AS Rec ON \n" +
                            "                    SetFees.FeesType = Rec.Head_id AND SetFees.CourseFeeHead_id = Rec.SubHead_id AND SetFees.InstallmentNo = Rec.InstallmentNo AND \n" +
                            "                    Rec.SessionYearID = SetFees.SessionYearid AND Rec.Stu_AdmissionNo = @AdmNo\n" +
                            "                    and setfees.ActivationDate <=rec.ReceiptDate \n" +
                            "                    and SetFees.DueDate >= rec.ReceiptDate \n" +
                            "                    ) Paid_fee where FeeSetSessID =@SessYr \n" +
                            "                  Group By CourseFeeHead_id,FeesType,Fees_HeadName,InstallmentNo,InstallmentAmt,Discount,PayableAmt,PaidAmt,DueAmt,LateFeeDueDate,DueDate,PaidDate,Stu_AdmissionNo,AdmissionNo,FeeSetSessID,RecSessYrID,ActivationDate,IsPerDayCharge \n" +
                            "                    Union All --Union Between Paid And Unpaid\n" +
                            "                    select CourseFeeHead_id,FeesType,Fees_HeadName,InstallmentNo,InstallmentAmt,Discount,\n" +
                            "                    case when IsPerDayCharge='YES' then sum(ApplicableDays*convert(decimal,LateFeeAmount)) else sum(convert(decimal,LateFeeAmount)) end LFA,\n" +
                            "                    case when IsPerDayCharge='YES' then sum(ApplicableDays2*convert(decimal,LateFeeAmount)) else sum(convert(decimal,LateFeeAmount)) end LFA2,\n" +
                            "                    PayableAmt,PaidAmt,DueAmt,LateFeeDueDate,DueDate,PaidDate,Stu_AdmissionNo,AdmissionNo,FeeSetSessID,RecSessYrID,ActivationDate from (\n" +
                            "                    SELECT     SetFees.CourseFeeHead_id, SetFees.FeesType, SetFees.Fees_HeadName, SetFees.InstallmentNo, SetFees.InstallmentAmt, \n" +
                            "                    ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) AS Discount,\n" +
                            "                    case when (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) <0 then 0 else (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) end ApplicableDays,\n" +
                            "                    case when (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) <0 then 0 else (DATEDIFF(DAY,case when Course_fee_Latefees.FromDate< SetFees.LateFeeDueDate  then SetFees.LateFeeDueDate else Course_fee_Latefees.FromDate-1 End,case when Course_fee_Latefees.ToDate>= @TodayDate then @TodayDate else Course_fee_Latefees.ToDate End)) end ApplicableDays2,\n" +
                            "                    Course_Fee_LateFees.LateFeeAmount,IsPerDayCharge,\n" +
                            "                    SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) AS PayableAmt, ISNULL(Rec.PAmount, 0) AS PaidAmt,\n" +
                            "                    SetFees.InstallmentAmt - ISNULL(dbo.Course_Student_DiscountDetails.Discount, 0) - ISNULL(Rec.PAmount, 0) AS DueAmt,\n" +
                            "                    SetFees.DueDate,SetFees.LateFeeDueDate, Rec.ReceiptDate AS PaidDate, Rec.Stu_AdmissionNo, SetFees.AdmissionNo, SetFees.SessionYearid AS FeeSetSessID, \n" +
                            "                    Rec.SessionYearID AS RecSessYrID, SetFees.ActivationDate\n" +
                            "                    FROM         (SELECT     dbo.Course_wise_Amount_Details.CourseFeeHead_id, dbo.Course_wise_Amount_Details.FeesType, (dbo.Course_Fee_HeadName.Fees_HeadName +' '+ ISNULL(dbo.Course_wise_Amount_Details.Remark ,'')) as Fees_HeadName,\n" +
                            "                    dbo.Course_wise_Amount_Details.InstallmentNo, dbo.Course_wise_Amount_Details.InstallmentAmt, 0 AS Discount, 0 AS PayableAmt, 0 AS PaidAmt, \n" +
                            "                    dbo.Course_wise_Amount_Details.InstallmentAmt AS DueAmt,dbo.Course_wise_Amount_Details.LateFeeDueDate,  dbo.Course_wise_Amount_Details.DefaultDueDate AS DueDate, NULL AS PaidDate, \n" +
                            "                    dbo.Course_wise_Amount_Details.SessionYearid, @admNo AS AdmissionNo, dbo.Course_wise_Amount_Details.ActivationDate\n" +
                            "                    FROM          dbo.Course_Fee_HeadName INNER JOIN\n" +
                            "                    dbo.Course_wise_Amount_Details ON dbo.Course_Fee_HeadName.CourseFeeHead_id = dbo.Course_wise_Amount_Details.CourseFeeHead_id AND \n" +
                            "                    dbo.Course_wise_Amount_Details.StudentType = dbo.GetStdType(@AdmNo, @SessYr)\n" +
                            "                    WHERE      (dbo.Course_wise_Amount_Details.Course_id = @CourseIDvr) AND (dbo.Course_wise_Amount_Details.FeesType NOT IN\n" +
                            "                    (SELECT     FeeTypeID\n" +
                            "                    FROM          dbo.Course_Fee_Mappings)) OR\n" +
                            "                    (dbo.Course_wise_Amount_Details.FeesType IN\n" +
                            "                    (SELECT     FeeTypeID\n" +
                            "                    FROM          (SELECT     a.StudId, b.FeeTypeID\n" +
                            "                    FROM          dbo.Course_HostelDetails AS a INNER JOIN\n" +
                            "                    dbo.Course_Fee_Mappings AS b ON a.HostalName = b.itemID AND b.itemTypeCode = 'HOS'\n" +
                            "                    UNION ALL\n" +
                            "                    SELECT     a.StudId, b.FeeTypeID\n" +
                            "                    FROM         dbo.Course_TrasportationDetails AS a INNER JOIN\n" +
                            "                    dbo.Course_Fee_Mappings AS b ON a.BusRootName = b.itemID AND b.itemTypeCode = 'TRA') AS tmp\n" +
                            "                    WHERE      (StudId = @AdmNo) AND (dbo.Course_wise_Amount_Details.Course_id = @CourseIDvr) AND \n" +
                            "                    (dbo.Course_wise_Amount_Details.SessionYearid = @SessYr)))) AS SetFees LEFT OUTER JOIN\n" +
                            "                    dbo.Course_Student_DiscountDetails ON SetFees.AdmissionNo = dbo.Course_Student_DiscountDetails.AdmissionNo AND \n" +
                            "                    SetFees.InstallmentNo = dbo.Course_Student_DiscountDetails.InstallmentNo AND SetFees.FeesType = dbo.Course_Student_DiscountDetails.FeesHead_ID AND \n" +
                            "                    SetFees.CourseFeeHead_id = dbo.Course_Student_DiscountDetails.FeesSubHead_ID \n" +
                            "                    \n" +
                            "                    LEFT OUTER JOIN  Course_Fee_LateFees\n" +
                            "                    on SetFees.FeesType = Course_Fee_LateFees.FeeType and setfees.CourseFeeHead_id = Course_Fee_LateFees.FeesHead \n" +
                            "                    and SetFees .InstallmentNo = Course_Fee_LateFees .Installment and SetFees .SessionYearid = Course_Fee_LateFees.sessionYearID\n" +
                            "                    and  Course_Fee_LateFees.course_id = @CourseIDvr  and Course_Fee_LateFees .stdType = dbo.GetStdType(@AdmNo, @SessYr) \n" +
                            "                    and  SetFees.LateFeeDueDate < @TodayDate \n" +
                            "                    \n" +
                            "                    LEFT OUTER JOIN --yha krna hai\n" +
                            "                    (SELECT     Stu_AdmissionNo, Head_id, SubHead_id, InstallmentNo, PAmount, ReceiptDate, SessionYearID\n" +
                            "                    FROM          (SELECT     dbo.Course_Fee_Collection.Stu_AdmissionNo, dbo.Course_Fee_CollecionChild.Head_id, dbo.Course_Fee_CollecionChild.SubHead_id, \n" +
                            "                    dbo.Course_Fee_CollecionChild.InstallmentNo, SUM(dbo.Course_Fee_CollecionChild.PAmount) AS PAmount, \n" +
                            "                    dbo.Course_Fee_Collection.SessionYearID,\n" +
                            "                    (SELECT     TOP (1) hd.ReceiptDate\n" +
                            "                    FROM          dbo.Course_Fee_CollecionChild AS dt INNER JOIN\n" +
                            "                    dbo.Course_Fee_Collection AS hd ON dt.CollectionID = hd.CollectionID\n" +
                            "                    WHERE      (hd.SessionYearID = dbo.Course_Fee_Collection.SessionYearID) AND (dt.Head_id = dbo.Course_Fee_CollecionChild.Head_id) \n" +
                            "                    AND (dt.SubHead_id = dbo.Course_Fee_CollecionChild.SubHead_id) AND \n" +
                            "                    (dt.InstallmentNo = dbo.Course_Fee_CollecionChild.InstallmentNo)\n" +
                            "                    ORDER BY hd.CollectionID DESC) AS ReceiptDate\n" +
                            "                    FROM          dbo.Course_Fee_CollecionChild INNER JOIN\n" +
                            "                    dbo.Course_Fee_Collection ON dbo.Course_Fee_CollecionChild.CollectionID = dbo.Course_Fee_Collection.CollectionID\n" +
                            "                    GROUP BY dbo.Course_Fee_Collection.Stu_AdmissionNo, dbo.Course_Fee_CollecionChild.Head_id, dbo.Course_Fee_CollecionChild.SubHead_id, \n" +
                            "                    dbo.Course_Fee_CollecionChild.InstallmentNo, dbo.Course_Fee_Collection.SessionYearID) AS Receipts) AS Rec ON \n" +
                            "                    SetFees.FeesType = Rec.Head_id AND SetFees.CourseFeeHead_id = Rec.SubHead_id AND SetFees.InstallmentNo = Rec.InstallmentNo AND \n" +
                            "                    Rec.SessionYearID = SetFees.SessionYearid AND Rec.Stu_AdmissionNo = @AdmNo\n" +
                            "                   ) unpaid_fee where PaidDate is null and ActivationDate<=@TodayDate and DueDate >=@TodayDate and FeeSetSessID =@SessYr \n" +
                            "                    Group By CourseFeeHead_id,FeesType,Fees_HeadName,InstallmentNo,InstallmentAmt,Discount,PayableAmt,PaidAmt,DueAmt,LateFeeDueDate,DueDate,PaidDate,Stu_AdmissionNo,AdmissionNo,FeeSetSessID,RecSessYrID,ActivationDate,IsPerDayCharge \n" +
                            "                    ) assasa\n" +
                            "\n" +
                            "                     UNION ALL\n" +
                            "                                        \n" +
                            "                                        SELECT -1 CourseFeeHead_id,-1\tFeesType,'OLD DUE' Fees_HeadName,1 InstallmentNo,DueAmt InstallmentAmt,BadAccAmt Discount,\n" +
                            "                    0 LateFees,DUEAMT- ISNULL(convert(decimal,BadAccAmt),0) PayableAmt,ISNULL(SUM(Course_Fee_CollecionChild.PAmount),0) PaidAmt,(DueAmt-ISNULL(convert(decimal,BadAccAmt),0)-ISNULL(SUM(PAMOUNT),0)) DueAmt,NULL LateFeeDueDate,NULL DueDate,ISNULL(MAX(RECEIPTDATE), NULL) PaidDate,\n" +
                            "                    ADMISSIONNO Stu_AdmissionNo,ADMISSIONNO AdmissionNo,@SessYr FeeSetSessID,Course_Fee_Collection.SessionYearID   RecSessYrID, NULL ActivationDate fROM \n" +
                            "                    Course_fee_settlement LEFT OUTER JOIN \n" +
                            "                    Course_Fee_CollecionChild INNER JOIN Course_Fee_Collection ON Course_Fee_CollecionChild.CollectionID = Course_Fee_Collection.CollectionID \n" +
                            "                    ON Course_Fee_CollecionChild .Head_id =-1 AND Course_Fee_CollecionChild .SubHead_id = -1  and Course_Fee_Collection.Stu_AdmissionNo = Course_fee_settlement .AdmissionNo \n" +
                            "                    WHERE COURSE_FEE_SETTLEMENT.SessionYearID =@SessYr-1  AND AdmissionNo =@AdmNo and convert(decimal,isnull(Course_fee_settlement.DueAmt,0))>convert(decimal,isnull(Course_fee_settlement.BadAccAmt,0))\n" +
                            "                    gROUP BY DueAmt ,AdmissionNo,COURSE_FEE_coLLECTION.SessionYearID ,BadAccAmt \n" +
                            "                    order by FeeSetSessID,FeesType ,CourseFeeHead_id,InstallmentNo";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while(rs.next())
                    {
                        InstallmentDetailsModel idm = new InstallmentDetailsModel();
                        idm.setHeadName(rs.getString(3));
                        idm.setInstallment(rs.getString(4));
                        idm.setAmount(rs.getString(5));
                        idm.setDiscount(rs.getString(6));
                        idm.setLatefee(rs.getString(7));
                        idm.setPayamt(rs.getString(8));
                        idm.setPaidamt(rs.getString(9));
                        idm.setDueamt(rs.getString(10));
                        idm.setDuedate(rs.getDate(12));
                        idm.setPaiddate(rs.getDate(13));
                        arrayinstallment.add(idm);
                        Log.e("MyTag","kkkRow31 = "+rs.getString(1));
                        Log.e("MyTag","kkkRow32 = "+rs.getString(2));
                        Log.e("MyTag","kkkRow33 = "+rs.getString(3));
                        Log.e("MyTag","kkkRow34 = "+rs.getString(4));
                        Log.e("MyTag","kkkRow35 = "+rs.getString(5));
                        Log.e("MyTag","kkkRow36 = "+rs.getString(6));
                        Log.e("MyTag","kkkRow37 = "+rs.getString(7));
                        Log.e("MyTag","kkkRow38 = "+rs.getString(8));
                        Log.e("MyTag","kkkRow39 = "+rs.getString(9));
                        Log.e("MyTag","kkkRow310 = "+rs.getString(10));
                        Log.e("MyTag","kkkRow311 = "+rs.getString(11));
                        Log.e("MyTag","kkkRow312 = "+rs.getString(12));
                        Log.e("MyTag","kkkRow313 = "+rs.getString(13));
                        Log.e("MyTag","kkkRow314 = "+rs.getString(14));
                        Log.e("MyTag","kkkRow315 = "+rs.getString(15));
                        Log.e("MyTag","kkkRow316 = "+rs.getString(16));
                        Log.e("MyTag","kkkRow317 = "+rs.getString(17));
                        Log.e("MyTag","kkkRow318 = "+rs.getString(18));
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