package com.nishant.mathsample;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

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
    MyDatabaseHelper myDatabaseHelper;
    Cursor cursor;
    BackgroundTask(Context ctx, TextView problem){
        this.ctx=ctx;
        this.problem=problem;



    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("problems Description");
    }

    @Override
    protected String doInBackground(String ... params) {
        String reg_url=DbContract.SERVER_URL2;
        String login_url=DbContract.SERVER_URL3;
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
            String problemId=params[1];

            myDatabaseHelper = new MyDatabaseHelper(ctx);
            SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
            cursor = myDatabaseHelper.readFromLocalDatabase(database);


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
        return "Not found Data";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Data Saved to server"))
        Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        else{
            alertDialog.setMessage(result);
            alertDialog.show();
            problem.setText(result);
        }
    }
}
