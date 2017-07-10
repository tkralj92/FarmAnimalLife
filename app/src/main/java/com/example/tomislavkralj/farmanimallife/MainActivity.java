package com.example.tomislavkralj.farmanimallife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tomislavkralj.feed.Feed;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showPigsList(View view){
        Intent intent = new Intent(this, PigsListActivity.class);
        startActivity(intent);

        MyDbHelper my = new MyDbHelper(this);
        my.deleteAllFeed();
        my.insertFeed(new Feed("corn yellow",364));
        my.insertFeed(new Feed("barley raw",352));
        my.insertFeed(new Feed("soy boiled",141));
        my.insertFeed(new Feed("sunchoke",385));

    }
}
