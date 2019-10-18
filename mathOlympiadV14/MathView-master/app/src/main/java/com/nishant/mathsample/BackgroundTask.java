package com.nishant.mathsample;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
import java.net.URLEncoder;
import java.util.StringTokenizer;

public class BackgroundTask extends AsyncTask<String,Void,String> {
    TextView problem;
    AlertDialog alertDialog;
    Context ctx;
    final MyDatabaseHelper myDatabaseHelper;
    Cursor cursor;

    BackgroundTask(Context ctx){
        this.ctx=ctx;
        this.problem=problem;

         myDatabaseHelper=new MyDatabaseHelper(ctx);
        // final SQLiteDatabase database =myDatabaseHelper.getWritableDatabase();

        cursor=myDatabaseHelper.showAllData();


    }

    @Override
    protected synchronized void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Alert Note");
    }

    @Override
    protected synchronized String doInBackground(String ... params) {
        String reg_url=DbContract.SERVER_URL2;
        String login_url=DbContract.SERVER_URL3;//dataFetch.php
        String allDataFetchingUrl=DbContract.ALL_DATA_FETCHING_URL;



        String method=params[0];

        if(method.equals("saveOnline")){
            String Title=params[1];
            String Problem=params[2];
            String Solution=params[3];
            String Tag=params[4];
            String Setter=params[5];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(Title,"UTF-8")+"&"+
                        URLEncoder.encode("problem","UTF-8")+"="+URLEncoder.encode(Problem,"UTF-8")+"&"+
                        URLEncoder.encode("solution","UTF-8")+"="+URLEncoder.encode(Solution,"UTF-8")+"&"+
                        URLEncoder.encode("tag","UTF-8")+"="+URLEncoder.encode(Tag,"UTF-8")+"&"+
                        URLEncoder.encode("setter","UTF-8")+"="+URLEncoder.encode(Setter,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS=httpURLConnection.getInputStream();
                IS.close();

                return "Data Saved to server";
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("saveLocal")){

            //Don't use this  method for saveToLocal rather we used the next "saveFromServer"
            String problemId=params[1];




            try {

                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("problemId","UTF-8")+"="+URLEncoder.encode(problemId,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String response="";
                String line="";
                String problemId1="",Title="",Problem="",Solution="",Tag="",Setter="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  response;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }


        }
        else if(method.equals("saveFromServer")){


            //retrive data from json object begin



                String result=null;
                InputStream is=null;

                try {
                    URL url = new URL(allDataFetchingUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setRequestMethod("GET");
                     is = new BufferedInputStream(con.getInputStream());
                }catch(Exception e){
                    e.printStackTrace();
                }
                //read is content into a string
                try{
                    BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    StringBuilder sb=new StringBuilder();
                    String line=null;
                    while((line=br.readLine())!=null){

                        sb.append(line+"\n");

                    }
                    is.close();
                    result=sb.toString();

                }catch(Exception e){
                    e.printStackTrace();
                }
                //parse json data
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null,joLastDateTime=null;

                    for(int i=0;i<ja.length();i++){

                        joLastDateTime=ja.getJSONObject(0);//first row of index will have last update date and time
                        final int lastUpdateDate=Integer.parseInt(joLastDateTime.getString("lastUpdateDate"));
                        final int lastUpdateTime=Integer.parseInt(joLastDateTime.getString("lastUpdateTime"));

                        jo=ja.getJSONObject(i);

                        String ProblemId=jo.getString("problemId");
                        final String Title=jo.getString("title");//Title
                        final String Problem=jo.getString("problem");//problemStatement
                        final String Solution=jo.getString("solution");//solution
                        final String Tag=jo.getString("tag");//Tags
                        final String Setter=jo.getString("setter");// problem setter
                        final int updateDate=Integer.parseInt(jo.getString("updateDate"));//year_month_date
                        final int updateTime=Integer.parseInt(jo.getString("updateTime"));//internation 24 formate hour only


                        final int sync_status=DbContract.SYNC_STATUS_OK;

                       if (cursor.moveToNext()){

                           final int lastUpdateDate2=cursor.getInt(9);//lastupdateDate on local
                           final int lastUpdateTime2=cursor.getInt(10);//lastUpdateTime on local

                           if((lastUpdateDate==lastUpdateDate2) && (lastUpdateTime==lastUpdateTime2) ){
                               return "Local Data up to Date";
                           }


                           final String ProblemId2=cursor.getString(0);//problemId on local
                           final int updateDate2=cursor.getInt(7);//updateDate on local
                           final int updateTime2=cursor.getInt(8);//UpdateTime on local

                               if((updateDate>updateDate2) || ((updateDate==updateDate2) && (updateTime>updateTime2)) )
                               myDatabaseHelper.UpdateFromOnline(ProblemId2,Title,Problem,Solution,Tag,Setter,sync_status,updateDate,updateTime,lastUpdateDate,lastUpdateTime);

                        }
                       else myDatabaseHelper.insertData(Title,Problem,Solution,Tag,Setter,sync_status,updateDate,updateTime,lastUpdateDate,lastUpdateTime);



                    }
                } catch(Exception e){
                    e.printStackTrace();
                    return "No connection is available";
                }

                return "updated Local Database";

            }
//retrive data from json object end

        return "Nothind Done yet";
    }

    @Override
    protected synchronized void onPostExecute(String result) {
        if(result.equals("Data Saved to server"))
        Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        else if(result.equals("updated Local Database")){
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
            alertDialog.setMessage(result);
            //alertDialog.show();
        }
        else {
            alertDialog.setMessage(result);
           // alertDialog.show();
           // problem.setText(result);
        }
    }


}
