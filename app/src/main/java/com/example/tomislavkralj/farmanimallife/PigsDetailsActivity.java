package com.example.tomislavkralj.farmanimallife;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class PigsDetailsActivity extends AppCompatActivity {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
    private Pig piggy;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pigs_details);
        Intent intent = getIntent();
        Pig pig= null;
        if(intent.getExtras().getParcelable("OBJEKT") == (null)){

        }else{
           pig = intent.getExtras().getParcelable("OBJEKT");
        }

        TextView pig_id = (TextView) findViewById(R.id.pig_id);
        TextView pig_weight = (TextView) findViewById(R.id.pig_weight);
        TextView pig_pregnant = (TextView) findViewById(R.id.pig_pregnant);
        TextView pig_dateOfBirth = (TextView) findViewById(R.id.pig_dateOfBirth);
        TextView pig_feed_name = (TextView) findViewById(R.id.pig_feed_name);
        ImageView pig_gender = (ImageView) findViewById(R.id.gender);
        ImageButton pig_edit = (ImageButton) findViewById(R.id.EditImageButton);

        TextView pig_Father = (TextView) findViewById(R.id.pig_Father_ID);
        TextView pig_Mother = (TextView) findViewById(R.id.pig_Mother_ID);
        TextView pig_numBirths = (TextView) findViewById(R.id.pig_numberOfBirths);
        TextView pig_perMort = (TextView) findViewById(R.id.percentageOfMortality);
        TextView pig_numOfChilPerBirth = (TextView) findViewById(R.id.numChildrenPerBirth);

        ConstraintLayout glavniLay = (ConstraintLayout) findViewById(R.id.glavni);



        if(pig.isGender()){
            pig_gender.setImageResource(R.drawable.female_sign_pink);
            glavniLay.setBackgroundColor(Color.parseColor("#ffb6c1"));
            pig_edit.setImageResource(R.drawable.edit_write_icon_pink);
        }else{
            pig_gender.setImageResource(R.drawable.male_sign_blue);
            glavniLay.setBackgroundColor(Color.parseColor("#87CEFA"));
            pig_edit.setImageResource(R.drawable.edit_write_icon_blue);
        }
        pig_id.setText("ID: "+Integer.toString(pig.getId()));
        pig_weight.setText("Weight: "+Integer.toString(pig.getWeight())+"kg");
        pig_feed_name.setText("Feed: " +pig.getFeed());
        if(pig instanceof Sow ) {
            Sow sow = (Sow) pig;
            if(sow.isPregnant() == 0) {
                pig_pregnant.setText("Pregnant: No");
            }else {
                pig_pregnant.setText("Pregnant: Yes");
            }
            pig_numBirths.setText("Number of births: "+Integer.toString(sow.getNumberOfBirths()));
            pig_perMort.setText("Mortality rate: "+Double.toString(Math.round(sow.getPrecentOfMortality()*100.0))+"%");
            pig_numOfChilPerBirth.setText("Piglets/Birth: "+Double.toString(sow.getNumOfchildrenPerBirth()));
        }else{
            Hog hog = (Hog) pig;
            pig_pregnant.setVisibility(View.INVISIBLE);
            pig_numBirths.setText("Percent of succesful pregnancys: "+Double.toString(Math.round(hog.getPercentageOfSuccPerpregnancys()))+"%");
            pig_perMort.setText("Mortality rate: "+Double.toString(Math.round(hog.getPercentageOfMortality()*100.0))+"%");
            pig_numOfChilPerBirth.setText("Piglets/Pregnancy: "+Integer.toString(hog.getNumOfChildrenPerPregnancy()));

        }
        Date date = pig.getDateOfBirth();

        if(date.equals(null) || date.equals("")){
            pig_dateOfBirth.setText("Date of birth: " );
        }else{
            pig_dateOfBirth.setText("Date of birth: " + sdf.format(pig.getDateOfBirth()));
        }
        pig_Father.setText("Father ID: "+Integer.toString(pig.getIdFather()));
        pig_Mother.setText("Mother ID: "+Integer.toString(pig.getIdMother()));

        piggy = pig;
    }

    public void editPig(View view) throws ParseException {
        Intent intent = new Intent(this, EditPigActivity.class);
        MyDbHelper myDb = new MyDbHelper(this);

        RelativeLayout parent = (RelativeLayout) view.getParent();
        TextView id = (TextView) parent.getChildAt(0);
        String str = id.getText().toString();
        str = str.substring(4);
        int i = Integer.parseInt(str);
        Cursor cr = myDb.getPig(i);
        cr.moveToFirst();
        Pig pig = DbConverter.cursotToPig(cr);

        if(pig.isGender()){
            intent.putExtra("OBJEKT", (Sow)pig);
        }else{
            intent.putExtra("OBJEKT", (Hog)pig);
        }
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
