package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static org.techtown.mediclock.SeecodeActivity.android_id;

public class alarmsetlist extends AppCompatActivity {
    protected static ArrayList<AlarmListFromDB> alarmArrayList;
    ActionBar actionBar;

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
        setContentView(R.layout.alarm_set_list);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람");
        Log.e("alarmsetlist", "들어왔음");

        //>>>>> 미리 List만들기 수정한 부분 - 시작 -

        String get_android_id =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID).substring(8);
       alarmArrayList = new ArrayList<>();
        Log.e("GET_ANDROID_ID",get_android_id);

        new Server.Show_alarmList( get_android_id).execute("http://192.168.0.10:3306/post" ); //list를 여기서 만들기
        Log.e("AlarmArrayList","만들어짐 + 서버다녀오기");
        // >>>>> 미리 List만들기 수정한 부분 - 끝 -


        // 액션바 배경색 변경#368AFF

        Button button = findViewById(R.id.setAlarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_set = new Intent(getApplicationContext(), Mediname.class);
                startActivity(intent_set);
            }
        });
        Button button2 = findViewById(R.id.listAlarm);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_list = new Intent(getApplicationContext(), AlarmList.class);
                startActivity(intent_list);
            }
        });
    }
}


