package com.nishant.mathsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkMonitor extends BroadcastReceiver {

 @Override
    public void onReceive(final Context context, Intent intent) {
        if(checkNetworkConnection(context)){

            final MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(context);
            final SQLiteDatabase database =myDatabaseHelper.getWritableDatabase();

            Cursor cursor=myDatabaseHelper.readFromLocalDatabase(database);

            while (cursor.moveToNext()){

                final String ProblemId = cursor.getString(0);//problemId
                final String Title = cursor.getString(1);//Title
                final String Problem = cursor.getString(2);//problemStatement
                final String Solution = cursor.getString(3);//solution
                final String Tag = cursor.getString(4);//Tags
                final String Setter = cursor.getString(5);// problem setter
                final int sync_status = cursor.getInt(6);
                final String updateDate = cursor.getString(7);//
                final String updateTime = cursor.getString(8);//
                final String lastUpdateDate = cursor.getString(9);//
                final String lastUpdateTime = cursor.getString(10);//

                if(sync_status==DbContract.SYNC_STATUS_FAILED){
                   // final String Name=cursor.getString(cursor.getColumnIndex(DbContract.NAME));

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, DbContract.SERVER_URL2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject=new JSONObject(response);
                                        String Response=jsonObject.getString("response");
                                        if(Response.equals("OK")){

                                            myDatabaseHelper.updateLocalDatabase(ProblemId,DbContract.SYNC_STATUS_OK,database);
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
                            params.put("title", Title);
                            params.put("problem", Problem);
                            params.put("solution", Solution);
                            params.put("tag", Tag);
                            params.put("setter", Setter);
                            params.put("updateDate", updateDate);
                            params.put("updateTime", updateTime);
                            params.put("lastUpdateDate", lastUpdateDate);
                            params.put("lastUpdateTime", lastUpdateTime);
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
