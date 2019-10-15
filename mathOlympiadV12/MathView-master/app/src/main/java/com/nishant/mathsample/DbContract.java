package com.nishant.mathsample;

public class DbContract {

    public static final int SYNC_STATUS_OK=0;
    public static final int SYNC_STATUS_FAILED=1;
    public static final String SERVER_URL="http://192.168.0.105/syncdemo/sync.php";//no need on 15/10/19 9:54Pm
    public static final String SERVER_URL2="http://192.168.0.102/syncdemo/dataSync.php";//15/10/19->9:16 pm from phone to mysql
    public static final String SERVER_URL3="http://192.168.0.117/syncdemo/dataFetch.php";//for single information such login
    public static final String SERVER_URL4="http://192.168.0.102/syncdemo/allDataFetching.php";//15/10/19->8:05 pm
    public static final String UI_UPDATE_BROADCAST="com.nishant.mathsample.uiupdatebroadcast";
    public static final String DATABASE_NAME="contactdb";
    public static final String DATABASE_NAME2="SRMC.db";
    public static final String TABLE_NAME="contactinfo";
    public static final String TABLE_NAME2="problemAndSolution";//work with
    public static final String NAME="name";
    public static final String PROBLEM_ID="problemId";
    public static final String SYNC_STATUS="syncstatus";




}
