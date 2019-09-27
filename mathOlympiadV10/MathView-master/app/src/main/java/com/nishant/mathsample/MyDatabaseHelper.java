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
    private static final String TABLE_NAME="problemAndSolution";
    private static final String PROBLEM_ID="problemId";
    private static final String TITLE="title";
    private static final String PROBLEM_STATEMENT="problem";
    private static final String SOLUTION="solution";
    private static final String TAG="tag";
    public static final String SETTER="setter";
    public static final String SYNC_STATUS="syncstatus";
    private static final int VERSION_NUMBER=1;
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"( "+PROBLEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(100), "+PROBLEM_STATEMENT+" VARCHAR(500), "+SOLUTION+" VARCHAR(50), "+TAG+" VARCHAR(100),"+SETTER+" VARCHAR(25),"+SYNC_STATUS+" integer ); ";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_ALL="SELECT * FROM "+TABLE_NAME;



    private Context context;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Toast.makeText(context,"onCreate is created", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(CREATE_TABLE);

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

    public long insertData(String title,String problemStatement,String solution,String tag,String setter,int syncstatus){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(PROBLEM_STATEMENT,problemStatement);
        contentValues.put(SOLUTION,solution);
        contentValues.put(TAG,tag);
        contentValues.put(SETTER,setter);
        contentValues.put(SYNC_STATUS,syncstatus);

      long rowId= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return  rowId;

    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return  cursor;
    }

    public void updateLocalDatabase(String ProblemId,int sync_status,SQLiteDatabase database){



        ContentValues contentValues=new ContentValues();
        contentValues.put(DbContract.SYNC_STATUS,sync_status);

        String selection =DbContract.PROBLEM_ID+" LIKE ?";
        String[] selection_args={ProblemId};
        database.update(DbContract.TABLE_NAME2,contentValues,selection,selection_args);
        // System.out.println("update " +k);


    }



    public long saveToLocalDatabase(int sync_status,SQLiteDatabase database){

        ContentValues contentValues=new ContentValues();


        contentValues.put(DbContract.SYNC_STATUS,sync_status);

        long id= database.insert(DbContract.TABLE_NAME2,null,contentValues);

        return id;


    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){

        Cursor cursor = database.rawQuery(SELECT_ALL,null);

        return cursor;

    }

}
