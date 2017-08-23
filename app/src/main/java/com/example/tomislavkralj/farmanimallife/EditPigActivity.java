package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;
import com.example.tomislavkralj.adapters.SpinnerAdapters;
import com.example.tomislavkralj.toasts.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPigActivity extends AppCompatActivity {

    private Pig piggy;
    private RadioButton pig_pregnantY;

    @BindView(R.id.pig_id) TextView pig_id;
    @BindView(R.id.gender) ImageView pig_gender;
    @BindView(R.id.pig_weight)  EditText pig_weight;
    @BindView(R.id.feed_spinner) Spinner feed_spinner;
    @BindView(R.id.pig_dateOfBirth) DatePicker pig_dateOfBirth;
    @BindView(R.id.spinnerFather) Spinner fatherSp;
    @BindView(R.id.spinnerMother) Spinner motherSp;
    @BindView(R.id.extra_oneE) EditText extra_oneE;
    @BindView(R.id.extra_twoE) EditText extra_twoE;
    @BindView(R.id.extra_threeE) EditText extra_threeE;
    @BindView(R.id.extra_one) TextView extra_one;
    @BindView(R.id.extra_two) TextView extra_two;
    @BindView(R.id.extra_three) TextView extra_three;
    @BindView(R.id.glavni) ConstraintLayout glavniLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        piggy = intent.getExtras().getParcelable("OBJEKT");
        int position;
        ArrayAdapter<Integer> adapterMother = SpinnerAdapters.getAllMothersSpinnerAdapter(piggy, this);
        ArrayAdapter<Integer> adapterFather = SpinnerAdapters.getAllFathersSpinnerAdapter(piggy, this);
        ArrayAdapter<String> adapterFeed = SpinnerAdapters.getAllFeedSpinnerAdapter(this);
        Resources res = getResources();

        if(piggy.isGender()){
            setContentView(R.layout.activity_edit_female);
            pig_gender.setImageResource(R.drawable.female_sign_pink);
            glavniLay.setBackgroundColor(ContextCompat.getColor(this, R.color.babyPink));

            RadioGroup radio_grp = (RadioGroup) findViewById(R.id.radio_pregnant);
            pig_pregnantY = (RadioButton) findViewById(R.id.radio_yes);
            RadioButton pig_pregnantN = (RadioButton) findViewById(R.id.radio_no);

            Sow sow = (Sow) piggy;
            radio_grp.setEnabled(true);
            if(sow.isPregnant()==0){
                pig_pregnantN.setChecked(true);
            }else{
                pig_pregnantY.setChecked(true);
            }

            extra_one.setText(res.getString(R.string.numOfBirths));
            extra_oneE.setText(res.getString(R.string.numOfBirthsNum,sow.getNumberOfBirths()));

            extra_two.setText(res.getString(R.string.mortRate));
            extra_twoE.setText(res.getString(R.string.mortRateNum,sow.getPrecentOfMortality()*100.0));

            extra_three.setText(res.getString(R.string.pigPerBir));
            extra_threeE.setText(res.getString(R.string.pigPerBirNum, (sow.getNumOfchildrenPerBirth())));
        }else{
            setContentView(R.layout.activity_edit_male);
            pig_gender.setImageResource(R.drawable.male_sign_blue);
            glavniLay.setBackgroundColor(ContextCompat.getColor(this, R.color.babyBlue));

            Hog hog = (Hog) piggy;
            extra_one.setText(res.getString(R.string.succPreg));
            if(hog.getPercentageOfSuccPerpregnancys()<1){
                extra_oneE.setText("0");
            }else{
                extra_oneE.setText(res.getString(R.string.succPregNum));
            }

            extra_two.setText(res.getString(R.string.mortRate));
            extra_twoE.setText(res.getString(R.string.mortRateNum, hog.getPercentageOfMortality()*100.0));

            extra_three.setText(res.getString(R.string.pigPerBir));
            if(hog.getNumOfChildrenPerPregnancy()==0) {
                extra_threeE.setText(res.getString(R.string.pigPerBirNumInt,hog.getNumOfChildrenPerPregnancy()));
            }else {
                extra_threeE.setText("0");
            }
        }
        pig_id.setText(res.getString(R.string.idAndID, piggy.getId()));
        pig_weight.setText(res.getString(R.string.weightOnlyKg, piggy.getWeight()));

        motherSp.setAdapter(adapterMother);
        position = adapterMother.getPosition(piggy.getIdMother());
        motherSp.setSelection(position);

        fatherSp.setAdapter(adapterFather);
        position = adapterFather.getPosition(piggy.getIdFather());
        fatherSp.setSelection(position);

        feed_spinner.setAdapter(adapterFeed);
        int spinnerLocation = adapterFeed.getPosition(piggy.getFeed());
        feed_spinner.setSelection(spinnerLocation);

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(piggy.getDateOfBirth());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        pig_dateOfBirth.updateDate(year,month,day);*/
    }

    public void savePigChanges(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Intent intent = new Intent(this, PigsListActivity.class);
        final MyDbHelper myDb = new MyDbHelper(this);
        final Context context = this;
        Resources res = getResources();

        builder.setMessage(res.getString(R.string.alertDiaSaveCh));
        builder.setPositiveButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!extra_oneE.getText().toString().equals("") && !extra_twoE.getText().toString().equals("") && !extra_threeE.getText().toString().equals("")){

                    piggy.setWeight(Integer.parseInt(pig_weight.getText().toString()));
                    piggy.setFeed(feed_spinner.getSelectedItem().toString());
                    piggy.setDateOfBirthCalendar(pig_dateOfBirth.getYear(),pig_dateOfBirth.getMonth(),pig_dateOfBirth.getDayOfMonth());
                    piggy.setIdFather(Integer.parseInt(fatherSp.getSelectedItem().toString()));
                    piggy.setIdMother(Integer.parseInt(motherSp.getSelectedItem().toString()));

                    if(piggy instanceof Sow){
                        Sow sow = (Sow) piggy;
                        sow.setPregnant((pig_pregnantY.isChecked()) ? 1 : 0);
                        sow.setNumberOfBirths(Integer.parseInt(extra_oneE.getText().toString()));
                        sow.setPrecentOfMortality(Double.parseDouble(extra_twoE.getText().toString())/100.0);
                        sow.setNumOfchildrenPerBirth(Double.parseDouble(extra_threeE.getText().toString()));
                        myDb.updatePig(sow);
                        myDb.close();
                        startActivity(intent);
                    }else{
                        Hog hog = (Hog) piggy;
                        hog.setPercentageOfSuccPerpregnancys(Double.parseDouble(extra_oneE.getText().toString()));
                        hog.setPercentageOfMortality(Double.parseDouble(extra_twoE.getText().toString())/100.0);
                        hog.setNumOfChildrenPerPregnancy(Integer.parseInt(extra_threeE.getText().toString()));
                        myDb.updatePig(hog);
                        myDb.close();
                        startActivity(intent);
                    }
                }else{
                    CustomToast.fillAllFields(context);
                }
            }
        });
        builder.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDb.close();
                startActivity(intent);
            }
        });
        builder.show();
    }
}