package com.example.tomislavkralj.dbSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tomislavkralj.animals.Feed;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by tomislav.kralj on 2.6.2017..
 */

public class MyDbHelper extends SQLiteOpenHelper {

    protected static DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    private static final String Create_Table =
            "CREATE TABLE " + MyConstants.DATABASE_TABLE + " (" +
                    MyConstants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MyConstants.GENDER + " INTEGER, " +
                    MyConstants.WEIGHT + " INTEGER, " +
                    MyConstants.DATE + " TEXT, " +
                    MyConstants.FEED + " TEXT, " +
                    MyConstants.ALIVE + " INTEGER, " +
                    MyConstants.ID_MOTHER + " INTEGER, " +
                    MyConstants.ID_FATHER + " INTEGER, " +
                    MyConstants.PREGNANT + " INTEGER, " +
                    MyConstants.NUM_BIRTHS + " INTEGER, " +
                    MyConstants.PREC_MORT + " REAL, " +
                    MyConstants.CHILD_BIRTH + " REAL)";

    private static final String Create_Table_Feed =
            "CREATE TABLE " + MyConstants.DATABASE_TABLE_FEED + " (" +
                    MyConstants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MyConstants.FEED_NAME + " TEXT, " +
                    MyConstants.FEED_CALORIES + " INTEGER)";

    private static final String Delete_Table =
            "DROP TABLE IF EXISTS " + MyConstants.DATABASE_TABLE;

