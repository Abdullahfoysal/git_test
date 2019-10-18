package com.nishant.mathsample;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper myDatabaseHelper;
    private TextInputEditText name,userName,password,gender,birthDate,email,phonNumber,institution;
    private Button signUp;
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

        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getApplicationContext(),"DATA SAVED TO LOCAL",Toast.LENGTH_SHORT).show();
        long id=view.getId();
        if(id==R.id.signUpButtonId){
            getAllFieldText();
            if(!NAME.isEmpty() && !USERNAME.isEmpty() && !PASSWORD.isEmpty() && !GENDER.isEmpty() && !BIRTHDATE.isEmpty() && !EMAIL.isEmpty() && !PHONENUMBER.isEmpty() && !INSTITUTION.isEmpty() ){

               long rowId= myDatabaseHelper.insertData(NAME,USERNAME,PASSWORD,GENDER,BIRTHDATE,EMAIL,PHONENUMBER,INSTITUTION,"0","0",DbContract.SYNC_STATUS_FAILED);
               if(rowId==-1)
               Toast.makeText(getApplicationContext(),"DATA Not SAVED TO LOCAL",Toast.LENGTH_SHORT).show();
               else  Toast.makeText(getApplicationContext(),"DATA SAVED TO LOCAL",Toast.LENGTH_SHORT).show();
            }
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
}
