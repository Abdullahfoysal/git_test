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
    private static final String TABLE_NAME="problemAndSolutionTable";
    private static final String PROBLEM_ID="problemId";
    private static final String TITLE="title";
    private static final String PROBLEM_STATEMENT="problem";
    private static final String SOLUTION="solution";
    private static final String TAG="tag";
    private static final int VERSION_NUMBER=2;
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"( "+PROBLEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(100), "+PROBLEM_STATEMENT+" VARCHAR(500), "+SOLUTION+" VARCHAR(50), "+TAG+" VARCHAR(100) ); ";
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

    public long insertData(String title,String problemStatement,String solution,String tag){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(PROBLEM_STATEMENT,problemStatement);
        contentValues.put(SOLUTION,solution);
        contentValues.put(TAG,tag);

      long rowId= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return  rowId;

    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return  cursor;
    }

}
