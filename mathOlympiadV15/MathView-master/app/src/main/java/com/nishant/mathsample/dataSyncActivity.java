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

                readFromLocalStorage();

            }
        };
    }


    private void readFromLocalStorage(){

        arrayList.clear();


           myDatabaseHelper=new MyDatabaseHelper(this);
           SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();

           Cursor cursor=myDatabaseHelper.readFromLocalDatabase("userInformation");

           while (cursor.moveToNext()){

                String userName=cursor.getString(1);
               int sync_status=cursor.getInt(10);


               String save="SIGN UP of "+userName;



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
    public void saveToAppServer(){



        if(checkNetworkConnection()) {

            Toast.makeText(getApplicationContext(),"network is on",Toast.LENGTH_SHORT).show();

            myDatabaseHelper = new MyDatabaseHelper(this);
            final SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
            Cursor cursor = myDatabaseHelper.readFromLocalDatabase("userInformation");

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
                                            Toast.makeText(getApplicationContext(), "saved on server", Toast.LENGTH_SHORT).show();

                                            myDatabaseHelper.updateLocalDatabase(DbContract.SYNC_STATUS_OK,USERNAME);


                                        } else {
                                            Toast.makeText(getApplicationContext(), "Not saved on server", Toast.LENGTH_SHORT).show();

                                            //saveToLocalStorage(name,DbContract.SYNC_STATUS_FAILED);
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(),"Exception occur 404",Toast.LENGTH_SHORT).show();
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

                    MySingleton.getInstance(dataSyncActivity.this).addToRequestQue(stringRequest);

                }




            }
            cursor.close();

        }




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
