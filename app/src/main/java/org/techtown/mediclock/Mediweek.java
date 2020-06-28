package org.techtown.mediclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Calendar;

public class Mediweek extends AppCompatActivity {
    ActionBar actionBar;

    private static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/myapp";
    private static final String NORMAL_PATH = BASE_PATH + "/normal";
    public static boolean mon_pr = false;
    static boolean tue_pr = false;
    static boolean wed_pr = false;
    static boolean thur_pr = false;
    static boolean fri_pr = false;
    static boolean sat_pr = false;
    static boolean sun_pr = false;
    static boolean ev_pr = false;

    static boolean[] week = {mon_pr, tue_pr, wed_pr, thur_pr, fri_pr, sat_pr, sun_pr, ev_pr};

    private AlarmManager _am;

    private Button mon, tue, wed, thur, fri, sat, sun ;


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
        setContentView(R.layout.fragment_mediweek);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람요일주기");
        //액션바 배경색 변경#368AFF

        final Button mon = findViewById(R.id.mon);
        final Button tue = findViewById(R.id.tue);
        final Button wed = findViewById(R.id.wed);
        final Button thur = findViewById(R.id.thur);
        final Button fri = findViewById(R.id.fri);
        final Button sat = findViewById(R.id.sat);
        final Button sun = findViewById(R.id.sun);
        final Button every = findViewById(R.id.everyday);

/*
        File file = new File(NORMAL_PATH + "/drop_1235.m4a");
        //boolean[] week = { false, sun.isPressed(), mon.isPressed(), tue.isPressed() , wed.isPressed() ,
                //thur.isPressed() ,fri.isPressed(), sat.isPressed()}; // sunday=1 이라서 0의 자리에는 아무 값이나 넣었음

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("file", file.toString());
        intent.putExtra("weekday", week);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, file.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 10); // 10초 뒤

        long oneday = 24 * 60 * 60 * 1000;// 24시간

        // 10초 뒤에 시작해서 매일 같은 시간에 반복하기
        _am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), oneday, pIntent);
       */

        mon.setOnClickListener(new View.OnClickListener() {
            int clickmon = 0;
            public void onClick(View view) {
                if(clickmon == 0)
                {
                    mon.setSelected(true);
                    mon.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clickmon = 1;
                    mon_pr = true;
                }
                else
                {
                    mon.setSelected(false);
                    mon.setBackgroundResource(R.drawable.pr_button_week);
                    clickmon= 0;
                    mon_pr = false;
                }
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            int clicktue = 0;
            public void onClick(View view) {
                if(clicktue == 0)
                {
                    tue.setSelected(true);
                    tue.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clicktue = 1;
                    tue_pr = true;
                }
                else
                {
                    tue.setSelected(false);
                    tue.setBackgroundResource(R.drawable.pr_button_week);
                    clicktue= 0;
                    tue_pr = false;
                }
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            int clickwed = 0;
            public void onClick(View view) {
                if(clickwed == 0)
                {
                    wed.setSelected(true);
                    wed.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clickwed = 1;
                    wed_pr = true;
                }
                else
                {
                    wed.setSelected(false);
                    wed.setBackgroundResource(R.drawable.pr_button_week);
                    clickwed= 0;
                    wed_pr = false;
                }
            }
        });
        thur.setOnClickListener(new View.OnClickListener() {
            int clickthur = 0;
            public void onClick(View view) {
                if(clickthur == 0)
                {
                    thur.setSelected(true);
                    thur.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clickthur = 1;
                    thur_pr = true;
                }
                else
                {
                    thur.setSelected(false);
                    thur.setBackgroundResource(R.drawable.pr_button_week);
                    clickthur= 0;
                    thur_pr = false;
                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            int clickfri = 0;
            public void onClick(View view) {
                if(clickfri == 0)
                {
                    fri.setSelected(true);
                    fri.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clickfri = 1;
                    fri_pr = true;
                }
                else
                {
                    fri.setSelected(false);
                    fri.setBackgroundResource(R.drawable.pr_button_week);
                    clickfri= 0;
                    fri_pr = false;
                }
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            int clicksat = 0;
            public void onClick(View view) {
                if(clicksat == 0)
                {
                    sat.setSelected(true);
                    sat.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clicksat = 1;
                    sat_pr = true;
                }
                else
                {
                    sat.setSelected(false);
                    sat.setBackgroundResource(R.drawable.pr_button_week);
                    clicksat= 0;
                    sat_pr = false;
                }
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            int clicksun = 0;
            public void onClick(View view) {
                if(clicksun == 0)
                {
                    sun.setSelected(true);
                    sun.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clicksun = 1;
                    sun_pr = true;
                }
                else
                {
                    sun.setSelected(false);
                    sun.setBackgroundResource(R.drawable.pr_button_week);
                    clicksun= 0;
                    sun_pr = false;
                }
            }
        });
        every.setOnClickListener(new View.OnClickListener() {
            int clickevery = 0;
            public void onClick(View view) {
                if(clickevery == 0)
                {
                    every.setSelected(true);
                    every.setBackgroundResource(R.drawable.pr_button_week_pressed);
                    clickevery = 1;
                    ev_pr = true;

                }
                else
                {
                    every.setSelected(false);
                    every.setBackgroundResource(R.drawable.pr_button_week);
                    clickevery= 0;
                    ev_pr = false;
                }
            }
        });

        Button button = findViewById(R.id.confirmweek_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mon_pr==false && tue_pr==false && wed_pr==false && thur_pr==false
                        && fri_pr==false && sat_pr==false && sun_pr==false && ev_pr==false){
                    Toast toastView = Toast.makeText(getApplicationContext(), "요일을 선택해주세요", Toast.LENGTH_LONG);
                    toastView.setGravity( Gravity.TOP| Gravity.LEFT, 320, 700); //토스트 메시지 위치 x,y좌표로 바꿀 수 있다.
                    toastView.show();
                    Intent intent_medialarm2 = new Intent(getApplicationContext(), Mediweek.class); //일단 바로 검색결과 띄음
                    startActivity(intent_medialarm2);
                }
                else {
                    Intent intent_medialarm = new Intent(getApplicationContext(), TimePickerAlarm.class); //일단 바로 검색결과 띄음
                    startActivity(intent_medialarm);
                }
            }
        });

    }

}
