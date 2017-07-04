package com.example.tomislavkralj.dbSqlite;

import android.database.Cursor;

import com.example.tomislavkralj.animals.Feed;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tomislav.kralj on 5.6.2017..
 */

public class DbConverter {

    protected static DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);


    public static Pig cursotToPig(Cursor cr) throws ParseException {

        int id = cr.getInt(cr.getColumnIndex(MyConstants.KEY_ID));
        int weight = cr.getInt(cr.getColumnIndex(MyConstants.WEIGHT));
        int gendertTemp = cr.getInt(cr.getColumnIndex(MyConstants.GENDER));
        boolean gender;
        if (gendertTemp == 0) {
            gender = false;
        } else {
            gender = true;
        }
        String dateTemp = cr.getString(cr.getColumnIndex(MyConstants.DATE));
        Date date;
        if (dateTemp.equals("") || dateTemp.equals(null)) {
            date = new Date();
        } else {
            Long dateL = Long.parseLong(dateTemp);
            date = new Date(dateL);
        }
        String feed = cr.getString(cr.getColumnIndex(MyConstants.FEED));
        int aliveTemp = cr.getInt(cr.getColumnIndex(MyConstants.ALIVE));
        boolean alive;
        if (aliveTemp == 0) {
            alive = false;
        } else {
            alive = true;
        }
        int idMother = cr.getInt(cr.getColumnIndex(MyConstants.ID_MOTHER));
        int idFather = cr.getInt(cr.getColumnIndex(MyConstants.ID_FATHER));
        int pregnant = cr.getInt(cr.getColumnIndex(MyConstants.PREGNANT));
        int numOfBirths = cr.getInt(cr.getColumnIndex(MyConstants.NUM_BIRTHS));
        double precOfMort = cr.getDouble(cr.getColumnIndex(MyConstants.PREC_MORT));
        double childPerBirth = cr.getDouble(cr.getColumnIndex(MyConstants.CHILD_BIRTH));

        if (gender) {
            Sow pig = new Sow(id, gender, weight, date, feed, alive, idMother, idFather, pregnant, numOfBirths, precOfMort, childPerBirth);
            return (Pig) pig;
        } else {
            Hog pig = new Hog(id, gender, weight, date, feed, alive, idMother, idFather, numOfBirths, (int) childPerBirth, precOfMort);
            return (Pig) pig;
        }
    }

    public static Feed cursotToFeed(Cursor cr) throws ParseException {

        int id = cr.getInt(cr.getColumnIndex(MyConstants.KEY_ID));
        String feedName = cr.getString(cr.getColumnIndex(MyConstants.FEED_NAME));
        int feedCalories = cr.getInt(cr.getColumnIndex(MyConstants.FEED_CALORIES));

        Feed feed = new Feed(id, feedName, feedCalories);

        return feed;
    }
}