package com.ikspl.hps;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Service1 extends Service {

    private NotificationManager notifManager;
    UserSessionManager session;
    String admissionno;
    Alarm alarm = new Alarm();
    public Service1() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        //Toast.makeText(this.getBaseContext(), "Service start", Toast.LENGTH_SHORT).show();
        alarm.setAlarm(this);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //getConnection();
        alarm.setAlarm(this);
        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {




                CheckLoginSelfcare();

                handler.postDelayed(this, 1000 * 1 * 30);
            }
        };

        handler.postDelayed(r, 1000 * 0 * 0);

        //return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        CheckLoginSelfcare();
        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        alarm.setAlarm(this);

        super.onTaskRemoved(rootIntent);
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        alarm.setAlarm(this);

        //Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);

    }
    public void createNotification(String aMessage, Context context,String NoticeID,String NotType) {
        int NOTIFY_ID =(int)System.currentTimeMillis();
        //int notificationId=(int)System.currentTimeMillis();
        String id = "11"; // default_channel_id
        String title = "CHNANLNAME"; // Default Channel
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent;

        PendingIntent pendingIntent;
        // NotificationCompat.Builder builder;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.BLUE);
                mChannel.setSound(alarmSound,null);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            //builder = new NotificationCompat.Builder(context);
            intent = new Intent(context, StudentHomePage.class);
            intent.putExtra("StuID", NotType);
            intent.putExtra("NotID", NoticeID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, NOTIFY_ID, intent, 0);
            builder.setContentTitle(NotType)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(alarmSound)
                    .setColor(context.getColor(R.color.colorPrimary))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            //builder = new NotificationCompat.Builder(context);
            intent = new Intent(context, StudentHomePage.class);
            intent.putExtra("StuID", NotType);
            intent.putExtra("NotID", NoticeID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, NOTIFY_ID, intent, 0);
            builder.setContentTitle(NotType)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher))
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setSound(alarmSound)
                    .setColor(Color.GREEN)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
        //startForeground(NOTIFY_ID, notification);
    }
    public void CheckLoginSelfcare()
    {
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        admissionno = user.get(UserSessionManager.KEY_ADMISSIONNO);
        Log.e("MyTag","kkkusernameshared = "+admissionno);
        if(admissionno != null)
        {
            ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nf=cn.getActiveNetworkInfo();
            if(nf != null && nf.isConnected()==true ) {
                getConnection(admissionno);
            }
        }


    }
    public void getConnection(String StuID)
    {
        String username = getString(R.string.username);
        String pwd = getString(R.string.password);
        String db = getString(R.string.database);
        String data_source=getString(R.string.server);

        Statement st;
        Connection connect = ConnectionHelper(username, pwd, db, data_source);
        if (connect == null)
        {
            Toast.makeText(this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                st = connect.createStatement();

                String Sql = "select NotificationID,Not_Title,Not_Text,NotID from Notification_MST where StuID='" + StuID + "' and Send_Status IS NULL";
                ResultSet rs = st.executeQuery(Sql);
                while (rs.next()) {
                    String Cat_ID = rs.getString("Not_Text");
                    createNotification(Cat_ID, getApplicationContext(), rs.getString("NotID"), rs.getString("Not_Title"));
                    //Notification(Cat_ID);
                    //Intent inte=new Intent(getApplicationContext(), MainActivity.class);
                    //inte.putExtra("StuID", "Notice");
                    String SqlDelete = "update Notification_MST set Send_Status='Send' where NotificationID='" + rs.getString("NotificationID") + "' and Send_Status IS NULL";
                    st.executeQuery(SqlDelete);
                }

                String SqlAtt = "select * from HR_AttendanceMst_Students where StuID='" + StuID + "' and SendStatus IS NULL";
                ResultSet rsAtt = st.executeQuery(SqlAtt);
                while (rsAtt.next()) {
                    String Mesage = "Dear Parent Your wards  is present today, ie " + rsAtt.getString("Dateatt") + ". Regards " + getString(R.string.app_name);
                    createNotification(Mesage, getApplicationContext(), rsAtt.getString("Dateatt"), "ATTENDANCE");
                    //Notification(Cat_ID);
                    //Intent inte=new Intent(getApplicationContext(), MainActivity.class);
                    //inte.putExtra("StuID", "Notice");
                    String SqlDeleteAtt = "update HR_AttendanceMst_Students set SendStatus='Send' where tableID='" + rsAtt.getString("tableID") + "' and SendStatus IS NULL";
                    st.executeQuery(SqlDeleteAtt);
                }


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    @SuppressLint("NewApi")
    private Connection ConnectionHelper(String user, String password,
                                        String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ";"
                    + "databaseName=" + database + ";user=" + user
                    + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {

        } catch (ClassNotFoundException e) {

        } catch (Exception e) {

        }
        return connection;
    }
}