package org.techtown.mediclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static org.techtown.mediclock.Mediname.realmediname;
import static org.techtown.mediclock.Mediweek.ev_pr;
import static org.techtown.mediclock.Mediweek.fri_pr;
import static org.techtown.mediclock.Mediweek.mon_pr;
import static org.techtown.mediclock.Mediweek.sat_pr;
import static org.techtown.mediclock.Mediweek.sun_pr;
import static org.techtown.mediclock.Mediweek.thur_pr;
import static org.techtown.mediclock.Mediweek.tue_pr;
import static org.techtown.mediclock.Mediweek.wed_pr;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.support.v4.app.*;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class TimePickerAlarm extends AppCompatActivity {
    //private Mediname mediname;
    ActionBar actionBar;
    String giveRealmedi;
    String android_num;
    public static String datetextstring;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.gotohome) {
            Intent homeIntent = new Intent(this, Mainmenu.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker_alarm);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람시간설정");
        //액션바 배경색 변경#368AFF



        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        final String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //Log.d(TAG, android_id);
        // String android_id="088738928";
        //int id2 = Integer.parseInt(android_id);

        final TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
        picker.setIs24HourView(true); //24시간 view로 만들기


        // 앞서 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재시간
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis); //notification 설정

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        //Toast.makeText(getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
        //String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(nextDate);

        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());


        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));


        if (Build.VERSION.SDK_INT >= 23) {
            picker.setHour(pre_hour);
            picker.setMinute(pre_minute);
        } else {
            picker.setCurrentHour(pre_hour);
            picker.setCurrentMinute(pre_minute);
        }


        Button button = (Button) findViewById(R.id.alarm_btn);  //알람 설정 버튼 클릭시에 일어나는 이벤트
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int hour, hour_24, minute;
                String am_pm, weekpr = null;

                if (Build.VERSION.SDK_INT >= 23) {
                    hour_24 = picker.getHour();
                    minute = picker.getMinute();       //변수에 설정한 시각, 분 저장
                } else {
                    hour_24 = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if (hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;      //12보다 늦으면 pm 오후로 돌리기
                } else {
                    hour = hour_24;
                    am_pm = "AM";        //12보다 작으면 am 오전으로 돌리기
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                // 현재 지정된 시간으로 알람 시간 설정


                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);   //calender에다가 hous와 min set해놓기

                // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);

                if (ev_pr) {
                    weekpr = "매일";
                    Toast.makeText(getApplicationContext(), "매일" + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                    ev_pr = false;
                }
                if (sun_pr) {
                    weekpr = "일";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    date_text = "일요일 " + date_text ;
                    sun_pr = false;
                    //Toast.makeText(getApplicationContext(), "일요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (sat_pr) {
                    weekpr = "토";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    date_text = "토요일 " + date_text ;
                    sat_pr = false;
                    //Toast.makeText(getApplicationContext(), "토요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (fri_pr) {
                    weekpr = "금";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    date_text = "금요일 " + date_text ;
                    fri_pr = false;
                    //Toast.makeText(getApplicationContext(), "금요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (thur_pr) {
                    weekpr = "목";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    date_text = "목요일 " + date_text ;
                    thur_pr = false;
                    //Toast.makeText(getApplicationContext(), "목요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (wed_pr) {
                    weekpr = "수";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    date_text = "수요일 " + date_text ;
                    wed_pr = false;
                    //Toast.makeText(getApplicationContext(), "수요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (tue_pr) {
                    weekpr = "화";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    date_text = "화요일 " + date_text ;
                    tue_pr = false;
                    //Toast.makeText(getApplicationContext(), "화요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }
                if (mon_pr) {
                    weekpr = "월";
                    //calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    date_text = "월요일 " + date_text ;
                    mon_pr = false;
                    //Toast.makeText(getApplicationContext(), "월요일 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();  //calender에 저장되어 있는 time 가져와서 date_text에 넣기
                }

               /* mediname = (Mediname)getApplicationContext();
                mediname.AAA();
                String test2 = mediname.realmediname;*/


                //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                //String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                //android_id = android_id.substring(8);

               // String medimediname = Mediname.alarmmediname;

                //givemedi = ((Mediname)Mediname.context).realmediname;
                giveRealmedi = realmediname;
                android_num = android_id.substring(8);

                datetextstring = date_text;
                Toast.makeText(getApplicationContext(), giveRealmedi+"약이 " +date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
                new Server.Update_alarmList( android_num,  weekpr , date_text , giveRealmedi).execute("http://192.168.0.10:3306/insert");
                //( [사용자 id] ,  [시간] , [day] ).

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
                editor.apply();


                diaryNotification(calendar);

                Intent intent_mainmenu = new Intent(getApplicationContext(),Mainmenu.class); //다음 액티비티 화면으로 전환
                startActivity(intent_mainmenu);
            }

        });

    }

    void diaryNotification(Calendar calendar) {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if ( dailyNotify ) {

            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000*60*60*24, pendingIntent);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                }*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
/*
        Button button = findViewById(R.id.alarm_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mainmenu = new Intent(getApplicationContext(),AlarmList.class); //다음 액티비티 화면으로 전환
                startActivity(intent_mainmenu);

            }
        });*/
    }

}

