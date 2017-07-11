package com.example.tomislavkralj.farmanimallife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import com.example.tomislavkralj.adapters.PigAdapter;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class PigsListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        myDb.close();
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

        int i = Integer.parseInt(str.substring(4));

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
        final int i = Integer.parseInt(str.substring(4));
        Resources res = getResources();

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
