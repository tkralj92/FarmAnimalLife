package com.example.tomislavkralj.farmanimallife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class AddNewPigActivity extends AppCompatActivity {

    private Spinner fatherSp = (Spinner) findViewById(R.id.spinnerFather);
    private Spinner motherSp = (Spinner) findViewById(R.id.spinnerMother);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pig);

        MyDbHelper myDb = new MyDbHelper(this);


        List<Integer> motherArray = new ArrayList<>();
        motherArray.addAll(myDb.getAllMothers(null));
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, motherArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherSp.setAdapter(adapter2);


        List<Integer> fatherArray = new ArrayList<>();
        fatherArray.addAll(myDb.getAllFathers(null));
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, fatherArray);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatherSp.setAdapter(adapter3);
    }

    public void addNewPig(View view) {

        MyDbHelper myDb = new MyDbHelper(this);
        EditText pig_weight = (EditText) findViewById(R.id.pig_weight);
        RadioButton pig_gender = (RadioButton) findViewById(R.id.pig_male);
        RadioButton pig_genderF = (RadioButton) findViewById((R.id.pig_female));
        DatePicker pig_date = (DatePicker) findViewById(R.id.datePicker2);
        
        int year = pig_date.getYear();
        int month = pig_date.getMonth();
        int day = pig_date.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date dateOfBirth = (calendar.getTime());

        if (!pig_weight.getText().toString().equals("") ) {
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
                Toast msg = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
                msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                msg.show();
            }
        }else {
            Toast msg = Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT);
            msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            msg.show();
        }
    }
}
