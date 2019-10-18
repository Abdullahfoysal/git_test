package com.nishant.mathsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkMonitorUpdateProblem extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(checkNetworkConnection(context)) {

            String method="saveFromServer";
            BackgroundTask backgroundTask=new BackgroundTask(context);
            backgroundTask.execute(method);


        }
//retrive data from json object end


    }

    public boolean checkNetworkConnection(Context context){

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }
}
