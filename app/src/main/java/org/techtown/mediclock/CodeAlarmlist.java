package org.techtown.mediclock;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import static org.techtown.mediclock.WritecodeActivity.usercode;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CodeAlarmlist extends AppCompatActivity {

    ActionBar actionBar;
    String giveUsercode;

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
        setContentView(R.layout.code_alarm_list);

        TextView alarmList = (TextView)findViewById(R.id.alarmlist);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("약 묵 자");
        //액션바 배경색 변경#368AFF

        giveUsercode = usercode;

        new Server.Show_alarmList( giveUsercode ).execute("http://192.168.0.10:3306/post" );
        alarmList.setText(Server.getResultFromDB());
        //Log.e("DB",Server.getResultFromDB());
        //tvData.setText( Server.getResultFromDB() ); //화면띄우기
    }
}



