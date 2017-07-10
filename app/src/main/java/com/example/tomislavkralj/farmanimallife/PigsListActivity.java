package com.example.tomislavkralj.farmanimallife;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.tomislavkralj.adapters.PigAdapter;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class PigsListActivity extends AppCompatActivity{

    private ListView sowListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pigs_list);
        sowListView = (ListView) findViewById(R.id.pigs_list_view);

        List<Pig> allPigs = new ArrayList<>();
        MyDbHelper myDbHelper = new MyDbHelper(this);
        try {
            allPigs.addAll(myDbHelper.getAllPigs());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PigAdapter adapter = new PigAdapter(this, (ArrayList<Pig>) allPigs);
        sowListView.setAdapter(adapter);
        myDbHelper.close();
    }

    public void addNewPig(View view){
        Intent intent = new Intent(this, AddNewPigActivity.class);
        startActivity(intent);
    }

    public void showPigDetails(View view) throws ParseException {
        Intent intent = new Intent(this, PigsDetailsActivity.class);
        MyDbHelper myDb = new MyDbHelper(this);
        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.getChildAt(0);
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
        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.getChildAt(0);
        String str = id.getText().toString();
        str = str.substring(4);
        final int i = Integer.parseInt(str);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete the pig with ID number: " + i);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.deletePig(i);
                myDb.close();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.close();
                startActivity(intent);
            }
        });
        builder.show();
    }
}
