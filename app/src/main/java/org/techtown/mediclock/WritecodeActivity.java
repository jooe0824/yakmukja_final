package org.techtown.mediclock;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class WritecodeActivity extends AppCompatActivity {
    ActionBar actionBar;
    public static String usercode;
    static ArrayList<AlarmListFromDB> sharedArrayList;
    static int shareFlag=0;


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
            Intent homeIntent = new Intent(this,Mainmenu.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_writecode);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("코드 입력");
        //액션바 배경색 변경#368AFF

        //final EditText inputcode = (EditText)findViewById(R.id.inputcode);



        Button button = findViewById(R.id.confirmwritecode_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usercode = ((EditText)findViewById(R.id.inputcode)).getText().toString();

                Log.e("너가 문제니?3333", usercode);

                Log.e("너가 문제니?", ((EditText)findViewById(R.id.inputcode)).getText().toString());

                //  Intent intent_shareactivity = new Intent(getApplicationContext(), AlarmList.class); //일단 바로 검색결과 띄음
                //   startActivity(intent_shareactivity);
                sharedArrayList = new ArrayList<>();
                shareFlag = 1;
                Log.e("Shared List -알람List ", String.valueOf(sharedArrayList.size()));
                new Server.Show_alarmList(usercode ).execute("http://192.168.0.10:3306/post" );
            }
        });

        Button confirm_btn = findViewById(R.id.btn_confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent_shareactivity = new Intent(getApplicationContext(), AlarmList.class); //일단 바로 검색결과 띄음
                startActivity(intent_shareactivity);
            }
        });

    }
}
