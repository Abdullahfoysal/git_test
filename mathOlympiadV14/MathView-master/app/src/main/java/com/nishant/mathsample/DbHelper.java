package com.nishant.mathsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String CREATE_TABLE="create table "+DbContract.TABLE_NAME+"(id integer primary key autoincrement,"+
            DbContract.NAME+" text,"+DbContract.SYNC_STATUS+" integer);";
    private static final String DROP_TABLE="drop table if exists "+DbContract.TABLE_NAME;

    public DbHelper(Context context){
        super(context,DbContract.DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long saveToLocalDatabase(String name,int sync_status,SQLiteDatabase database){

        ContentValues contentValues=new ContentValues();

        contentValues.put(DbContract.NAME,name);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);

       long id= database.insert(DbContract.TABLE_NAME,null,contentValues);

       return id;


    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){
        String[] projection={DbContract.NAME,DbContract.SYNC_STATUS};

        return (database.query(DbContract.TABLE_NAME,projection,null,null,null,null,null));

    }

    public void updateLocalDatabase(String name,int sync_status,SQLiteDatabase database){



        ContentValues contentValues=new ContentValues();
        contentValues.put(DbContract.SYNC_STATUS,sync_status);

        String selection =DbContract.NAME+" LIKE ?";
        String[] selection_args={name};
       database.update(DbContract.TABLE_NAME,contentValues,selection,selection_args);
       // System.out.println("update " +k);


    }
}
