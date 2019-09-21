package com.nishant.mathsample;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nishant.math.MathView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;







public class problemProfileActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.math_view)
    MathView mathView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private problemProfileActivity context;

    private MyDatabaseHelper databaseHelper;
    private Cursor cursor;
    private TextView title;
    private Button submitButton;
    private EditText submittedSolutionEditText;
    private String problemId="1";
    private Bundle bundle;
    private String KEY="problemId";
    private String realSolution="#!?@";
    private String submittedSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_profile);
        ButterKnife.bind(this);
        mathView.setText("$$a^2$$");
        //load nav_menu
        context=this;
        loadNavMenu();


        //Database
        databaseHelper = new MyDatabaseHelper(this);

        findId();
        receiveData();
        loadData();
        submitButton.setOnClickListener(this);

    }

    public void loadData() {


        cursor = databaseHelper.showAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "NO data is available in database", Toast.LENGTH_LONG).show();

        } else {

            while (cursor.moveToNext()) {
//                listData.add(cursor.getString(0)+". "+cursor.getString(2));
                if(cursor.getString(0).equals(problemId)){
                    realSolution=cursor.getString(3);
                    title.setText(cursor.getString(1));
                    mathView.setText(cursor.getString(2));
                    break;

                }

            }
        }




    }

    void findId(){
        title = this.<TextView>findViewById(R.id.problemTitleId);
        submitButton=findViewById(R.id.solutionSubmitButtonId);
        submittedSolutionEditText=findViewById(R.id.submittedSolutionEditTextId);

    }
    void receiveData(){
        bundle = getIntent().getExtras();
         problemId=bundle.getString(KEY);

         Toast.makeText(this,problemId,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.solutionSubmitButtonId){
            submittedSolution= submittedSolutionEditText.getText().toString();

            if(realSolution.equals(submittedSolution)){
                Toast.makeText(this,"Accepted",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Wrong",Toast.LENGTH_LONG).show();
            }
            submittedSolutionEditText.setText("");
            submittedSolutionEditText.setHint("Enter Answer Again");

        }

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
