package com.nishant.mathsample;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class problemActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private MyDatabaseHelper databaseHelper;
    private Cursor cursor;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private problemActivity context;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        //load nav_menu
        context=this;
        loadNavMenu();

        listView = this.<ListView>findViewById(R.id.problemActivityListViewId);
        //Data base work
        databaseHelper = new MyDatabaseHelper(this);

        loadData();
        listView.setOnItemClickListener(this);


    }
    public void loadData() {
        ArrayList<String> listData = new ArrayList<>();

         cursor = databaseHelper.showAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "NO data is available in database", Toast.LENGTH_LONG).show();

        } else {
            while (cursor.moveToNext()) {
                listData.add(cursor.getString(0)+". "+cursor.getString(1));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.problem_title_list_view, R.id.problemTitleTextListViewId, listData);
        listView.setAdapter(adapter);



    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Object obj= getListView().getItemAtPosition(i);




       StringTokenizer st=new StringTokenizer(obj.toString(),".");
        String problemId="";

        problemId=st.nextToken();

        Toast.makeText(getApplicationContext(),problemId,Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,problemProfileActivity.class);
        intent.putExtra("problemId",problemId);

        startActivity(intent);

    }

    public ListView getListView() {

        return listView;
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
       // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    System.out.println("Asse");
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

}
