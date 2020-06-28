package org.techtown.mediclock;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;

import static org.techtown.mediclock.Server.getResultFromDB;
import static org.techtown.mediclock.WritecodeActivity.shareFlag;
import static org.techtown.mediclock.WritecodeActivity.sharedArrayList;
import static org.techtown.mediclock.alarmsetlist.alarmArrayList;

public class AlarmList extends AppCompatActivity {

    ActionBar actionBar;
    String android_sub;
    protected static ArrayList<AlarmListFromDB> alarmArrayList2;
    private AlarmListAdapter alarmListAdapter;
    public static int flag = 0;


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
        //  setContentView(R.layout.alarm_list);
        setContentView(R.layout.alarm_list_recyclerview); //xml 변경

        // alarmArrayList = new ArrayList<>();
//        AlarmListFromDB data =  new AlarmListFromDB("test이름","test 번호");
//        alarmArrayList.add(data);
        //     Log.e("AlarmArrayList 사이즈", String.valueOf(alarmArrayList.size()));


        TextView alarmList = (TextView) findViewById(R.id.alarmlist);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람목록");
        //액션바 배경색 변경#368AFF
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        android_sub = android_id.substring(8);

        //new Server.Show_alarmList( android_sub ).execute("http://192.168.0.10:3306/post" );
        // alarmList.setText(Server.getResultFromDB());

        //>>>>>>>>>>>> RecyclerView 수정부분 - 시작 -
        RecyclerView alarm_recyclerView = (RecyclerView) findViewById(R.id.alarm_list_recyclerview);
        LinearLayoutManager alarm_LinearLayoutManager = new LinearLayoutManager(this);
        alarm_recyclerView.setLayoutManager(alarm_LinearLayoutManager);

        Log.e("222222222222","미리 만들어진LIST 띄울거 만드는 중");
        alarmArrayList2 = new ArrayList<>();
        if(shareFlag==1){ //공유됐을 때
            alarmArrayList2 = sharedArrayList;
            Log.e("Shared List -알람L ", String.valueOf(sharedArrayList.size()));
        }else{
            alarmArrayList2 = alarmArrayList;
            shareFlag =0;
        }
        alarmListAdapter = new AlarmListAdapter(alarmArrayList2);
        alarm_recyclerView.setAdapter(alarmListAdapter);
        alarmArrayList2 = new ArrayList<>();
        
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(alarm_recyclerView.getContext(),
                alarm_LinearLayoutManager.getOrientation());
        alarm_recyclerView.addItemDecoration(dividerItemDecoration);

        alarmListAdapter.notifyDataSetChanged();
        //>>>>>>>>>>>> RecyclerView 수정부분 - 끝 -

    }


}




