package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomislavkralj.animals.Feed;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.calculator.PigCalculator;
import com.example.tomislavkralj.dbSqlite.DbConverter;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import  static  butterknife.ButterKnife.findById;

public class CalculationActivity extends AppCompatActivity {

    private Pig piggy;
    private MyDbHelper myDb = new MyDbHelper(this);

    @BindView(R.id.pig_weight) TextView pig_weight;
    @BindView(R.id.feed_kg)  EditText feed_kg;
    @BindView(R.id.pig_NewWeight) TextView pig_new_weight;
    @BindView(R.id.feed_name) Spinner pig_feed;
    @BindView(R.id.pig_mortality) TextView pig_mortality;
    @BindView(R.id.pig_piglet_per_birth) TextView pig_birth;
    @BindView(R.id.pig_time) TextView pig_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        ButterKnife.bind(this);


        List<String> allFeed = new ArrayList<String>();

        Intent intent = getIntent();
        Pig pig= null;
        if(intent.getExtras().getParcelable("OBJEKT").equals(null)){
        }else{
            pig = intent.getExtras().getParcelable("OBJEKT");
        }
        piggy = pig;

        pig_weight.setText("Pig weight is: " + Integer.toString(piggy.getWeight()));
        try {
            allFeed = myDb.getAllFeedNames();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, allFeed);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pig_feed.setAdapter(adapter);
    }

    public void Calculate(View view) {

        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        if(feed_kg.getText().toString().equals("") || pig_new_weight.getText().toString().equals("")) {
            Toast msg = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
            msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            msg.show();
        }else {
            String feedName = pig_feed.getSelectedItem().toString();

            MyDbHelper myDb = new MyDbHelper(this);
            Feed feed = new Feed();
            try {
                feed = myDb.getFeedByName(feedName);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int wantedWeight = Integer.parseInt(pig_new_weight.getText().toString());
            double feedKG = Double.parseDouble(feed_kg.getText().toString());

            if(feedKG < 0 ){
                Toast msg = Toast.makeText(this, "Feed amount must be a positive number!", Toast.LENGTH_SHORT);
                msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                msg.show();
            }else if(wantedWeight < piggy.getWeight()){
                Toast msg = Toast.makeText(this, "Wanted weight must be higher than actual weight!", Toast.LENGTH_SHORT);
                msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                msg.show();
            }else {
                Cursor crS = myDb.getPig(piggy.getIdMother());
                Cursor crH = myDb.getPig(piggy.getIdFather());
                Sow sow = new Sow();
                Hog hog = new Hog();

                if (crS.moveToFirst()) {
                    try {
                        sow = (Sow) DbConverter.cursotToPig(crS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    sow.setPrecentOfMortality(0);
                    sow.setNumOfchildrenPerBirth(0);
                }

                if (crH.moveToFirst()) {
                    try {
                        hog = (Hog) DbConverter.cursotToPig(crH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    hog.setPercentageOfMortality(0);
                    hog.setNumOfChildrenPerPregnancy(0);
                }

                double mortality;
                double pigsPerBirth;
                if (piggy.isGender()) {
                    Sow piggyS = (Sow) piggy;
                    mortality = 0.5 * hog.getPercentageOfMortality() + 0.5 * sow.getPrecentOfMortality()
                            + piggyS.getPrecentOfMortality();
                    mortality /= 2.0;
                    pigsPerBirth = 0.5 * hog.getNumOfChildrenPerPregnancy() + 0.5 * sow.getNumOfchildrenPerBirth()
                            + piggyS.getNumOfchildrenPerBirth();
                    pigsPerBirth /= 2.0;
                } else {
                    Hog piggyH = (Hog) piggy;
                    mortality = 0.5 * hog.getPercentageOfMortality() + 0.5 * sow.getPrecentOfMortality()
                            + piggyH.getPercentageOfMortality();
                    mortality /= 2.0;
                    pigsPerBirth = 0.5 * hog.getNumOfChildrenPerPregnancy() + 0.5 * sow.getNumOfchildrenPerBirth()
                            + piggyH.getNumOfChildrenPerPregnancy();
                    pigsPerBirth /= 2.0;
                }

                pig_mortality.setText("Piglet mortality: " + Double.toString(mortality*100) + "%");
                pig_birth.setText("Piglet per birth: " + Double.toString(pigsPerBirth));

                double pigWeight = piggy.getWeight();
                double weightNow = pigWeight;
                double feedCalories = feed.getFeedCalories();
                feedKG = feedKG * (feedCalories / 385);

                PigCalculator pigCalc = new PigCalculator();
                String answer = pigCalc.calculateDays(wantedWeight, weightNow,feedKG);

                pig_time.setText(answer);
            }
        }
    }
}