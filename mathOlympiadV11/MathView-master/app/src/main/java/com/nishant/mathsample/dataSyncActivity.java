package com.nishant.mathsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dataSyncActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;

    RecyclerView recyclerView;
    EditText Name;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Contact> arrayList=new ArrayList<>();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sync);





        recyclerView = this.<RecyclerView>findViewById(R.id.recyclerViewId);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter =new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);


        saveToAppServer();
        readFromLocalStorage();

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                 //saveToAppServer();
                readFromLocalStorage();

            }
        };
    }


    private void readFromLocalStorage(){

        arrayList.clear();


        //DbHelper dbHelper=new DbHelper(this);
        //SQLiteDatabase database=dbHelper.getReadableDatabase();
//        Cursor cursor=dbHelper.readFromLocalDatabase(database);
           myDatabaseHelper=new MyDatabaseHelper(this);
           SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();
           Cursor cursor=myDatabaseHelper.readFromLocalDatabase(database);

           while (cursor.moveToNext()){
            //String name=cursor.getString(cursor.getColumnIndex(DbContract.NAME));
               String ProblemId=cursor.getString(0);//problemId
               String Title=cursor.getString(1);//Title
               String Problem=cursor.getString(2);//problemStatement
               String Solution=cursor.getString(3);//solution
               String Tag=cursor.getString(4);//Tags
               String Setter=cursor.getString(5);// problem setter
               int sync_status=cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));

               String save=ProblemId+". "+Title;



               arrayList.add(new Contact(save,sync_status));

        }

        adapter.notifyDataSetChanged();
        cursor.close();
        myDatabaseHelper.close();



    }
    //worked above done 11:24 PM

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }
    private void saveToAppServer(){



        if(checkNetworkConnection()) {

            Toast.makeText(getApplicationContext(),"network is on",Toast.LENGTH_SHORT).show();

            myDatabaseHelper = new MyDatabaseHelper(this);
            final SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
            Cursor cursor = myDatabaseHelper.readFromLocalDatabase(database);

            while (cursor.moveToNext()) {
                //String name=cursor.getString(cursor.getColumnIndex(DbContract.NAME));
                final String ProblemId = cursor.getString(0);//problemId
                final String Title = cursor.getString(1);//Title
                final String Problem = cursor.getString(2);//problemStatement
                final String Solution = cursor.getString(3);//solution
               final String Tag = cursor.getString(4);//Tags
                final String Setter = cursor.getString(5);// problem setter
                final int sync_status = cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));
                if(sync_status==DbContract.SYNC_STATUS_FAILED){


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL2,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String Response = jsonObject.getString("response");

                                        if (Response.equals("OK")) {
                                            Toast.makeText(getApplicationContext(), "saved on server", Toast.LENGTH_SHORT).show();

                                            myDatabaseHelper.updateLocalDatabase(ProblemId,DbContract.SYNC_STATUS_OK,database);


                                        } else {
                                            Toast.makeText(getApplicationContext(), "Not saved on server", Toast.LENGTH_SHORT).show();

                                            //saveToLocalStorage(name,DbContract.SYNC_STATUS_FAILED);
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
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


                            params.put("title", Title);
                            params.put("problem", Problem);
                            params.put("solution", Solution);
                            params.put("tag", Tag);
                            params.put("setter", Setter);
                            // params.put("syncstatus", Integer.toString(sync_status));

                            return params;
                        }
                    }
                            ;

                    MySingleton.getInstance(dataSyncActivity.this).addToRequestQue(stringRequest);

                }




            }
            cursor.close();

        }




    }

    private void saveToLocalStorage(int sync){

//        DbHelper dbHelper=new DbHelper(this);
//        SQLiteDatabase database=dbHelper.getWritableDatabase();
        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase database=myDatabaseHelper.getWritableDatabase();

        //save to sqlite database
        long id= myDatabaseHelper.saveToLocalDatabase(sync,database);

        if(id==-1) Toast.makeText(this,"recoded unsuccessful",Toast.LENGTH_SHORT).show();
        else  Toast.makeText(this,"recoded successful",Toast.LENGTH_SHORT).show();

        readFromLocalStorage();
        myDatabaseHelper.close();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver,new IntentFilter(DbContract.UI_UPDATE_BROADCAST));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
