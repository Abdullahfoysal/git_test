package com.nishant.mathsample;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DbContract {

    public static final int SYNC_STATUS_OK=0;
    public static final int SYNC_STATUS_FAILED=1;
    public static final String SERVER_URL="http://192.168.0.105/syncdemo/sync.php";//no need on 15/10/19 9:54Pm
    public static final String SERVER_URL2="http://192.168.0.102/syncdemo/dataSync.php";//15/10/19->9:16 pm from phone to mysql
    public static final String USERDATASYNC_URL="http://192.168.0.102/syncdemo/userDataSync.php";
    public static final String SERVER_URL3="http://192.168.0.117/syncdemo/dataFetch.php";//for single information such login
    public static final String SERVER_URL4="http://192.168.0.102/syncdemo/allDataFetching.php";//15/10/19->8:05 pm
    public static final String ALL_DATA_FETCHING_URL="http://192.168.0.102/syncdemo/allDataFetching.php";//15/10/19->8:05 pm
    public static final String UI_UPDATE_BROADCAST="com.nishant.mathsample.uiupdatebroadcast";
    public static final String DATABASE_NAME="contactdb";
    public static final String DATABASE_NAME2="SRMC.db";
    public static final String TABLE_NAME="contactinfo";
    public static final String TABLE_NAME2="problemAndSolution";//work with
    public static final String NAME="name";
    public static final String PROBLEM_ID="problemId";
    public static final String SYNC_STATUS="syncstatus";

    public  static boolean checkNetworkConnection(Context ctx){
        ConnectivityManager connectivityManager= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }
    public static synchronized void saveToAppServer(final Context ctx,final MyDatabaseHelper myDatabaseHelper){



        if(checkNetworkConnection(ctx)) {

          //  Toast.makeText(ctx,"Network is ON",Toast.LENGTH_SHORT).show();


            final SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
            Cursor cursor = myDatabaseHelper.readFromLocalDatabase("userInformation",database);

            while (cursor.moveToNext()) {
                //USER INFORMATION UPDATE TO ONLINE
                final String NAME = cursor.getString(0);//
                final String USERNAME = cursor.getString(1);//Title
                final String PASSWORD= cursor.getString(2);//problemStatement
                final String GENDER = cursor.getString(3);//solution
                final String BIRTHDATE= cursor.getString(4);//Tags
                final String EMAIL = cursor.getString(5);// problem setter
                final String PHONE = cursor.getString(6);//
                final String INSTITUTION = cursor.getString(7);//
                final String SOLVINGSTRING = cursor.getString(8);//
                final String TOTALSOLVED = cursor.getString(9);//
                final int sync_status = cursor.getInt(10);

                if(sync_status==DbContract.SYNC_STATUS_FAILED){


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.USERDATASYNC_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String Response = jsonObject.getString("response");

                                        if (Response.equals("OK")) {
                                            //Toast.makeText(ctx, USERNAME+" is saved on server", Toast.LENGTH_SHORT).show();

                                            myDatabaseHelper.updateLocalDatabase(DbContract.SYNC_STATUS_OK,USERNAME,database);


                                        } else {
                                           // Toast.makeText(ctx, "Not saved on server", Toast.LENGTH_SHORT).show();


                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                        //Toast.makeText(ctx,"Exception occur 404",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();


                            params.put("name", NAME);
                            params.put("userName",USERNAME);
                            params.put("password", PASSWORD);
                            params.put("gender", GENDER);
                            params.put("dateBirth", BIRTHDATE);
                            params.put("email", EMAIL);
                            params.put("phone", PHONE);
                            params.put("institution",INSTITUTION);
                            params.put("solvingString",SOLVINGSTRING);
                            params.put("totalSolved",TOTALSOLVED);

                            return params;
                        }
                    }
                            ;

                    MySingleton.getInstance(ctx).addToRequestQue(stringRequest);

                }




            }
            cursor.close();

        }




    }

    public static synchronized void saveFromServer(Context ctx,MyDatabaseHelper myDatabaseHelper){

        if(checkNetworkConnection(ctx)) {
            System.out.println("aisse from server");
            //retrive data from json object begin
            final SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
           Cursor cursor=myDatabaseHelper.readFromLocalDatabase("userInformation",database);


            String result=null;
            InputStream is=null;

            try {
                URL url = new URL(ALL_DATA_FETCHING_URL);
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
                            //return "Local Data up to Date";
                            return;
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
                //return "No connection is available";
            }

            // return "updated Local Database";


        }
//retrive data from json object end

        }





    }


