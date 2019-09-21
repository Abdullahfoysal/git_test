package com.nishant.mathsample;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class navigationMenu extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private navigationMenu context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_menu);

        context=this;
        loadNavMenu();
    }

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


}
