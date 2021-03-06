package com.example.tomislavkralj.farmanimallife;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;
import com.example.tomislavkralj.fragments.DatePickerFragment;
import com.example.tomislavkralj.toasts.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewPigActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    MyDbHelper myDb = new MyDbHelper(this);
    @BindView(R.id.pig_weight) EditText pig_weight;
    @BindView(R.id.spinnerFather) Spinner fatherSp;
    @BindView(R.id.spinnerMother) Spinner motherSp;
    @BindView(R.id.pig_male) RadioButton pig_gender;
    @BindView(R.id.pig_female) RadioButton pig_genderF;

    private Date dateOfBirth = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_pig_layout);
        ButterKnife.bind(this);

        List<Integer> motherArray = new ArrayList<>();
        motherArray.addAll(myDb.getAllMothers(null));
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, motherArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherSp.setAdapter(adapter2);

        List<Integer> fatherArray = new ArrayList<>();
        fatherArray.addAll(myDb.getAllFathers(null));
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, fatherArray);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatherSp.setAdapter(adapter3);
    }

    public void addNewPig(View view) {

        if (!pig_weight.getText().toString().equals("")) {
            int dad  = Integer.parseInt(fatherSp.getSelectedItem().toString());
            int mom = Integer.parseInt(motherSp.getSelectedItem().toString());

            if (pig_gender.isChecked()) {
                int weight = Integer.parseInt(pig_weight.getText().toString());
                Hog newPig = new Hog(10, false, weight, dateOfBirth, "", true, mom, dad);
                myDb.insertPig(newPig);
                Intent intent = new Intent(this, PigsListActivity.class);
                startActivity(intent);
            } else if (pig_genderF.isChecked()) {

                int weight = Integer.parseInt(pig_weight.getText().toString());
                Sow newPig = new Sow(10, true, weight, dateOfBirth, "", true, mom, dad);
                myDb.insertPig(newPig);
                Intent intent = new Intent(this, PigsListActivity.class);
                startActivity(intent);
            } else {
                CustomToast.fillAllFields(this);
            }
        }else {
            CustomToast.fillAllFields(this);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        dateOfBirth = (calendar.getTime());
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}