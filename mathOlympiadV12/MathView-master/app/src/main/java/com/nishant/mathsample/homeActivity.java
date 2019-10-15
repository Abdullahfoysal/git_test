package com.nishant.mathsample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    //nav menu begin
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private homeActivity context;
    private Toolbar toolbar;
    //nav menu end


    //button
    private Button showProblemButton,updateProblemButton,addProblemButton;

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



    private void findAllButton(){
        showProblemButton= this.<Button>findViewById(R.id.showButtonId);
        updateProblemButton= this.<Button>findViewById(R.id.updateButtonId);
        addProblemButton= this.<Button>findViewById(R.id.addProblemButtonId);

        showProblemButton.setOnClickListener(this);
        updateProblemButton.setOnClickListener(this);
        addProblemButton.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        long id=view.getId();
        if(id==R.id.showButtonId){

            startActivity(new Intent(this,problemActivity.class));

        }
        else if(id==R.id.updateButtonId){

            String method="saveFromOnline";
            BackgroundTask backgroundTask=new BackgroundTask(this);
            backgroundTask.execute(method);

        }
        else if(id==R.id.addProblemButtonId){
            startActivity(new Intent(this,menuActivity.class));

        }
    }
}
