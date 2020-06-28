package org.techtown.mediclock;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Mediname extends AppCompatActivity {
    ActionBar actionBar;
    public static String realmediname;
   // public static Context context;


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
        //final String alarmmediname;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mediname);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("약이름설정");
        //액션바 배경색 변경#368AFF
        //EditText name= (EditText)findViewById(R.id.edit);

        //alarmmediname =  ((EditText)findViewById(R.id.edit)).getText().toString();

        //final String value = name.getText().toString();

        //mediname2 =((EditText)findViewById(R.id.edit)).getText().toString();
        final EditText Alarmname = (EditText)findViewById(R.id.edit);


        Button button = findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmediname = Alarmname.getText().toString();
                Intent intent_meditime = new Intent(getApplicationContext(), Mediweek.class); //일단 바로 검색결과 띄음
                startActivity(intent_meditime);

                Toast toastView = Toast.makeText(getApplicationContext(), "약 이름이 '"+((EditText)findViewById(R.id.edit)).getText().toString()+"'로 설정되었습니다" , Toast.LENGTH_LONG);

                toastView.setGravity( Gravity.TOP| Gravity.LEFT, 180, 700); //토스트 메시지 위치 x,y좌표로 바꿀 수 있다.
                toastView.show();

            }
        });


    }
/*
    public String getString() {
        EditText name= (EditText)findViewById(R.id.edit);
        alarmmediname =  name.getText().toString();
        return alarmmediname;
    }*/
}
