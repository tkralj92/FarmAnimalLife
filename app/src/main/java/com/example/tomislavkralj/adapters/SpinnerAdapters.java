package com.example.tomislavkralj.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomislav.kralj on 10.7.2017..
 */

public class SpinnerAdapters {

    public static ArrayAdapter<Integer> getAllMothersSpinnerAdapter(Pig pig, Context context){
        MyDbHelper myDb = new MyDbHelper(context);
        List<Integer> motherArray = new ArrayList<>();
        motherArray.addAll(myDb.getAllMothers(pig));
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                context, android.R.layout.simple_spinner_item, motherArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<Integer> getAllFathersSpinnerAdapter(Pig pig, Context context){
        MyDbHelper myDb = new MyDbHelper(context);
        List<Integer> fatherArray = new ArrayList<>();
        fatherArray.addAll(myDb.getAllFathers(pig));
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                context, android.R.layout.simple_spinner_item, fatherArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public static ArrayAdapter<String> getAllFeedSpinnerAdapter(Context context){
        MyDbHelper myDb = new MyDbHelper(context);
        List<String> feedList =  new ArrayList<String>();
        try {
            feedList.addAll(myDb.getAllFeedNames());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, feedList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
