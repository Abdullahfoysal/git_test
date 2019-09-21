package com.nishant.mathsample;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    /// only for nav menu begin
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void loadNavMenu(){
        mDrawerLayout= this.<DrawerLayout>findViewById(R.id.drawer_navId);
        mtoggle =new ActionBarDrawerToggle(context,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.home){
                    Toast.makeText(context,"This is home",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.setting){
                    Toast.makeText(context,"setting",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.log){
                    Toast.makeText(context,"logout",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }
    ///only for nav menu end
}
