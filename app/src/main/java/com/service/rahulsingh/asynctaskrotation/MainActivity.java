package com.service.rahulsingh.asynctaskrotation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<String> listItem;
   // ListView listView;
    String responseMain;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  listView=(ListView)findViewById(R.id.listView);
       // listItem=new ArrayList<>();
        String url="https://api.github.com/users/facebook/repos";
        pd=new ProgressDialog(getApplicationContext());
      //  new AsyncClass().execute(url);
    }
    class AsyncClass extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

         //   pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //String url=params[0];
           // pd.show();
            HttpURLConnection  httpURLConnection=null;
            try {
                URL url1=new URL(params[0]);
                Log.i("URL IS",url1.toString());
               httpURLConnection=(HttpURLConnection)url1.openConnection();
               httpURLConnection.setDoInput(true);
              // httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(2000);

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line=null;
                StringBuilder sb=new StringBuilder();
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();
                responseMain=sb.toString();
                Log.d("Response",responseMain);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseMain;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
         //   pd.dismiss();
            try {
                //JSONObject jsonObject=new JSONObject(s);
                Log.d("JSonData",s);
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String name=jsonObject.getString("name");
                    listItem.add(name);
                    Log.d("ListItem",name);
                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.custom_textview,listItem);
                //listView.setAdapter(arrayAdapter);
                Toast.makeText(getApplicationContext(),"List Size: "+listItem.size(),Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void actvity2(View view){
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
