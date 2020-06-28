package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TextresultActivity extends AppCompatActivity implements RecyclerviewAdapter.onItemListener, TextWatcher {
    ActionBar actionBar;
    public String requestUrl;
    Recent bus = null;
    public String key="BZAkHyL1OvsaKk4INUgYd1ra39ts5cl%2BDojvvOH%2BQkW3FCIifva%2FTa5ZTKvrIt03W97NKmFMZH4Oq%2B6jIwy5bA%3D%3D";
    private RecyclerviewAdapter adapter;
    ArrayList<Recent> mList;

    public String barcodeinfo, photextinfo;
    EditText editText;
    // Button gosearch_btn;
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
        setContentView(R.layout.activity_textresult);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("알람시간설정");
        //액션바 배경색 변경#368AFF

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        editText = (EditText) findViewById(R.id.editText);
        if(getIntent().getStringExtra("BAR_CODE_info") != null) {
            barcodeinfo = getIntent().getStringExtra("BAR_CODE_info");
            Log.e("barcodeinfo", barcodeinfo);
            editText.setText(barcodeinfo);
        }

        if(getIntent().getStringExtra("photext_info") != null){
            photextinfo = getIntent().getStringExtra("photext_info");
            Log.e("photextinfo", photextinfo);
            Toast myToast = Toast.makeText(this.getApplicationContext(),"사진 검색이 완료되었습니다. 입력창에 아무 글자나 입력하고 지워주세요", Toast.LENGTH_SHORT);
            myToast.show();
        }

        editText.addTextChangedListener(this);

//        gosearch_btn = findViewById(R.id.gosearch_btn);
//        gosearch_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //adapter.getFilter().filter(s);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String filterText = s.toString() ;
        adapter.getFilter().filter(filterText);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit (String query){
//                    Log.d("query", query);
//
//                return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange (String newText){
//
//
//                    Log.e("NEWTEXT", newText);
//                    adapter.getFilter().filter(newText);
//                    return false;
//            }
//        });
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "클릭" + position, Toast.LENGTH_SHORT).show();
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        //parser.require(XmlPullParser.START_TAG, "", "EE_DOC_DATA");
        //parser.next();//DOC
        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
////                continue;
////            }
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String start = parser.getName();
                Log.d("start1", start);

                if (start.equals("SECTION")) {
                    if (parser.getAttributeValue(0) != null) {
                        String sec = parser.getAttributeValue(0);
                        Log.d("sec", sec);
                        link = link + sec;
                    }
                } else if (start.equals("ARTICLE")) {
                    String ti = parser.getAttributeValue(0);//ARTICLE first line
                    Log.d("test", ti);
                    link = link + ti;

                    //1번만 하려면 이렇게 해야 함
                    parser.nextTag();//ARTICLE
                    String check = parser.getName();
                    Log.d("check", check);

                    if (check.equals("PARAGRAPH")) {
                        String tag2 = parser.nextText();//paragraph 내용
                        Log.d("art para", tag2);
                        link = link + tag2;

                        parser.nextTag();
                        String tag3 = parser.getName();
                        Log.d("tag3", tag3);

                        if (tag3.equals("PARAGRAPH")) {
                            String tag4 = parser.nextText();//paragraph 내용
                            Log.d("art para para", tag4);
                            link = link + tag4;

                            parser.nextTag();
                            String check2 = parser.getName();
                            Log.d("check2", check2);

                        }
                    }
                    //#4,7,8,9 section -> article -> /section -> section 과정 안 넘어감
                }
//            else if (start.equals("PARAGRAPH")){
//                String tag3 = parser.nextText();//paragraph 내용
//                Log.d("para test3", tag3);
//                link = link + tag3;
//
//                //2번째 약은 이렇게 해야 다 뜸
//                parser.nextTag();
//                String art2 = parser.getName();
//                Log.d("art2", art2);


//                if(parser.next() != XmlPullParser.END_TAG){
//                    continue;
//                } else {
//                    parser.nextTag();
//                    String art2 = parser.getName();
//                    Log.d("art2", art2);
//                }


                // }

                //parser.nextTag();//paragraph
                //String tag = parser.getName();

//            if (parser.getName().equals("ARTICLE")) {
//                Log.d("art test2", tag);
//                String tag2 = parser.getAttributeValue(0);
//                Log.d("art test3", tag2);
//                link = link + tag2;
//
//            } else if (parser.getName().equals("PARAGRAPH")) {
//                Log.d("para test2", tag);
//                String tag2 = parser.nextText();//paragraph 내용
//                Log.d("para test3", tag2);
//                link = link + tag2;
//            }
            }

//        parser.nextTag();//ARTICLE
//        String tag3 = parser.getName();
//        Log.d("test4", tag3);

//        String ti2 = parser.getAttributeValue(null, "title");
//        Log.d("test5", ti2);
            //String tag2 = parser.getText();


