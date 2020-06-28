package org.techtown.mediclock;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;


import static org.techtown.mediclock.AlarmList.flag;
import static org.techtown.mediclock.WritecodeActivity.shareFlag;
import static org.techtown.mediclock.WritecodeActivity.sharedArrayList;
import static org.techtown.mediclock.alarmsetlist.alarmArrayList;


public class Server {
    static String SettingAlarmList ="";
    static String DBresult;
    Context context;


    public Server(Context context) {
        this.context = context;
    }

    public static class JSONTask1 extends AsyncTask<String, String, String> {

        public JSONTask1() {
            super();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);
                    con = (HttpURLConnection) url.openConnection();
                    //   con.connect();

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while ((line = reader.readLine()) != null) {
                        Log.e("line", line);
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("null", "This is null");
            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            DBAnswer(result);
            // tvData.setText(result);
        }

    }
    /*
    //원본
        public class JSONTask2 extends AsyncTask<String, String, String>{

            @Override
            protected String doInBackground(String... urls) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("name", "b");

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try{
                        //URL url = new URL("http://192.168.25.16:3000/users");
                        URL url = new URL(urls[0]);
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Cache-Control", "no-cache");
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestProperty("Accept", "text/html");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.connect();

                        OutputStream outStream = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();

                        InputStream stream = con.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(stream));

                        StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while((line = reader.readLine()) != null){
                            buffer.append(line);
                        }

                        return buffer.toString();

                    } catch (MalformedURLException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(con != null){
                            con.disconnect();
                        }
                        try {
                            if(reader != null){
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                tvData.setText(result);
            }
        }
    */
//    public static class Show_alarmList extends AsyncTask<String, String, String>{
//
//        String alarm_user_code;
//
//        public Show_alarmList(String alarm_user_code) {
//            this.alarm_user_code = alarm_user_code;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //progress bar를 보여주는 등등의 행위
//        }
////        protected void onPreExecute() {
////            Log.e("HERE...","OnPREEXECUTE");
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.accumulate("alarm_user_code", alarm_user_code); //(2):찾으려는 요소 넣기
//
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    URL url = new URL(urls[0]);
//                    //연결을 함
//                    con = (HttpURLConnection) url.openConnection();
//
//
//                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//
//                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    con.connect();
//
//                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    Log.e("Json?",jsonObject.toString());
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//
//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//                  return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            Toast.makeText(MainActivity.getAppContext(), result, Toast.LENGTH_SHORT).show();
//            DBAnswer(result);
//        }
//    }

    public static class Show_alarmList extends AsyncTask<String, String, String>{

        String alarm_user_code;

        public Show_alarmList(String alarm_user_code) {
            this.alarm_user_code = alarm_user_code;
            Log.e("USER_CODE ADDED",alarm_user_code);
            Log.e("너가 문제니?2222",alarm_user_code);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }
        //        protected void onPreExecute() {
//            Log.e("HERE...","OnPREEXECUTE");
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("alarm_user_code", alarm_user_code); //(2):찾으려는 요소 넣기


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();


                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정

                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    Log.e("Json?",jsonObject.toString());
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//                String SettingAlarmList ="";
            //           >>>>>>>>>>>>>>>>>>  parsing 하는 부분 : SettingAlarmList에 시간을 스트링으로 담아 출력함
            try {
                JSONArray jsonArray= new JSONArray(result);

//                String SettingAlarmList ="";

                for(int i=0; i<jsonArray.length();i++){

                    JSONObject jo=jsonArray.getJSONObject(i);
                    String alarm_name = jo.getString("alarm_name");
                    Log.e("alarm_name : ", alarm_name );

                    JSONObject jo2=jsonArray.getJSONObject(i);
                    String day = jo2.getString("day");
                    Log.e("day : ", day );

                    if(i == 0) {
                        SettingAlarmList += "설정하신 알람 목록입니다. \n" ; }
                    if(i != jsonArray.length()-1) {

                        AlarmListFromDB data =  new AlarmListFromDB(alarm_name,day);
                        if(shareFlag==1){
                            sharedArrayList.add(data);
                            Log.e("Shared List-서버 ", String.valueOf(sharedArrayList.size()));
                        }else{
                            alarmArrayList.add(data);
                        }
                        SettingAlarmList += "알람이름 "+ alarm_name+ " ";
                        SettingAlarmList += "알람 설정 시간 "+day+ " \n ";
                    }else{
                        AlarmListFromDB data =  new AlarmListFromDB(alarm_name,day);
                        if(shareFlag==1){
                            sharedArrayList.add(data);
                        }else{
                            alarmArrayList.add(data);
                        }

                        Log.e("지금 CLASS Alarmsetlist에서","미리 만들어지는 중입니다.");


                        SettingAlarmList += "알람이름 "+ alarm_name+ " ";
                        SettingAlarmList += "알람 설정 시간 "+day+ " 입니다.";
                        DBresult= SettingAlarmList;
                        Log.e("DBresult",DBresult);
                        //DBAnswer(SettingAlarmList);
                    }
//
//                    else {
//                        SettingAlarmList += " 끝입니다. ";
//                        //SettingAlarmList += day+ " 입니다. ";
//                        //SettingAlarmList +=  + " 입니다. ";
//                    }
                    //  JSONObject flag=jo.getJSONObject("flag");
                }
                Log.e("TIME & DAY : ", SettingAlarmList );


                SettingAlarmList = "";
            } catch (JSONException e) {
                Log.e("JSON","JSON으로 변환 못해서 가져오는 중");
                e.printStackTrace();
            }

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< parsing 된 부분 추가 끝
//Toast.makeText(MainActivity.getAppContext(), result, Toast.LENGTH_SHORT).show();
            //DBAnswer(SettingAlarmList);
        }
    }

    public static class Update_alarmList extends AsyncTask<String, String, String>{
        String alarm_user_code;
        String time;
        String day;
        String alarm_name;

        public Update_alarmList(String alarm_user_code,String time, String day,String alarm_name) {
            this.alarm_user_code = alarm_user_code;
            this.time = time ;
            this.day = day;
            this.alarm_name = alarm_name;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("time", time);
                jsonObject.accumulate("day", day);
                jsonObject.accumulate("alarm_user_code", alarm_user_code); //(2)찾으려는 요소 넣기
                jsonObject.accumulate("alarm_name", alarm_name);





                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정

                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    Log.e("Json?",jsonObject.toString());
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부분
            //Toast.makeText(MainActivity.getAppContext(), time+"시 "+day+"요일 알람이 새로 업데이트 되었습니다.",Toast.LENGTH_LONG).show();
        }
    } //완성

    public static class JSONTask4 extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("email", "changed-Email"); //(2)찾으려는 요소 넣기
                jsonObject.accumulate("name", "f");


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정

                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    Log.e("Json?",jsonObject.toString());
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
            Log.e("HII","HHHHHHHERE");
            //
        }
    }

    public static void DBAnswer (String result){
        DBresult = result;
        Log.e("DBresult",DBresult);
    }
    public static String getResultFromDB(){
        Log.e("getREsultFromDB","화면에 줄 결과값"+DBresult);
        return  DBresult;
    }

}