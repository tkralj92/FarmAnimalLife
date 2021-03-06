package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tomislavkralj.adapters.SpinnerAdapters;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.calculator.PigCalculator;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;
import com.example.tomislavkralj.toasts.CustomToast;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        setContentView(R.layout.calculator_layout);
        ButterKnife.bind(this);

        Resources res = getResources();
        Intent intent = getIntent();
        piggy = intent.getExtras().getParcelable("OBJEKT");
        ArrayAdapter<String> feedAdapter = SpinnerAdapters.getAllFeedSpinnerAdapter(this);

        pig_weight.setText(res.getString(R.string.weightIsNewLine, piggy.getWeight()));
        pig_feed.setAdapter(feedAdapter);
    }

    public void Calculate(View view) {

        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(feed_kg.getText().toString().equals("") || pig_new_weight.getText().toString().equals("")) {
            CustomToast.fillAllFields(this);
            return;
        }

        double pigWeight = piggy.getWeight();
        String feedName = pig_feed.getSelectedItem().toString();
        int wantedWeight = Integer.parseInt(pig_new_weight.getText().toString());
        double feedKG = Double.parseDouble(feed_kg.getText().toString());
        PigCalculator pigCalc = new PigCalculator();

        if(wantedWeight < piggy.getWeight()){
            CustomToast.higerWantedWeight(this);
            return;
        }

        String answer = pigCalc.calculateDaysUntillWantedWeight(getApplicationContext(), wantedWeight, pigWeight, feedKG, feedName);
        pig_mortality.setText(pigCalc.calculateMortalityRate(getApplicationContext(), piggy));
        pig_birth.setText(pigCalc.calculatePigletsPerBirth(getApplicationContext(),piggy));
        pig_time.setText(answer);
    }
}