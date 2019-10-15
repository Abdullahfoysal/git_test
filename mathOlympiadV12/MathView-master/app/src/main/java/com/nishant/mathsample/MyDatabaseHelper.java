package com.nishant.mathsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="SRMC.db";
    private static final String PROBLEM_AND_SOLUTION_TABLE="problemAndSolution";

    private static final String PROBLEM_ID="problemId";
    private static final String TITLE="title";
    private static final String PROBLEM_STATEMENT="problem";
    private static final String SOLUTION="solution";
    private static final String TAG="tag";
    public static final String SETTER="setter";
    public static final String SYNC_STATUS="syncstatus";
    public static final String UPDATE_DATE="updateDate";
    public static final String UPDATE_TIME="updateTime";
    public static final String LAST_UPDATE_DATE="lastUpdateDate";
    public static final String LAST_UPDATE_TIME="lastUpdateTime";
    private static final int VERSION_NUMBER=1;
    private static final String CREATE_PROBLEM_AND_SOLUTION_TABLE="CREATE TABLE "+PROBLEM_AND_SOLUTION_TABLE+"( "+PROBLEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(100), "+PROBLEM_STATEMENT+" VARCHAR(500), "+
            SOLUTION+" VARCHAR(50), "+TAG+" VARCHAR(100),"+SETTER+" VARCHAR(25),"+SYNC_STATUS+" INTEGER,"+UPDATE_DATE+" INTEGER, "+UPDATE_TIME+" INTEGER,"+LAST_UPDATE_DATE+" INTEGER,"+LAST_UPDATE_TIME+" INTEGER ); ";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+PROBLEM_AND_SOLUTION_TABLE;
    private static final String SELECT_ALL_FROM_PROBLEM_AND_SOLUTION_TABLE="SELECT * FROM "+PROBLEM_AND_SOLUTION_TABLE;

    //user table begin
    private static final String USER_INFORMATION_TABLE="userInformation";
    private static final String NAME="name";
    private static final String USER_NAME="userName";
    private static final String PASSWORD="password";
    private static final String GENDER="gender";
    private static final String DATE_BIRTH="dateBirth";
    private static final String EMAIL="email";
    private static final String PHONE_NUMBER="phone";
    private static final String INSTITUTION="institution";
    private static final String SOLVING_STRING="solvingString";
    private static final String TOTAL_SOLVED="totalSolved";
    private static final String CREATE_USER_INFORMATION_TABLE="CREATE TABLE "+USER_INFORMATION_TABLE+"( "+NAME+" VARCHAR(25), "+USER_NAME+" VARCHAR(25), "+PASSWORD+" VARCHAR(25), "+GENDER+" VARCHAR(25), "+DATE_BIRTH+" VARCHAR(25), "+EMAIL+" VARCHAR(25), "+PHONE_NUMBER+" VARCHAR(25), "+INSTITUTION+" VARCHAR(50), "+SOLVING_STRING+" TEXT, "+TOTAL_SOLVED+" VARCHAR(25) );";
    private static final String SELECT_ALL_FROM_USER_INFORMATION_TABLE="SELECT * FROM "+USER_INFORMATION_TABLE;








    private Context context;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Toast.makeText(context,"onCreate is created", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(CREATE_PROBLEM_AND_SOLUTION_TABLE);
            sqLiteDatabase.execSQL(CREATE_USER_INFORMATION_TABLE);

        }catch (Exception e){
            Toast.makeText(context,"Exception: "+e, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            Toast.makeText(context,"onUpgrade is created", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);

        }catch (Exception e){
            Toast.makeText(context,"Exception: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public long insertData(String title,String problemStatement,String solution,String tag,String setter,int synStatus,int updateDate,int updateTime,int lastUpdateDate,int lastUpdateTime){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(PROBLEM_STATEMENT,problemStatement);
        contentValues.put(SOLUTION,solution);
        contentValues.put(TAG,tag);
        contentValues.put(SETTER,setter);
        contentValues.put(SYNC_STATUS,synStatus);
        contentValues.put(UPDATE_DATE,updateDate);
        contentValues.put(UPDATE_TIME,updateTime);
        contentValues.put(LAST_UPDATE_DATE,lastUpdateDate);
        contentValues.put(LAST_UPDATE_TIME,lastUpdateTime);

      long rowId= sqLiteDatabase.insert(PROBLEM_AND_SOLUTION_TABLE,null,contentValues);

        return  rowId;

    }
    public long insertData(String name,String userName,String password,String gender,String dateBirth,String email,String phone,String institution,String solvingString,String totalSolved){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(USER_NAME,userName);
        contentValues.put(PASSWORD,password);
        contentValues.put(GENDER,gender);
        contentValues.put(DATE_BIRTH,dateBirth);
        contentValues.put(EMAIL,email);
        contentValues.put(PHONE_NUMBER,phone);
        contentValues.put(INSTITUTION,institution);
        contentValues.put(SOLVING_STRING,solvingString);
        contentValues.put(TOTAL_SOLVED,totalSolved);

        long rowId= sqLiteDatabase.insert(USER_INFORMATION_TABLE,null,contentValues);
        return rowId;
    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_FROM_PROBLEM_AND_SOLUTION_TABLE,null);
        return  cursor;
    }

    public void updateLocalDatabase(String ProblemId,int sync_status,SQLiteDatabase database){



        ContentValues contentValues=new ContentValues();
        contentValues.put(DbContract.SYNC_STATUS,sync_status);

        String selection =DbContract.PROBLEM_ID+" LIKE ?";
        String[] selection_args={ProblemId};
        database.update(PROBLEM_AND_SOLUTION_TABLE,contentValues,selection,selection_args);
        // System.out.println("update " +k);


    }



    public long saveToLocalDatabase(int sync_status,SQLiteDatabase database){

        ContentValues contentValues=new ContentValues();


        contentValues.put(DbContract.SYNC_STATUS,sync_status);

        long id= database.insert(PROBLEM_AND_SOLUTION_TABLE,null,contentValues);

        return id;


    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){

        Cursor cursor = database.rawQuery(SELECT_ALL_FROM_PROBLEM_AND_SOLUTION_TABLE,null);

        return cursor;

    }

    public void UpdateFromOnline(String ProblemId,String title,String problemStatement,String solution,String tag,String setter,int syncstatus,int updateDate,int updateTime,int lastUpdateDate,int lastUpdateTime){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(PROBLEM_STATEMENT,problemStatement);
        contentValues.put(SOLUTION,solution);
        contentValues.put(TAG,tag);
        contentValues.put(SETTER,setter);
        contentValues.put(UPDATE_DATE,updateDate);
        contentValues.put(UPDATE_TIME,updateTime);

        contentValues.put(LAST_UPDATE_DATE,lastUpdateDate);
        contentValues.put(LAST_UPDATE_TIME,lastUpdateTime);
        contentValues.put(SYNC_STATUS,syncstatus);

        String selection =DbContract.PROBLEM_ID+" LIKE ?";
        String[] selection_args={ProblemId};

        sqLiteDatabase.update(PROBLEM_AND_SOLUTION_TABLE,contentValues,selection,selection_args);


    }



}
