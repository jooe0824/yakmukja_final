package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EffectinfoActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_effectinfo);


        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));

        //액션바 배경색 변경#368AFF

        if(getIntent().getStringExtra("EE_DOC_DATA") != null) {
            getSupportActionBar().setTitle("효능 효과");
            String EE_DOC_DATA = getIntent().getStringExtra("EE_DOC_DATA"); //효능효과
            Log.e("EE_DOC_DATA", EE_DOC_DATA);

            TextView textView34 = (TextView) findViewById(R.id.textView34);
            textView34.setText("효능 효과");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(EE_DOC_DATA);
        }

        if(getIntent().getStringExtra("chart") != null) {
            getSupportActionBar().setTitle("제품 모양");
            String CHART = getIntent().getStringExtra("chart");

            TextView textView34 = (TextView) findViewById(R.id.textView34);
            textView34.setText("제품 모양");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(CHART);
        }

        if(getIntent().getStringExtra("STORAGE_METHOD") != null) {
            getSupportActionBar().setTitle("저장 방법");
            String STORAGE_METHOD = getIntent().getStringExtra("STORAGE_METHOD");

            TextView textView34 = (TextView) findViewById(R.id.textView34);
            textView34.setText("저장 방법");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(STORAGE_METHOD);
        }

        if(getIntent().getStringExtra("VALID_TERM") != null) {
            getSupportActionBar().setTitle("유효 기간");
            String VALID_TERM = getIntent().getStringExtra("VALID_TERM");

            TextView textView34 = (TextView) findViewById(R.id.textView34);
            textView34.setText("유효 기간");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(VALID_TERM);
        }

        if(getIntent().getStringExtra("material_name") != null) {
            getSupportActionBar().setTitle("원료 및 성분");

            String MATERIAL_NAME = getIntent().getStringExtra("material_name");//원료성분

            TextView textView34 = (TextView) findViewById(R.id.textView34);
            textView34.setText("원료 및 성분");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(MATERIAL_NAME);
        }
        if(getIntent().getStringExtra("UD_DOC_DATA") != null) {
            getSupportActionBar().setTitle("용법 용량");
            String UD_DOC_DATA = getIntent().getStringExtra("UD_DOC_DATA"); //효능효과
            Log.d("UD", UD_DOC_DATA);

            TextView textView37 = (TextView) findViewById((R.id.textView34));
            textView37.setText("용법 용량");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(UD_DOC_DATA);
        }

        if(getIntent().getStringExtra("NB_DOC_DATA") != null) {
            getSupportActionBar().setTitle("주의 사항");
            String NB_DOC_DATA = getIntent().getStringExtra("NB_DOC_DATA"); //효능효과

            TextView textView37 = (TextView) findViewById((R.id.textView34));
            textView37.setText("주의사항");

            TextView textView36 = (TextView) findViewById(R.id.textView36);
            textView36.setText(NB_DOC_DATA);
        }

    }
}