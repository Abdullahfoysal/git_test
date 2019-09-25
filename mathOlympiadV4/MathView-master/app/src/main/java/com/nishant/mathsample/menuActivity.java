package com.nishant.mathsample;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nishant.math.MathView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class menuActivity extends AppCompatActivity implements View.OnClickListener{

    MyDatabaseHelper myDatabaseHelper;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private menuActivity context;
    private Toolbar toolbar;

    private EditText problemStatement,problemSolution;
    private Button saveButton,loadButton;
    //private writeRead WriteRead=new writeRead();
   @BindView(R.id.math_view)
   MathView mathView;

    @BindView(R.id.input_view)
    EditText inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        //load nav_menu
        context=this;
        loadNavMenu();

        //testing

        //init Database
        myDatabaseHelper=new MyDatabaseHelper(this);
        try {

            SQLiteDatabase sqLiteDatabase =myDatabaseHelper.getWritableDatabase();

        }catch (Exception e){

        }


        problemStatement = this.<EditText>findViewById(R.id.input_view);
        problemSolution = this.<EditText>findViewById(R.id.addproblemSolutionEditTextId);
        saveButton= this.<Button>findViewById(R.id.saveButtonId);
        loadButton= this.<Button>findViewById(R.id.loadButtonId);

        Toast.makeText(this,"menu",Toast.LENGTH_SHORT).show();

        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);


        mathView.setText("$$(a+b)^2$$");

        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            ///data supply here to show on
            @Override
            public void afterTextChanged(Editable s) {
                mathView.setText(inputView.getText().toString());
            }
        });


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveButtonId) {

            String problem_statement = problemStatement.getText().toString();
            String solution = problemSolution.getText().toString();

            if (! problem_statement.isEmpty() && !solution.isEmpty()) {
                ///Data insert to Database

                long rowId=myDatabaseHelper.insertData("Title/Short Description",problem_statement,solution,"tag");

                if(rowId==-1)Toast.makeText(getApplicationContext(), "unsuccessfull", Toast.LENGTH_SHORT).show();
                else {
                    problemStatement.setText("");
                    problemSolution.setText("");
                    Toast.makeText(getApplicationContext(), "row id: "+rowId+" is inserted", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Add statement and solution", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId()==R.id.loadButtonId){

            startActivity(new Intent(getApplicationContext(),problemActivity.class));
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
}


