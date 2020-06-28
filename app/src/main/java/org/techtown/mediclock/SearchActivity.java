package org.techtown.mediclock;
//SearchActivity

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SearchActivity extends AppCompatActivity {
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
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        setContentView(R.layout.activity_search);

        Button QR_searchbtn = findViewById(R.id.QR_searchbtn);
        QR_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QR_intent = new Intent(getApplicationContext(), Search_ResultList.class); //일단 바로 검색결과 띄음
                startActivity(QR_intent);
            }
        });

        Button text_searchbtn= findViewById(R.id.text_searchbtn);
        text_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent text_intent = new Intent (getApplicationContext(), Search_Text.class); //일단 바로 검색결과 띄음
                startActivity(text_intent);
            }
        });



    }
=======
        setContentView(R.layout.fragment_search);
    }
>>>>>>> Stashed changes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false); //container 라는 뷰 안에 첫번째 인자를 넣어줘라, 메모리에 올려준 것

        Button bbutton = rootView.findViewById(R.id.QR_searchbtn);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(15);
            }
        });
        Button bbutton2 = rootView.findViewById(R.id.photo_searchbtn);
        bbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(15);
            }
        });
        Button bbutton3 = rootView.findViewById(R.id.text_searchbtn);
        bbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(15);
            }
        });
        return rootView;
    }
}*/


    ImageView iv = null;

@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_search);

    actionBar = getSupportActionBar();
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
    getSupportActionBar().setTitle("약검색");
    //액션바 배경색 변경#368AFF


    Button button1 = findViewById(R.id.QR_searchbtn);
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_barcode = new Intent(getApplicationContext(), BarcodeActivity.class); //일단 바로 검색결과 띄음
            startActivity(intent_barcode);
        }
    });

    Button button2 = findViewById(R.id.photo_searchbtn);
    button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_photosearch = new Intent(getApplicationContext(), Search_Photo.class); //일단 바로 검색결과 띄음
            startActivity(intent_photosearch);
        }
    });

    Button button3 = findViewById(R.id.text_searchbtn);
    button3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_textsearch = new Intent(getApplicationContext(), TextresultActivity.class); //일단 바로 검색결과 띄음
            startActivity(intent_textsearch);
        }
    });

 /*   setup();

}
    private void setup() {
        Button button = (Button) findViewById(R.id.photo_searchbtn);
        iv = (ImageView) findViewById(R.id.iv);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        iv.setImageURI(data.getData());
    }*/
}
}


