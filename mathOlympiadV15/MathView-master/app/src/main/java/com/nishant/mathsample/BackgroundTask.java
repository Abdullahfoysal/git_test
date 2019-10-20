package com.nishant.mathsample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import static android.support.v4.content.ContextCompat.startActivity;
import static com.nishant.mathsample.initActivity.getDatabase;
import static com.nishant.mathsample.initActivity.getDatabaseHelper;

public class BackgroundTask extends AsyncTask<String,Void,String> {
    TextView problem;
    AlertDialog alertDialog;

    Context ctx;
    MyDatabaseHelper myDatabaseHelper;

    Cursor cursor=null;



    BackgroundTask(Context ctx){
        this.ctx=ctx;

        myDatabaseHelper=getDatabaseHelper();

    }

    @Override
    protected synchronized void onPreExecute() {
       // alertDialog=new AlertDialog.Builder(ctx).create();
        //alertDialog.setTitle("Alert Note");
    }

    @Override
    protected synchronized String doInBackground(String ... params) {
        String reg_url=DbContract.SERVER_URL2;
        String login_url=DbContract.LOGIN_DATA_FETCHING_URL;//userLogin.php
        String allDataFetchingUrl=DbContract.ALL_DATA_FETCHING_URL;
        String checkSignUp_URL=DbContract.CHECK_SIGNUP_DATA_FETCHING_URL;
        String userDataFetching_URL=DbContract.USER_DATA_FETCHING_URL;



        String method=params[0];

        if(method.equals("saveOnline")){//not using this 19/10/19 11:12 PM
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
        else if(method.equals("login")){

            //login only check from server
            String USERNAME=params[1];
            String PASSWORD=params[2];




            try {

                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(USERNAME,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(PASSWORD,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String response="";
                String line="";

                if((line=bufferedReader.readLine())!=null){
                    response=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //change activity after login
                if(response.equals("OK")){

                    DbContract.CURRENT_USER_NAME=USERNAME;

                    Intent intent = new Intent (ctx, homeActivity.class);
                    ctx.startActivity(intent);
                }
               // else DbContract.Alert(ctx,"Login information","Enter correct username & password");

                return response;

            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }


        }
        else if(method.equals("checkSignUp")){
            // create account validation
            String NAME=params[1];
            String USERNAME=params[2];
            String PASSWORD=params[3];
            String GENDER=params[4];
            String BIRTHDATE=params[5];
            String EMAIL=params[6];
            String PHONENUMBER=params[7];
            String INSTITUTION=params[8];


            try {

                URL url=new URL(checkSignUp_URL);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(USERNAME,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String response="";
                String line="";

                if((line=bufferedReader.readLine())!=null){
                    response=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                if(response.equals("notMatched")){

                    long rowId = myDatabaseHelper.insertData(NAME, USERNAME, PASSWORD, GENDER, BIRTHDATE, EMAIL, PHONENUMBER, INSTITUTION, "0", "0", DbContract.SYNC_STATUS_FAILED);

                    DbContract.CURRENT_USER_NAME=USERNAME;

                    Intent intent = new Intent (ctx, homeActivity.class);
                    ctx.startActivity(intent);
                }




                return  response;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
        else if(method.equals("saveFromServer")){

            cursor=myDatabaseHelper.showAllData("problemAndSolution");
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

            //retrive data from json object end
        }
        else if(method.equals("userDataFetching")){
            //retrive data from json object begin


            cursor =myDatabaseHelper.query("userInformation",DbContract.CURRENT_USER_NAME);

            String currentUserName=DbContract.CURRENT_USER_NAME;

            String result=null;
            InputStream is=null;

            try {
               /* URL url = new URL(userDataFetching_URL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("GET");
                is = new BufferedInputStream(con.getInputStream());
*/
                //new
                URL url = new URL(userDataFetching_URL);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(currentUserName,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                is = new BufferedInputStream(httpURLConnection.getInputStream());
                //new

            }catch(Exception e){
                e.printStackTrace();
            }
            //read is content into a string
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=br.readLine())!=null){

                    if(line.equals("notFound")){
                        return "Not Matched";
                    }

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

                    jo=ja.getJSONObject(i);

                    final String NAME=jo.getString("name");
                    final String USERNAME=jo.getString("userName");//Title
                    final String PASSWORD=jo.getString("password");//problemStatement
                    final String GENDER=jo.getString("gender");//solution
                    final String DATEBIRTH=jo.getString("dateBirth");//Tags
                    final String EMAIL=jo.getString("email");// problem setter
                    final String PHONE=jo.getString("phone");
                    final String INSTITUTION=jo.getString("institution");
                    final String SOLVINGSTRING=jo.getString("solvingString");
                    final String TOTALSOLVED=jo.getString("totalSolved");
                    int syncstatus=DbContract.SYNC_STATUS_OK;

                    if (cursor.moveToNext())
                    myDatabaseHelper.UpdateFromOnline(NAME,USERNAME,PASSWORD,GENDER,DATEBIRTH,EMAIL,PHONE,INSTITUTION,SOLVINGSTRING,TOTALSOLVED,syncstatus);
                    else {
                        long rowId=myDatabaseHelper.insertData(NAME,USERNAME,PASSWORD,GENDER,DATEBIRTH,EMAIL,PHONE,INSTITUTION,SOLVINGSTRING,TOTALSOLVED,syncstatus);

                        if(rowId==-1)return "notSaved userInformation";
                    }




                }
            } catch(Exception e){
                e.printStackTrace();
                return "No connection is available";
            }

            return "updated userInformation on Local";

            //retrive data from json object end

        }



        return "Nothind Done yet";
    }

    @Override
    protected synchronized void onPostExecute(String result) {
        if(result.equals("Data Saved to server"))
        Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        else if(result.equals("updated Local Database") ||result.equals("Local Data up to Date") ){
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
            //alertDialog.setMessage(result);
            //alertDialog.show();
        }
        else if(result.equals("matched") || result.equals("notMatched")){//signUp

            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
            if(result.equals("matched")){
                DbContract.Alert(ctx,"SignUp Information","Enter an Unique username");
            }

        }
        else  if(result.equals("OK") || result.equals("FAILED")){//login
            if(result.equals("FAILED")){
                DbContract.Alert(ctx,"Login Information","Enter correct username & password");
            }
        }
        else {
           /* alertDialog.setMessage(result);
            alertDialog.show();*/
           Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();

        }
    }




}