    public static final int DATABASE_VERSION =  MyConstants.DATABASE_VERSION;
    public static final String DATABASE_NAME = MyConstants.DATABASE_NAME;
    private static final String Delete_Table_FEED =
            "DROP TABLE IF EXISTS " + MyConstants.DATABASE_TABLE_FEED;


    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Feed);
        db.execSQL(Create_Table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL(Delete_Table);
        db.execSQL(Delete_Table_FEED);
        onCreate(db);
    }

    public boolean insertPig(Pig pig){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants.GENDER, pig.isGender() ? 1 : 0);
        contentValues.put(MyConstants.WEIGHT, pig.getWeight());
        Long dateL = pig.getDateOfBirth().getTime();
        contentValues.put(MyConstants.DATE, dateL.toString());
        contentValues.put(MyConstants.FEED, pig.getFeed());
        contentValues.put(MyConstants.ALIVE, pig.isAlive() ? 1 : 0);
        contentValues.put(MyConstants.ID_MOTHER,pig.getIdMother());
        contentValues.put(MyConstants.ID_FATHER,pig.getIdFather());
        if (pig instanceof Sow) {
            Sow sow = (Sow) pig;
            contentValues.put(MyConstants.PREGNANT, sow.isPregnant());
            contentValues.put(MyConstants.NUM_BIRTHS, sow.getNumberOfBirths());
            contentValues.put(MyConstants.PREC_MORT, sow.getPrecentOfMortality());
            contentValues.put(MyConstants.CHILD_BIRTH, sow.getNumOfchildrenPerBirth());
        }else{
            Hog hog = (Hog) pig;
            contentValues.put(MyConstants.PREGNANT, 0);
            contentValues.put(MyConstants.NUM_BIRTHS, hog.getNumOfChildrenPerPregnancy());
            contentValues.put(MyConstants.PREC_MORT, hog.getPercentageOfMortality());
            contentValues.put(MyConstants.CHILD_BIRTH, hog.getNumOfChildrenPerPregnancy());
        }
        db.insert(MyConstants.DATABASE_TABLE, null, contentValues);
        return true;
    }

    public Cursor getPig(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE + " WHERE " + MyConstants.KEY_ID + " = " + id, null);
        return  cursor;
    }

    public void deletePig(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MyConstants.DATABASE_TABLE, MyConstants.KEY_ID + " = " + id , null);
    }

    public List<Pig> getAllPigs() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pig> allPigs = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Pig pig = DbConverter.cursotToPig(cursor);
            allPigs.add(pig);
            if(cursor.isLast()){return allPigs;}
            cursor.moveToNext();
        }
        if(cursor.moveToFirst()){
            do{
                Pig pig = DbConverter.cursotToPig(cursor);
                allPigs.add(pig);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  allPigs;
    }

    public List<Integer> getAllMothers(Pig pig){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> allMothers = new ArrayList<>();
        //NEPOSTOJI MAJKA
        allMothers.add(0);
        Cursor cursor;
        if(pig == null){
            String query = "SELECT " + MyConstants.KEY_ID + " FROM " + MyConstants.DATABASE_TABLE + " WHERE " + MyConstants.GENDER + " = 1 ";
            cursor = db.rawQuery(query, null);
        }else{
            Long tempLng = (pig.getDateOfBirth().getTime());
            Long tmp = Long.parseLong("19008000000");
            tempLng -= tmp;
            String query = "SELECT " + MyConstants.KEY_ID + " FROM " + MyConstants.DATABASE_TABLE + " WHERE " + MyConstants.GENDER + " = 1 AND " + MyConstants.DATE + " < " + tempLng.toString();
            cursor = db.rawQuery(query, null);
        }

        cursor.moveToFirst();
        if(cursor.moveToFirst()){
            do{
                allMothers.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  allMothers;
    }

    public List<Integer> getAllFathers(Pig pig){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> allFathers = new ArrayList<>();
        //NEPOSTOJI otac
        allFathers.add(0);
        Cursor cursor;
        if(pig == null){
            String query = "SELECT " + MyConstants.KEY_ID + " FROM " + MyConstants.DATABASE_TABLE + " WHERE " + MyConstants.GENDER + " = 0";
            cursor = db.rawQuery(query, null);
        }else {
            Long tempLng = (pig.getDateOfBirth().getTime());
            Long tmp = Long.parseLong("19008000000");
            tempLng -= tmp;
            String query = "SELECT " + MyConstants.KEY_ID + " FROM " + MyConstants.DATABASE_TABLE + " WHERE " + MyConstants.GENDER + " = 0 AND " + MyConstants.DATE + " < " + tempLng.toString();
            cursor = db.rawQuery(query, null);
        }
        cursor.moveToFirst();

        if(cursor.moveToFirst()){
            do{
                allFathers.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  allFathers;
    }

    public boolean updatePig(Pig pig){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants.GENDER, pig.isGender() ? 1 : 0);
        contentValues.put(MyConstants.WEIGHT, pig.getWeight());
        Long dateL = pig.getDateOfBirth().getTime();
        contentValues.put(MyConstants.DATE, dateL.toString());
        contentValues.put(MyConstants.FEED, pig.getFeed());
        contentValues.put(MyConstants.ALIVE, pig.isAlive() ? 1 : 0);
        contentValues.put(MyConstants.ID_MOTHER,pig.getIdMother());
        contentValues.put(MyConstants.ID_FATHER,pig.getIdFather());
        if (pig instanceof Sow) {
            Sow sow = (Sow) pig;
            contentValues.put(MyConstants.PREGNANT, sow.isPregnant());
            contentValues.put(MyConstants.NUM_BIRTHS, sow.getNumberOfBirths());
            contentValues.put(MyConstants.PREC_MORT, sow.getPrecentOfMortality());
            contentValues.put(MyConstants.CHILD_BIRTH, sow.getNumOfchildrenPerBirth());
        }else{
            Hog hog = (Hog) pig;
            contentValues.put(MyConstants.PREGNANT, 0);
            contentValues.put(MyConstants.NUM_BIRTHS, hog.getNumOfChildrenPerPregnancy());
            contentValues.put(MyConstants.PREC_MORT, hog.getPercentageOfMortality());
            contentValues.put(MyConstants.CHILD_BIRTH, hog.getNumOfChildrenPerPregnancy());
        }

        db.update(MyConstants.DATABASE_TABLE,contentValues,MyConstants.KEY_ID + " = ?",
                    new String[]{Integer.toString(pig.getId())});

        return true;
    }

    public boolean insertFeed(Feed feed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants.FEED_NAME, feed.getFeedName());
        contentValues.put(MyConstants.FEED_CALORIES, feed.getFeedCalories());
        db.insert(MyConstants.DATABASE_TABLE_FEED, null, contentValues);
        return true;
    }

    public Cursor getFeed(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE_FEED + " WHERE " + MyConstants.KEY_ID + " = " + id, null);
        return  cursor;
    }

    public Feed getFeedByName(String name) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE_FEED + " WHERE " + MyConstants.FEED_NAME + " = '" + name +"'", null);
        cursor.moveToFirst();
        Feed feed_name = DbConverter.cursotToFeed(cursor);
        return  feed_name;
    }

    public void deleteFeed(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MyConstants.DATABASE_TABLE_FEED, MyConstants.KEY_ID + " = " + id , null);
    }

    public void deleteAllFeed(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MyConstants.DATABASE_TABLE_FEED, null, null);
    }

    public List<Feed> getAllFeed() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Feed> allFeed = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE_FEED, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Feed feed = DbConverter.cursotToFeed(cursor);
            allFeed.add(feed);
            if(cursor.isLast()){return allFeed;}
            cursor.moveToNext();
        }
        if(cursor.moveToFirst()){
            do{
                Feed feed = DbConverter.cursotToFeed(cursor);
                allFeed.add(feed);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return allFeed;
    }

    public List<String> getAllFeedNames() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> allFeed = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyConstants.DATABASE_TABLE_FEED, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Feed feed = DbConverter.cursotToFeed(cursor);
            allFeed.add(feed.getFeedName());
            if(cursor.isLast()){
                return allFeed;
            }
            cursor.moveToNext();
        }
        if(cursor.moveToFirst()){
            do{
                Feed feed = DbConverter.cursotToFeed(cursor);
                allFeed.add(feed.getFeedName());
            }while (cursor.moveToNext());
        }
        cursor.close();
        return allFeed;
    }

    public boolean updateFeed(Feed feed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.FEED_NAME, feed.getFeedName());
        contentValues.put(MyConstants.FEED_CALORIES, feed.getFeedCalories());

        db.update(MyConstants.DATABASE_TABLE_FEED, contentValues,MyConstants.KEY_ID + " = ?",
                new String[]{Integer.toString(feed.getId())});

        return true;
    }
}
