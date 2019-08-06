package samstnet.com.kaz.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import samstnet.com.kaz.MainActivity;
import samstnet.com.kaz.R;
import samstnet.com.kaz.alarm.AlarmBroadcastReceiver;
import samstnet.com.kaz.alarm.AlarmDisturb;

import static samstnet.com.kaz.eventbus.Customer.CHANNEL_ID;


public class ExampleService extends Service {

    static AlarmManager mAlarmManager;
    static public PendingIntent[] operation;
    int _minute;
    static int minute;
    static PendingIntent pendingIntent;
    static public PendingIntent pendingIntent1;
    Intent intent;
    Intent intent1;
    static Calendar calendar;
    static public int time = 0;
    static public int operationNum = 24;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("test", "서비스의 onCreate");

        //알람이 실행될 때 실행되었으면 하는 액티비티
        intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent1 = new Intent(this, AlarmDisturb.class);
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        calendar = Calendar.getInstance();
        operation = new PendingIntent[operationNum];

        time = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            onTimeSet();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onTimeSet() {
        // 사용자가 시간을 선택하였을 때, 실행됨, 유저가 설정한 시간과 분이 이곳에서 설정됨
        _minute=0;

        calendar.set(Calendar.MINUTE, _minute);

        pendingIntent=PendingIntent.getBroadcast(this, 25, intent, 0);
        pendingIntent1=PendingIntent.getBroadcast(this,26,intent1,0);

        sendBroadcast(intent);

        Log.d(String.valueOf(time), String.valueOf(_minute));

        calendar.set(Calendar.HOUR_OF_DAY, time);
        calendar.set(Calendar.MINUTE, _minute);

        for (int i = 0; i < operationNum; i++) {

            calendar.set(Calendar.HOUR_OF_DAY,time);
            calendar.set(Calendar.MINUTE,0);

            operation[i] = PendingIntent.getBroadcast(this, i, intent, 0);

            //알람 반복
            // 10분
            //mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 *10 , operation[i]);
            // 24시간
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24 , operation[i]);

            //_minute++;
            time+=1;
        }

        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,30);

        //아침알람
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60*60*24 , pendingIntent);
    }

    //알람 서비스 시작
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sendAlarm(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        String getTime = sdf.format(date);
        minute = Integer.valueOf(getTime);

        //calendar.set(calendar.MINUTE,minute);
       // mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("어플을 켜주셔서 감사합니다.")
                .setContentText(input)
                .setSmallIcon(R.drawable.bean1)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < operationNum; i++) {
            mAlarmManager.cancel(operation[i]);
        }
        Log.d("test", "서비스의 onDestroy");
    }

    public static AlarmManager getAlarmManager(){
        return mAlarmManager;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}