//        parser.nextTag();
//        String stag2 = parser.getName();//ARTICLE
//        Log.d("test5", stag2);
//        String ti2 = parser.getAttributeValue(null, "title");
//        Log.d("test5", ti2);

//        String tag = parser.getName();
//        if (tag.equals("DOC")) {
//            String relType = parser.getAttributeValue(null, "title");
//            Log.d("test", relType);
//            if (relType.equals("효능효과")){
//                parser.nextTag();//section
//                parser.nextTag();//article
//                String stag = parser.getName();
//                if(stag.equals("ARTICLE")) {
//                    Log.d("test3", stag);
//                    String ti = parser.getAttributeValue(null, "title");
//                    Log.d("test4", ti);
//                    parser.next();
//                    String stag2 = parser.getName();
//                    String ti2 = parser.getAttributeValue(null, "title");
//                    Log.d("test5", stag2);


//                    parser.next();
//                    String stag3 = parser.getName();
//                    Log.d("test6", stag3);
//                    parser.next();
//                    String stag4 = parser.getName();
//                    Log.d("test5", stag2);
//                    parser.next();
//                    String stag5 = parser.getName();
//                    Log.d("test5", stag2);
//
//                    if(stag2.equals("ARTICLE")){
//                        String ti2 = parser.getAttributeValue(0);
//                        Log.d("test6", ti2);
//                    }
                    /*parser.nextTag();
                    String tag2 = parser.getName();
                    Log.d("test2", tag2);
                    if (tag2.equals("ARTICLE")) {
                        Log.d("test3", tag2);
                        String ti = parser.getAttributeValue(null, "title");
                        Log.d("test4", ti);
                    }
                    parser.nextTag();
                    tag2 = parser.getName();
                    Log.d("test5", tag2); */
            //}
            //  }
            // }
            //parser.require(XmlPullParser.END_TAG, "", "EE_DOC_DATA");

        }
        return link;
    }

    /*private String readLink2(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link2 = "";
        parser.require(XmlPullParser.START_TAG, "", "UD_DOC_DATA");
        parser.next();//DOC

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String start = parser.getName();
            Log.d("ud start", start);

            if (start.equals("SECTION")) {
                if (parser.getAttributeValue(0) != null) {
                    String sec = parser.getAttributeValue(0);
                    Log.d("ud sec", sec);
                    link2 = link2 + sec;
                }
            } else if (start.equals("ARTICLE")) {
                String ti = parser.getAttributeValue(0);//ARTICLE first line
                Log.d("ud test", ti);
                link2 = link2 + ti;

                //1번만 하려면 이렇게 해야 함
                parser.nextTag();//ARTICLE
                String check = parser.getName();
                Log.d("ud check", check);

//                parser.nextTag();
//                String tag5 = parser.getName();
//                Log.d("tag5", tag5);
//
//                parser.nextTag();
//                String tag6 = parser.getName();
//                Log.d("tag6", tag6);

                if (check.equals("PARAGRAPH")) {
                    String tag2 = parser.nextText();//paragraph 내용
                    Log.d("ud art para", tag2);
                    link2 = link2 + tag2;

                    parser.nextTag();
                    String tag3 = parser.getName();
                    Log.d("ud tag3", tag3);

                    if (tag3.equals("PARAGRAPH")) {
                        String tag4 = parser.nextText();//paragraph 내용
                        Log.d("ud art para para", tag4);
                        link2 = link2 + tag4;

                        parser.nextTag();
                        String check2 = parser.getName();
                        Log.d("ud check2", check2);

                    }
                }
                //#4,7,8,9 section -> article -> /section -> section 과정 안 넘어감
            }
        }
        return link2;
    }*/

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            requestUrl = "http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?"+"pageNo=1257&numOfRows=10&"+"ServiceKey=" + key;
            try {
                boolean b_ITEM_NAME = false;
                boolean b_ENTP_NAME = false;
                boolean b_ETC_OTC_CODE = false;
                boolean b_ITEM_PERMIT_DATE = false;
                boolean b_ENTP_NO = false;
                boolean b_BAR_CODE = false;
                boolean b_ITEM_SEQ = false;
                boolean b_CHART = false;
                boolean b_MATERIAL_NAME = false;
                boolean b_PACK_UNIT = false;
                boolean b_PERMIT_KIND_NAME = false;
                boolean b_CANCEL_DATE = false;
                boolean b_MAKE_MATERIAL_FLAG = false;
                boolean b_INDUTY_TYPE = false;
                boolean b_CHANGE_DATE = false;
                boolean b_INGR_NAME = false;
                boolean b_STORAGE_METHOD = false;
                boolean b_VALID_TERM = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            mList = new ArrayList<Recent>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item") && bus != null) {
                                mList.add(bus);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("item")) {
                                bus = new Recent();
                            }
                            if (parser.getName().equals("ITEM_NAME")) b_ITEM_NAME = true;
                            if (parser.getName().equals("ENTP_NAME")) b_ENTP_NAME = true;
                            if (parser.getName().equals("ETC_OTC_CODE")) b_ETC_OTC_CODE = true;
                            if (parser.getName().equals("ITEM_PERMIT_DATE")) b_ITEM_PERMIT_DATE = true;
                            if (parser.getName().equals("ENTP_NO")) b_ENTP_NO = true;
                            if (parser.getName().equals("BAR_CODE")) b_BAR_CODE = true;
                            if (parser.getName().equals("ITEM_SEQ")) b_ITEM_SEQ = true;
                            if (parser.getName().equals("CHART")) b_CHART = true;
                            if (parser.getName().equals("MATERIAL_NAME")) b_MATERIAL_NAME = true;
                            if (parser.getName().equals("PACK_UNIT")) b_PACK_UNIT = true;
                            if (parser.getName().equals("PERMIT_KIND_NAME")) b_PERMIT_KIND_NAME = true;
                            if (parser.getName().equals("CANCEL_DATE")) b_CANCEL_DATE = true;
                            if (parser.getName().equals("MAKE_MATERIAL_FLAG")) b_MAKE_MATERIAL_FLAG = true;
                            if (parser.getName().equals("INDUTY_TYPE")) b_INDUTY_TYPE = true;
                            if (parser.getName().equals("CHANGE_DATE")) b_CHANGE_DATE = true;
                            if (parser.getName().equals("INGR_NAME")) b_INGR_NAME = true;
                            if (parser.getName().equals("EE_DOC_DATA")) bus.setEE_DOC_DATA(readLink(parser));
                            if (parser.getName().equals("UD_DOC_DATA")) bus.setUD_DOC_DATA(readLink(parser));
                            if (parser.getName().equals("NB_DOC_DATA")) bus.setNB_DOC_DATA(readLink(parser));
                            if (parser.getName().equals("STORAGE_METHOD")) b_STORAGE_METHOD = true;
                            if (parser.getName().equals("VALID_TERM")) b_VALID_TERM = true;

                            break;

                        case XmlPullParser.TEXT:
                            if (b_ITEM_NAME) {
                                bus.setITEM_NAME(parser.getText());
                                b_ITEM_NAME = false;
                            } else if (b_ENTP_NAME) {
                                bus.setENTP_NAME(parser.getText());
                                b_ENTP_NAME = false;
                            } else if (b_ETC_OTC_CODE) {
                                bus.setETC_OTC_CODE(parser.getText());
                                b_ETC_OTC_CODE = false;
                            } else if (b_ITEM_PERMIT_DATE) {
                                bus.setITEM_PERMIT_DATE(parser.getText());
                                b_ITEM_PERMIT_DATE = false;
                            } else if (b_ENTP_NO) {
                                bus.setENTP_NO(parser.getText());
                                b_ENTP_NO = false;
                            } else if (b_BAR_CODE) {
                                bus.setBAR_CODE(parser.getText());
                                b_BAR_CODE = false;
                            } else if (b_ITEM_SEQ) {
                                bus.setITEM_SEQ(parser.getText());
                                b_ITEM_SEQ = false;
                            } else if (b_CHART) {
                                bus.setCHART(parser.getText());
                                b_CHART = false;
                            } else if (b_MATERIAL_NAME) {
                                bus.setMATERIAL_NAME(parser.getText());
                                b_MATERIAL_NAME = false;
                            } else if (b_PACK_UNIT) {
                                bus.setPACK_UNIT(parser.getText());
                                b_PACK_UNIT = false;
                            } else if (b_PERMIT_KIND_NAME) {
                                bus.setPERMIT_KIND_NAME(parser.getText());
                                b_PERMIT_KIND_NAME = false;
                            } else if (b_CANCEL_DATE) {
                                bus.setCANCEL_DATE(parser.getText());
                                b_CANCEL_DATE = false;
                            } else if (b_MAKE_MATERIAL_FLAG) {
                                bus.setMAKE_MATERIAL_FLAG(parser.getText());
                                b_MAKE_MATERIAL_FLAG = false;
                            } else if (b_INDUTY_TYPE) {
                                bus.setINDUTY_TYPE(parser.getText());
                                b_INDUTY_TYPE = false;
                            } else if (b_CHANGE_DATE) {
                                bus.setCHANGE_DATE(parser.getText());
                                b_CHANGE_DATE = false;
                            } else if (b_INGR_NAME) {
                                bus.setINGR_NAME(parser.getText());
                                b_INGR_NAME = false;
                            } else if (b_STORAGE_METHOD) {
                                bus.setSTORAGE_METHOD(parser.getText());
                                b_STORAGE_METHOD = false;
                            } else if (b_VALID_TERM) {
                                bus.setVALID_TERM(parser.getText());
                                b_VALID_TERM= false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RecyclerView recyclerView = findViewById(R.id.recyclerview_medlist);
            adapter = new RecyclerviewAdapter(getApplicationContext(), (ArrayList<Recent>) mList);
            recyclerView.setAdapter(adapter);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);


        }
    }

}
