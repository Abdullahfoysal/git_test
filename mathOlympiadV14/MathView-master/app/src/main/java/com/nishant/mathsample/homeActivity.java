package com.nishant.mathsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper myDatabaseHelper;
    //nav menu begin
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private homeActivity context;
    private Toolbar toolbar;
    //nav menu end


    //button
    private Button showProblemButton,updateProblemButton,addProblemButton,signUpButton,syncButton;

    private BroadcastReceiver broadcastReceiver,broadcastReceiverUpdateProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=this;

        loadAll();//nav,button,database



    }


    private void loadAll(){
        findAllButton();
        loadNavMenu();
        initDatabase();
        checkingNetworkAndThread();

    }







    private void findAllButton(){
        showProblemButton= this.<Button>findViewById(R.id.showButtonId);
        updateProblemButton= this.<Button>findViewById(R.id.updateButtonId);
        addProblemButton= this.<Button>findViewById(R.id.addProblemButtonId);
        signUpButton= this.<Button>findViewById(R.id.signUpButtonId);
        syncButton=findViewById(R.id.uploadUserInformationId);


        showProblemButton.setOnClickListener(this);
        updateProblemButton.setOnClickListener(this);
        addProblemButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        syncButton.setOnClickListener(this);
    }

    private void initDatabase(){
        //database init 1

        myDatabaseHelper=new MyDatabaseHelper(this);
        try {

            SQLiteDatabase sqLiteDatabase =myDatabaseHelper.getWritableDatabase();

        }catch (Exception e){

        }
        //database init 1 end

    }



    private void checkingNetworkAndThread(){

        //problemAndSolution saveFromServer thread


            new Thread(){
                @Override
                public void run() {
                    try {

                        DbContract.saveFromServer(context,myDatabaseHelper);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            }.start();




        //userData saveToServer thread
        new Thread(){
            @Override
            public void run() {
                try {

                    DbContract.saveToAppServer(context,myDatabaseHelper);


                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }.start();


    //NetWorkMonitoring method saveToServer
        broadcastReceiver=new BroadcastReceiver() {
            //userData saveToServer thread
            @Override
            public void onReceive( Context context, Intent intent) {

            }
        };
        broadcastReceiverUpdateProblem=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };




    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("broadcastReceiver onStart");
        registerReceiver(broadcastReceiver,new IntentFilter(DbContract.UI_UPDATE_BROADCAST));
        registerReceiver(broadcastReceiverUpdateProblem,new IntentFilter(DbContract.UI_UPDATE_BROADCAST));


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverUpdateProblem);
    }


    @Override
    public void onClick(View view) {
        long id=view.getId();
        if(id==R.id.showButtonId){

            startActivity(new Intent(this,problemActivity.class));

        }
        else if(id==R.id.updateButtonId){

            String method="saveFromServer";
            BackgroundTask backgroundTask=new BackgroundTask(this);
            backgroundTask.execute(method);

        }
        else if(id==R.id.addProblemButtonId){
            startActivity(new Intent(this,menuActivity.class));

        }
        else if(id==R.id.signUpButtonId){

            startActivity(new Intent(this,signUpActivity.class));
        }
        else if(id==R.id.uploadUserInformationId){

            startActivity(new Intent(this,dataSyncActivity.class));

        }
    }

    //load nav menu begin
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();
        }
    }
    public void loadNavMenu(){

        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout =findViewById(R.id.drawer_layout);
        mtoggle =new ActionBarDrawerToggle(context,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.solveProblemId){
                    Toast.makeText(context,"solved problems",Toast.LENGTH_SHORT).show();

                }
                if(id==R.id.attemptedProblems){
                    Toast.makeText(context,"attempted problems",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.notificationSettingId){
                    Toast.makeText(context,"Notification",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.updateInformationId){
                    Toast.makeText(context,"update information",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
    //nav menu function end

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }
    public synchronized void saveFromServer(){

        if(checkNetworkConnection()) {
            System.out.println("aisse from server");
            //retrive data from json object begin
          //  final SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
            Cursor cursor=myDatabaseHelper.showAllData();


            String result=null;
            InputStream is=null;

            try {
                URL url = new URL(DbContract.ALL_DATA_FETCHING_URL);
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
