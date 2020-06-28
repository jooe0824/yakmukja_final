package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ShareAlarm extends AppCompatActivity {

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
        setContentView(R.layout.fragment_sharealarm);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람공유");
        //액션바 배경색 변경#368AFF


        Button button1 = findViewById(R.id.seecode);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_seecodeactivity = new Intent(getApplicationContext(), SeecodeActivity.class); //일단 바로 검색결과 띄음
                startActivity(intent_seecodeactivity);
            }
        });
        Button button2 = findViewById(R.id.writecode);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_writecodeactivity = new Intent(getApplicationContext(), WritecodeActivity.class); //일단 바로 검색결과 띄음
                startActivity(intent_writecodeactivity);
            }
        });

    }
}
