package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class EditPigActivity extends AppCompatActivity {

    private Pig piggy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pig);

        Intent intent = getIntent();
        DecimalFormat numForm = new DecimalFormat("#.00");
        Pig pig= intent.getExtras().getParcelable("OBJEKT");
        piggy = pig;
        MyDbHelper myDbFeed = new MyDbHelper(this);

        TextView pig_id = (TextView) findViewById(R.id.pig_id);
        ImageView pig_gender = (ImageView) findViewById(R.id.gender);
        EditText pig_wight = (EditText) findViewById(R.id.pig_wight);
        RadioGroup radio_grp = (RadioGroup) findViewById(R.id.radio_pregnant);
        RadioButton pig_pregnantY = (RadioButton) findViewById(R.id.radio_yes);
        RadioButton pig_pregnantN = (RadioButton) findViewById(R.id.radio_no);
        Spinner feed_spinner = (Spinner) findViewById(R.id.feed_spinner);
        DatePicker pig_dateOfBirth = (DatePicker) findViewById(R.id.pig_dateOfBirth);

        Spinner fatherSp = (Spinner) findViewById(R.id.spinnerFather);
        Spinner motherSp = (Spinner) findViewById(R.id.spinnerMother);

        EditText extra_oneE = (EditText) findViewById(R.id.extra_oneE);
        EditText extra_twoE = (EditText) findViewById(R.id.extra_twoE);
        EditText extra_threeE = (EditText) findViewById(R.id.extra_threeE);

        TextView extra_one = (TextView) findViewById(R.id.extra_one);
        TextView extra_two = (TextView) findViewById(R.id.extra_two);
        TextView extra_three = (TextView) findViewById(R.id.extra_three);

        ConstraintLayout glavniLay = (ConstraintLayout) findViewById(R.id.glavni);

//////POPUNJAVANJE VIEW-a
        pig_id.setText(Integer.toString(pig.getId()));
        if(pig.isGender()){
            pig_gender.setImageResource(R.drawable.female_sign_pink);
            glavniLay.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }else{
            pig_gender.setImageResource(R.drawable.male_sign_blue);
            glavniLay.setBackgroundColor(Color.parseColor("#87CEFA"));
        }
        pig_wight.setText(Integer.toString(pig.getWeight()));
///SPINNNNNERRRRRRRRR INICIJALIZACIJA
        int position;
        List<Integer> motherArray = new ArrayList<>();
        motherArray.addAll(myDbFeed.getAllMothers(piggy));
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, motherArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherSp.setAdapter(adapter2);
        position = adapter2.getPosition(pig.getIdMother());
        motherSp.setSelection(position);

        List<Integer> fatherArray = new ArrayList<>();
        fatherArray.addAll(myDbFeed.getAllFathers(piggy));
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, fatherArray);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatherSp.setAdapter(adapter3);
        position = adapter3.getPosition(pig.getIdFather());
        fatherSp.setSelection(position);

        List<String> spinnerArray =  new ArrayList<String>();
        try {
            spinnerArray.addAll(myDbFeed.getAllFeedNames());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feed_spinner.setAdapter(adapter);
        int spinnerLocation = adapter.getPosition(pig.getFeed());
        feed_spinner.setSelection(spinnerLocation);
///////////////////////////
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pig.getDateOfBirth());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        pig_dateOfBirth.updateDate(year,month,day);

