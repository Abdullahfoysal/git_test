package com.nishant.mathsample;

import android.app.Activity;
import android.content.Context;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.nishant.math.MathView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class testingMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MyDatabaseHelper myDatabaseHelper;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private testingMain context;
    private Button button;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_main);



        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mDrawerLayout =findViewById(R.id.drawer_layout);
        mtoggle =new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.solveProblemId){
            Toast.makeText(this,"solved problems",Toast.LENGTH_SHORT).show();
            System.out.println("Asse");
        }
        if(id==R.id.attemptedProblems){
            Toast.makeText(this,"attempted problems",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.notificationSettingId){
            Toast.makeText(this,"Notification",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.updateInformationId){
            Toast.makeText(this,"update information",Toast.LENGTH_SHORT).show();
        }


        return true;
    }

}
