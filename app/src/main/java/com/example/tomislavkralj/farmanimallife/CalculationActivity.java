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
import com.example.tomislavkralj.dbSqlite.DbConverter;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import  static  butterknife.ButterKnife.findById;

public class CalculationActivity extends AppCompatActivity {

    private Pig piggy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        TextView pig_weight = (TextView) findViewById(R.id.pig_weight);
        EditText feed_kg = (EditText)  findViewById(R.id.feed_kg);
        TextView pig_new_weight = (TextView) findViewById(R.id.pig_NewWeight);
        Spinner pig_feed = (Spinner) findViewById(R.id.feed_name);
        TextView pig_mortality = (TextView) findViewById(R.id.pig_mortality);
        TextView pig_birth = (TextView) findViewById(R.id.pig_piglet_per_birth);
        TextView pig_time = (TextView) findViewById(R.id.pig_time);

        MyDbHelper myDb = new MyDbHelper(this);
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

        TextView pig_weight = (TextView) findViewById(R.id.pig_weight);
        EditText feed_kg = (EditText) findViewById(R.id.feed_kg);
        EditText pig_new_weight = (EditText) findViewById(R.id.pig_NewWeight);
        Spinner pig_feed = (Spinner) findViewById(R.id.feed_name);
        TextView pig_mortality = (TextView) findViewById(R.id.pig_mortality);
        TextView pig_birth = (TextView) findViewById(R.id.pig_piglet_per_birth);
        TextView pig_time = (TextView) findViewById(R.id.pig_time);

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
                double daysItTakes = 0;
                double extraFeedKg = 0;
                double feedCalories = feed.getFeedCalories();
                feedKG = feedKG * (feedCalories / 385);

                while (weightNow <= wantedWeight) {
                    if (weightNow < 20) {
                        if (feedKG < 1.3) {
                            weightNow += (feedKG / 1.3) * 0.60;
                            extraFeedKg -= 1.3 - feedKG;
                        } else {
                            weightNow += 0.60;
                            extraFeedKg -= 1.3 - feedKG;
                        }
                    } else if (weightNow >= 20 && weightNow < 50) {
                        if (feedKG < 2) {
                            weightNow += (feedKG / 2.0) * 0.67;
                            extraFeedKg -= 2 - feedKG;
                        } else {
                            weightNow += 0.67;
                            extraFeedKg -= 2 - feedKG;
                        }
                    } else if (weightNow >= 50 && weightNow < 110) {
                        if (feedKG < 2.5) {
                            weightNow += (feedKG / 2.5) * 0.81;
                            extraFeedKg -= 2.5 - feedKG;
                        } else {
                            weightNow += 0.81;
                            extraFeedKg -= 2.5 - feedKG;
                        }
                    } else if (weightNow >= 110 && weightNow < 140) {
                        if (feedKG < 3.3) {
                            weightNow += (feedKG / 3.3) * 0.76;
                            extraFeedKg -= 3.3 - feedKG;
                        } else {
                            weightNow += 0.76;
                            extraFeedKg -= 3.3 - feedKG;
                        }
                    } else {
                        if (feedKG < 4.5) {
                            weightNow += (feedKG / 4.5) * 0.69;
                            extraFeedKg -= 4.5 - feedKG;
                        } else {
                            weightNow += 0.69;
                            extraFeedKg -= 4.5 - feedKG;
                        }
                    }
                    daysItTakes++;
                }
                if (extraFeedKg < 0) {
                    extraFeedKg *= -1;
                    pig_time.setText("Pig needs " + Integer.toString((int) Math.round(daysItTakes)) + " days and you have to give " + Integer.toString((int) Math.round(extraFeedKg)) + "kg of extra feed");
                } else {
                    pig_time.setText("Pig needs " + Integer.toString((int) Math.round(daysItTakes)) + " days and you have given " + Integer.toString((int) Math.round(extraFeedKg)) + "kg of extra feed");
                }
            }
        }
    }
}