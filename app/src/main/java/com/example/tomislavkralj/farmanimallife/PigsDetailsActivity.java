package com.example.tomislavkralj.farmanimallife;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.DbConverter;

import com.example.tomislavkralj.dbSqlite.MyDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PigsDetailsActivity extends AppCompatActivity {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
    private Pig piggy;

    @BindView(R.id.pig_id) TextView pig_id;
    @BindView(R.id.gender) ImageView pig_gender;
    @BindView(R.id.pig_weight) TextView pig_weight;
    @BindView(R.id.pig_pregnant) TextView pig_pregnant;
    @BindView(R.id.pig_feed_name) TextView pig_feed_name;
    @BindView(R.id.pig_dateOfBirth) TextView pig_dateOfBirth;
    @BindView(R.id.pig_Father_ID) TextView pig_Father;
    @BindView(R.id.pig_Mother_ID) TextView pig_Mother;
    @BindView(R.id.EditImageButton) ImageButton pig_edit;
    @BindView(R.id.pig_numberOfBirths) TextView pig_numBirths;
    @BindView(R.id.percentageOfMortality) TextView pig_perMort;
    @BindView(R.id.numChildrenPerBirth) TextView pig_numOfChilPerBirth;
    @BindView(R.id.glavni) ConstraintLayout glavniLay;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pigs_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        piggy = intent.getExtras().getParcelable("OBJEKT");
        Resources res = getResources();

        if(piggy.isGender()){
            pig_gender.setImageResource(R.drawable.female_sign_pink);
            glavniLay.setBackgroundColor(Color.parseColor("#ffb6c1"));
            pig_edit.setImageResource(R.drawable.edit_write_icon_pink);
        }else{
            pig_gender.setImageResource(R.drawable.male_sign_blue);
            glavniLay.setBackgroundColor(Color.parseColor("#87CEFA"));
            pig_edit.setImageResource(R.drawable.edit_write_icon_blue);
        }
        pig_id.setText(res.getString(R.string.idAndID, piggy.getId()));
        pig_weight.setText(res.getString(R.string.weightAndKg, piggy.getWeight()));
        pig_feed_name.setText(res.getString(R.string.feedAndFeedName, piggy.getFeed()));

        if(piggy instanceof Sow ) {
            Sow sow = (Sow) piggy;
            if(sow.isPregnant() == 0) {
                pig_pregnant.setText(res.getString(R.string.pregNo));
            }else {
                pig_pregnant.setText(res.getString(R.string.pregYes));
            }
            pig_numBirths.setText("Number of births: "+Integer.toString(sow.getNumberOfBirths()));
            pig_perMort.setText("Mortality rate: "+Double.toString(Math.round(sow.getPrecentOfMortality()*100.0))+"%");
            pig_numOfChilPerBirth.setText("Piglets/Birth: "+Double.toString(sow.getNumOfchildrenPerBirth()));
        }else{
            Hog hog = (Hog) piggy;
            pig_pregnant.setVisibility(View.INVISIBLE);
            pig_numBirths.setText("Percent of succesful pregnancys: "+Double.toString(Math.round(hog.getPercentageOfSuccPerpregnancys()))+"%");
            pig_perMort.setText("Mortality rate: "+Double.toString(Math.round(hog.getPercentageOfMortality()*100.0))+"%");
            pig_numOfChilPerBirth.setText("Piglets/Pregnancy: "+Integer.toString(hog.getNumOfChildrenPerPregnancy()));

        }

        pig_dateOfBirth.setText("Date of birth: " + sdf.format(piggy.getDateOfBirth()));
        pig_Father.setText("Father ID: "+Integer.toString(piggy.getIdFather()));
        pig_Mother.setText("Mother ID: "+Integer.toString(piggy.getIdMother()));
    }

    public void editPig(View view) throws ParseException {
        Intent intent = new Intent(this, EditPigActivity.class);
        MyDbHelper myDb = new MyDbHelper(this);

        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.getChildAt(0);
        String str = id.getText().toString();
        str = str.substring(4);
        int i = Integer.parseInt(str);

        Pig pig = myDb.getPig(i);

        intent.putExtra("OBJEKT", (pig.isGender()) ? (Sow)pig : (Hog)pig);
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
