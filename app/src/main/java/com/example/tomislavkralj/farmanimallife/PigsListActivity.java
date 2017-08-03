package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.tomislavkralj.adapters.PigAdapter;
import com.example.tomislavkralj.adapters.PigAdapterRecyclerView;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class PigsListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton addNewBttn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyView);
        addNewBttn = (ImageButton) findViewById(R.id.addNewBttn);

        MyDbHelper myDb = new MyDbHelper(this);
        List<Pig> allPigs = new ArrayList<>();
        try {
            allPigs = myDb.getAllPigs();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final List<String> input = new ArrayList<>();
        mAdapter = new PigAdapterRecyclerView(allPigs, this);
        recyclerView.setAdapter(mAdapter);

        addNewBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewPigActivity.class);
                startActivity(intent);
            }
        });
    }
}