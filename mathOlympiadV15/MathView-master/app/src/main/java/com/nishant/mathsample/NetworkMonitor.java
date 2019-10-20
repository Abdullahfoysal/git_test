package com.nishant.mathsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.nishant.mathsample.initActivity.getDatabase;
import static com.nishant.mathsample.initActivity.getDatabaseHelper;

public class NetworkMonitor extends BroadcastReceiver {

 @Override
    public void onReceive(final Context context, Intent intent) {

        if(checkNetworkConnection(context)){

           // Toast.makeText(context,"updating on network class",Toast.LENGTH_SHORT).show();

            final MyDatabaseHelper myDatabaseHelper=getDatabaseHelper();
           // final SQLiteDatabase sqLiteDatabase =getDatabase();

            Cursor cursor=myDatabaseHelper.showAllData("userInformation");

            while (cursor.moveToNext()){

                //USER INFORMATION UPDATE TO ONLINE
                final String NAME = cursor.getString(0);
                final String USERNAME = cursor.getString(1);
                final String PASSWORD= cursor.getString(2);
                final String GENDER = cursor.getString(3);
                final String BIRTHDATE= cursor.getString(4);
                final String EMAIL = cursor.getString(5);
                final String PHONE = cursor.getString(6);
                final String INSTITUTION = cursor.getString(7);
                final String SOLVINGSTRING = cursor.getString(8);
                final String TOTALSOLVED = cursor.getString(9);
                final int sync_status = cursor.getInt(10);

                if(sync_status==DbContract.SYNC_STATUS_FAILED){
                   // final String Name=cursor.getString(cursor.getColumnIndex(DbContract.NAME));

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, DbContract.USERDATASYNC_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject=new JSONObject(response);
                                        String Response=jsonObject.getString("response");
                                        if(Response.equals("OK")){

                                            myDatabaseHelper.updateLocalDatabase(DbContract.SYNC_STATUS_OK,USERNAME);
                                            context.sendBroadcast(new Intent(DbContract.UI_UPDATE_BROADCAST));
                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }



                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();

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
                    MySingleton.getInstance(context).addToRequestQue(stringRequest);
                }
            }
            cursor.close();

        }


    }

    public boolean checkNetworkConnection(Context context){

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }

}
