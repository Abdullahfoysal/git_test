package com.nishant.mathsample;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper myDatabaseHelper;
    private TextInputEditText name,userName,password,gender,birthDate,email,phonNumber,institution;
    private Button signUp;
    private TextView loginLink;
    private String NAME,USERNAME,PASSWORD,GENDER,BIRTHDATE,EMAIL,PHONENUMBER,INSTITUTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadAll();
        myDatabaseHelper=new MyDatabaseHelper(this);

    }

    private  void loadAll(){

        findAllField();

    }
    private void findAllField(){

        name= this.<TextInputEditText>findViewById(R.id.input_name);
        userName= this.<TextInputEditText>findViewById(R.id.user_name);
        password= this.<TextInputEditText>findViewById(R.id.passwordId);
        gender= this.<TextInputEditText>findViewById(R.id.selectGenderId);
        birthDate= this.<TextInputEditText>findViewById(R.id.selectBirthDateId);
        email= this.<TextInputEditText>findViewById(R.id.your_email_address);
        phonNumber= this.<TextInputEditText>findViewById(R.id.phoneNumber);
        institution= this.<TextInputEditText>findViewById(R.id.selectInstitutionId);

        signUp= this.<Button>findViewById(R.id.signUpButtonId);
        loginLink=findViewById(R.id.link_login);

        signUp.setOnClickListener(this);
        loginLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getApplicationContext(),"DATA SAVED TO LOCAL",Toast.LENGTH_SHORT).show();
        long id=view.getId();
        if(id==R.id.signUpButtonId){
            getAllFieldText();

                if(!NAME.isEmpty() && !USERNAME.isEmpty() && !PASSWORD.isEmpty() && !GENDER.isEmpty() && !BIRTHDATE.isEmpty() && !EMAIL.isEmpty() && !PHONENUMBER.isEmpty() && !INSTITUTION.isEmpty() ) {

                    if (checkNetworkConnection()) {

                        String method = "checkSignUp";
                        BackgroundTask backgroundTask = new BackgroundTask(this);
                        backgroundTask.execute(method,NAME, USERNAME, PASSWORD, GENDER, BIRTHDATE, EMAIL, PHONENUMBER, INSTITUTION);

                        }

                   else DbContract.Alert(this, "Network Connectivity info:", "Connect to Internet");
                   }

                else DbContract.Alert(this,"Fill up Error","Enter All information correctly");

        }
        else if(id==R.id.link_login){
            startActivity(new Intent(this,loginActivity.class));
        }

    }
    private void getAllFieldText(){

        NAME=name.getText().toString();
        USERNAME=userName.getText().toString();
        PASSWORD=password.getText().toString();
        GENDER=gender.getText().toString();
        BIRTHDATE=birthDate.getText().toString();
        EMAIL=email.getText().toString();
        PHONENUMBER=phonNumber.getText().toString();
        INSTITUTION=institution.getText().toString();

    }
    public  boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }
}
