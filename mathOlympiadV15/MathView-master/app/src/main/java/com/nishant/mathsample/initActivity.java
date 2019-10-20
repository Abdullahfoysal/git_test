package com.nishant.mathsample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class initActivity extends AppCompatActivity implements View.OnClickListener {
    private Button initLoginButton;
    public static MyDatabaseHelper myDatabaseHelper;
    public static SQLiteDatabase sqLiteDatabase;
    private initActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        context=this;

        loadAll();
    }
    private void loadAll(){
        initDatabase();
        findAllButton();
        saveAllToServer();


    }
    private void saveAllToServer(){

        // DbContract.saveToAppServer(this);//userInformation for signUp information update to server
       // DbContract.saveFromServer(context);//problemAndSolution to local



    }
    private void initDatabase(){

            //database init 1

            myDatabaseHelper=new MyDatabaseHelper(this);
            try {

                 sqLiteDatabase =myDatabaseHelper.getWritableDatabase();

            }catch (Exception e){

            }

    }
    private void findAllButton(){

        initLoginButton= this.<Button>findViewById(R.id.initLoginButtonId);
        initLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        long id=view.getId();
        if(id==R.id.initLoginButtonId){
            startActivity(new Intent(this,loginActivity.class));
        }

    }
    public static SQLiteDatabase getDatabase(){

        return sqLiteDatabase;
    }
    public static MyDatabaseHelper getDatabaseHelper(){

        return  myDatabaseHelper;
    }

}
