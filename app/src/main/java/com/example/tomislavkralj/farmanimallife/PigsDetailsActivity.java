package com.example.tomislavkralj.farmanimallife;

import android.content.Intent;
import android.content.res.Resources;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PigsDetailsActivity extends AppCompatActivity {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
    private Pig piggy;

    @BindView(R.id.pig_id) TextView pig_id;
    @BindView(R.id.gender) ImageView pig_gender;
    @BindView(R.id.pig_weight) TextView pig_weight;

    @BindView(R.id.pig_feed_name) TextView pig_feed_name;
    @BindView(R.id.pig_dateOfBirth) TextView pig_dateOfBirth;
    @BindView(R.id.pig_Father_ID) TextView pig_Father;
    @BindView(R.id.pig_Mother_ID) TextView pig_Mother;
    @BindView(R.id.EditImageButton) ImageButton pig_edit;
    @BindView(R.id.calculate_bttn) Button pig_calcuate;
    @BindView(R.id.pig_numberOfBirths) TextView pig_numBirths;
    @BindView(R.id.percentageOfMortality) TextView pig_perMort;
    @BindView(R.id.numChildrenPerBirth) TextView pig_numOfChilPerBirth;
    @BindView(R.id.glavniLay) ConstraintLayout glavniLay;

    @BindView(R.id.pig_weightV) TextView pig_weightVal;
    @BindView(R.id.pig_feed_nameV) TextView pig_feed_nameVal;
    @BindView(R.id.pig_dateOfBirthV) TextView pig_dateOfBirthVal;
    @BindView(R.id.pig_Father_IDV) TextView pig_FatherVal;
    @BindView(R.id.pig_Mother_IDV) TextView pig_MotherVal;
    @BindView(R.id.pig_numberOfBirthsV) TextView pig_numBirthsVal;
    @BindView(R.id.percentageOfMortalityV) TextView pig_perMortVal;
    @BindView(R.id.numChildrenPerBirthV) TextView pig_numOfChilPerBirthVal;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        piggy = intent.getExtras().getParcelable("OBJEKT");
        Resources res = getResources();

        if(piggy.isGender()){
            setContentView(R.layout.activity_female_details);
            ButterKnife.bind(this);

            TextView pig_pregnant = (TextView) findViewById(R.id.pig_pregnant);
            TextView pig_pregnantVal = (TextView) findViewById(R.id.pig_pregnantV);

            pig_gender.setImageResource(R.drawable.female_sign_pink);
            glavniLay.setBackgroundColor(ContextCompat.getColor(this, R.color.babyPink));
            pig_edit.setImageResource(R.drawable.edit_write_icon_pink);

            Sow sow = (Sow) piggy;
            pig_pregnant.setText(res.getString(R.string.pregnant));
            if(sow.isPregnant() == 0) {
                pig_pregnantVal.setText(res.getString(R.string.no));
            }else {
                pig_pregnant.setText(res.getString(R.string.yes));
            }

            pig_numBirths.setText(res.getString(R.string.numOfBirths));
            pig_numBirthsVal.setText(String.valueOf(sow.getNumberOfBirths()));

            pig_perMort.setText(res.getString(R.string.mortRate));
            pig_perMortVal.setText(String.valueOf(sow.getPrecentOfMortality()*100));

            pig_numOfChilPerBirth.setText(res.getString(R.string.pigPerBir));
            pig_numOfChilPerBirthVal.setText(String.valueOf(sow.getNumOfchildrenPerBirth()));
        }else{
            setContentView(R.layout.activity_male_details);
            ButterKnife.bind(this);

            pig_gender.setImageResource(R.drawable.male_sign_blue);
            glavniLay.setBackgroundColor(ContextCompat.getColor(this, R.color.babyBlue));
            pig_edit.setImageResource(R.drawable.edit_write_icon_blue);

            Hog hog = (Hog) piggy;
            pig_numBirths.setText(res.getString(R.string.preg));
            pig_numBirthsVal.setText(String.valueOf(hog.getPercentageOfSuccPerpregnancys()));

            pig_perMort.setText(res.getString(R.string.mortRate));
            pig_perMortVal.setText(String.valueOf(hog.getPercentageOfMortality()*100));

            pig_numOfChilPerBirth.setText(res.getString(R.string.pigPerBir));
            pig_numOfChilPerBirthVal.setText(String.valueOf(hog.getNumOfChildrenPerPregnancy()));
        }
        pig_id.setText(res.getString(R.string.idAndID, piggy.getId()));

        pig_weight.setText(res.getString(R.string.weight));
        pig_weightVal.setText(String.valueOf(piggy.getWeight()));

        pig_feed_name.setText(res.getString(R.string.feed));
        pig_feed_nameVal.setText(piggy.getFeed());

        pig_dateOfBirth.setText(res.getString(R.string.DateOfBirth));
        pig_dateOfBirthVal.setText(sdf.format(piggy.getDateOfBirth()));

        pig_Father.setText(res.getString(R.string.FatherID));
        pig_FatherVal.setText(String.valueOf(piggy.getIdFather()));

        pig_Mother.setText(res.getString(R.string.MotherID));
        pig_MotherVal.setText(String.valueOf(piggy.getIdMother()));

        pig_calcuate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                calculatePig(v);
            }
        });

        pig_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    editPig(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void editPig(View view) throws ParseException {
        Intent intent = new Intent(this, EditPigActivity.class);

        intent.putExtra("OBJEKT", (piggy.isGender()) ? (Sow)piggy : (Hog)piggy);

        startActivity(intent);
    }

    public void calculatePig(View view) {
        Intent intent = new Intent(this, CalculationActivity.class);
        if(piggy.isGender()){
            intent.putExtra("OBJEKT", (Sow)piggy);
        }else{
            Hog hog = (Hog) piggy;
            intent.putExtra("OBJEKT", hog);
        }
        startActivity(intent);
    }
}