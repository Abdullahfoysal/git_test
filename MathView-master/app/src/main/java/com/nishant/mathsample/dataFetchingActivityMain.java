package com.nishant.mathsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class dataFetchingActivityMain extends AppCompatActivity {


    private EditText usernameEditText,passwordEditText;
    private Button fetchDataButton,saveDatatoServer;
    private TextView textView;
    String name="foysal",Title,Problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fetching_main);

        usernameEditText= this.<EditText>findViewById(R.id.userNameTextId);
        passwordEditText= this.<EditText>findViewById(R.id.passwordTextId);

        fetchDataButton= this.<Button>findViewById(R.id.fetchDataButtonId);
        textView=findViewById(R.id.fetchDatashowTextId);
    }

    public void saveToDatabase(View view){

        Title=usernameEditText.getText().toString();
        Problem=passwordEditText.getText().toString();
        String method="saveOnline";
        BackgroundTask backgroundTask=new BackgroundTask(this,textView);
        backgroundTask.execute(method,name,Title,Problem,"solution","tag","setter");

    }
    public void fetchdata(View view){

        Title=usernameEditText.getText().toString();
        Problem=passwordEditText.getText().toString();
        String method="saveLocal";
        BackgroundTask backgroundTask=new BackgroundTask(this,textView);
        backgroundTask.execute(method,Problem);

    }

}