/////musko zenski dio
        if(pig instanceof Hog) {
            Hog hog = (Hog) pig;
            radio_grp.setVisibility(View.INVISIBLE);
            extra_one.setText("Succesful\npregnancies (%) ");
            extra_oneE.setText(String.valueOf(numForm.format(hog.getPercentageOfSuccPerpregnancys())));
            if(hog.getPercentageOfSuccPerpregnancys()<1){
                extra_oneE.setText("0");
            }

            extra_two.setText("Mortality \nrate: (%)");
            extra_twoE.setText(String.valueOf(Math.round(hog.getPercentageOfMortality()*100.0)));

            extra_three.setText("Piglets/\nPregnancy: ");
            extra_threeE.setText(String.valueOf(hog.getNumOfChildrenPerPregnancy()));

        }else{
            Sow sow = (Sow) pig;
            radio_grp.setEnabled(true);
            if(sow.isPregnant()==0){
                pig_pregnantN.setChecked(true);
            }else{
                pig_pregnantY.setChecked(true);
            }

            extra_one.setText("Number\nof births");
            extra_oneE.setText(String.valueOf(sow.getNumberOfBirths()));

            extra_two.setText("Mortality \nrate: (%)");
            extra_twoE.setText(String.valueOf(Math.round(sow.getPrecentOfMortality()*100.0)));

            extra_three.setText("Piglets/\nBirth: ");
            extra_threeE.setText(String.valueOf(sow.getNumOfchildrenPerBirth()));

        }
    }

    public void savePigChanges(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Intent intent = new Intent(this, PigsListActivity.class);
        final MyDbHelper myDb = new MyDbHelper(this);
        final Context cntx = this;

        builder.setMessage("Do you want to save the changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText pig_wight = (EditText) findViewById(R.id.pig_wight);
                RadioGroup radio_grp = (RadioGroup) findViewById(R.id.radio_pregnant);
                RadioButton pig_pregnantY = (RadioButton) findViewById(R.id.radio_yes);
                RadioButton pig_pregnantN = (RadioButton) findViewById(R.id.radio_no);
                Spinner feed_spinner = (Spinner) findViewById(R.id.feed_spinner);
                Spinner fatherSp = (Spinner) findViewById(R.id.spinnerFather);
                Spinner motherSp = (Spinner) findViewById(R.id.spinnerMother);
                DatePicker pig_dateOfBirth = (DatePicker) findViewById(R.id.pig_dateOfBirth);

                EditText extra_oneE = (EditText) findViewById(R.id.extra_oneE);
                EditText extra_twoE = (EditText) findViewById(R.id.extra_twoE);
                EditText extra_threeE = (EditText) findViewById(R.id.extra_threeE);

                if(!extra_oneE.getText().toString().equals("") && !extra_twoE.getText().toString().equals("") && !extra_threeE.getText().toString().equals("")){

                    piggy.setWeight(Integer.parseInt(pig_wight.getText().toString()));
                    piggy.setFeed(feed_spinner.getSelectedItem().toString());
                    int year = pig_dateOfBirth.getYear();
                    int month = pig_dateOfBirth.getMonth();
                    int day = pig_dateOfBirth.getDayOfMonth();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,day);
                    piggy.setDateOfBirth(calendar.getTime());
                    piggy.setIdFather(Integer.parseInt(fatherSp.getSelectedItem().toString()));
                    piggy.setIdMother(Integer.parseInt(motherSp.getSelectedItem().toString()));
                    if(piggy instanceof Sow){
                        Sow sow = (Sow) piggy;
                        if(pig_pregnantY.isChecked()) {
                            sow.setPregnant(1);
                        }else{
                            sow.setPregnant(0);
                        }
                        sow.setNumberOfBirths(Integer.parseInt(extra_oneE.getText().toString()));
                        sow.setPrecentOfMortality(Double.parseDouble(extra_twoE.getText().toString())/100.0);
                        sow.setNumOfchildrenPerBirth(Double.parseDouble(extra_threeE.getText().toString()));
                        myDb.updatePig(sow);
                        myDb.close();
                        startActivity(intent);
                    }else{
                        Hog hog = (Hog) piggy;
                        String str = extra_oneE.getText().toString();
                        hog.setPercentageOfSuccPerpregnancys(Double.parseDouble(extra_oneE.getText().toString()));
                        hog.setPercentageOfMortality(Double.parseDouble(extra_twoE.getText().toString())/100.0);
                        str = extra_threeE.getText().toString();
                        hog.setNumOfChildrenPerPregnancy(Integer.parseInt(extra_threeE.getText().toString()));
                        myDb.updatePig(hog);
                        myDb.close();
                        startActivity(intent);
                    }
                }else{
                    Toast msg = Toast.makeText(cntx, "Fill all the fields", Toast.LENGTH_SHORT);
                    msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    msg.show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.close();
                startActivity(intent);
            }
        });
        builder.show();
    }
}