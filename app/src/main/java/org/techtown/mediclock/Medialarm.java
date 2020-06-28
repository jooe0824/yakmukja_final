package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class Medialarm extends AppCompatActivity {
  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_medialarm, container, false); //container 라는 뷰 안에 첫번째 인자를 넣어줘라, 메모리에 올려준 것

        Button bbutton = rootView.findViewById(R.id.pause);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(0);
            }
        });

        return rootView;
    }
}
*/
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
        setContentView(R.layout.fragment_medialarm);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("약 묵 자");
        //액션바 배경색 변경#368AFF

        Button button1 = findViewById(R.id.pause);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent_meditime = new Intent(getApplicationContext(), AlarmList.class); //일단 바로 검색결과 띄음
                startActivity(intent_meditime);

                Toast toastView = Toast.makeText(getApplicationContext(), "중단되었습니다", Toast.LENGTH_LONG);
                toastView.setGravity( Gravity.TOP| Gravity.LEFT, 350, 700); //토스트 메시지 위치 x,y좌표로 바꿀 수 있다.
                toastView.show();
               // Intent intent_mainmenu = new Intent(getApplicationContext(), Mainmenu.class); //일단 바로 검색결과 띄음
                // startActivity(intent_mainmenu);
//중단되었습니다 라는 문구 뜨게끔
                //10분뒤 알람이라는 문구 뜨게끔
            }
        });
        Button button2 = findViewById(R.id.after10);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toastView = Toast.makeText(getApplicationContext(), "10분 뒤 알람이 다시 설정되었습니다", Toast.LENGTH_LONG);
                toastView.setGravity( Gravity.TOP| Gravity.LEFT, 200, 700); //토스트 메시지 위치 x,y좌표로 바꿀 수 있다.
                toastView.show();

            }
        });
    }
}