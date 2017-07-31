package com.example.tomislavkralj.farmanimallife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import com.example.tomislavkralj.adapters.PigAdapter;
import com.example.tomislavkralj.adapters.PigAdapterRecyclerView;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class PigsListActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String[] myDataset = {"Maam","tata","deda"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PigAdapterRecyclerView(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        /*
        /////OVO JE OBIČNI LISTVIEW

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pigs_list);

        ListView sowListView = (ListView) findViewById(R.id.pigs_list_view);
        MyDbHelper myDb = new MyDbHelper(this);
        PigAdapter adapter = null;

        try {
            adapter = new PigAdapter(this, (ArrayList<Pig>) myDb.getAllPigs());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sowListView.setAdapter(adapter);
        myDb.close();*/
    }

    public void addNewPig(View view){
        Intent intent = new Intent(this, AddNewPigActivity.class);
        startActivity(intent);
    }

    public void showPigDetails(View view) throws ParseException {

        Intent intent = new Intent(this, PigsDetailsActivity.class);
        MyDbHelper myDb = new MyDbHelper(this);

        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.findViewById(R.id.pig_id);
        String str = id.getText().toString();
        str = str.substring(4);
        int i = Integer.parseInt(str);

        Pig pig = myDb.getPig(i);

        if(pig.isGender()){
            intent.putExtra("OBJEKT", (Sow)pig);
        }else{
            Hog hog = (Hog) pig;
            intent.putExtra("OBJEKT", hog);
        }
        startActivity(intent);
        myDb.close();
    }

    public void deletePig(View view){

        final Intent intent = new Intent(this, PigsListActivity.class);
        final MyDbHelper myDb = new MyDbHelper(this);
        Resources res = getResources();

        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.findViewById(R.id.pig_id);
        String str = id.getText().toString();
        str = str.substring(4);
        final int i = Integer.parseInt(str);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(res.getString(R.string.alertDeletePig, i));
        builder.setPositiveButton(res.getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.deletePig(i);
                myDb.close();
                startActivity(intent);
            }
        });
        builder.setNegativeButton(res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.close();
                startActivity(intent);
            }
        });
        builder.show();
    }
}