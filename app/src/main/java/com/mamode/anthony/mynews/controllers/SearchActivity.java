package com.mamode.anthony.mynews.controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.utils.Utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity{
    private int actualYear;
    private int actualMonth;
    private int actualDay;
    private String beginDateValue = "";
    private String endDateValue = "";
    private int checkboxCounter = 0;

    @BindView(R.id.begin_date) EditText beginDate;
    @BindView(R.id.end_date) EditText endDate;
    @BindView(R.id.search_frag_button) Button mSearchButton;
    @BindView(R.id.input_search_and_notif) TextInputEditText mInput;
    @BindView(R.id.textInputLayout) TextInputLayout mInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        ButterKnife.bind(this);

        this.configureToolbar();
        this.setActualDate();
        this.configureDatePicker(beginDate);
        this.configureDatePicker(endDate);

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doWeSetButtonClickable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    private void doWeSetButtonClickable(){
        if(mInput.getText().length()>=3){
            if(checkboxCounter>=1)mSearchButton.setEnabled(true);
            else{mSearchButton.setEnabled(false);}
        }else{mSearchButton.setEnabled(false);}
    }

    // Method call from the layout file
    public void onCheckboxClicked(View view){
        if(((CheckBox)view).isChecked()){checkboxCounter++;}
        else{checkboxCounter--;}
        doWeSetButtonClickable();
    }

    private void setActualDate(){
        Calendar calendar = Calendar.getInstance();
        this.actualYear = calendar.get(Calendar.YEAR);
        this.actualMonth = calendar.get(Calendar.MONTH);
        this.actualDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Set default google calendar with max and min date logic
    private void configureDatePicker(final EditText datePickerEditText){
        datePickerEditText.setKeyListener(null);
        datePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(SearchActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (datePickerEditText == beginDate){
                            beginDateValue = Utils.addZeroToDate(dayOfMonth)+"/"+ Utils.addZeroToDate(month+1)+"/"+year;
                            datePickerEditText.setText(beginDateValue);
                        }else {
                            endDateValue = Utils.addZeroToDate(dayOfMonth)+"/"+ Utils.addZeroToDate(month+1)+"/"+year;
                            datePickerEditText.setText(endDateValue);
                        }
                    }
                }, actualYear, actualMonth, actualDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                if (datePickerEditText == beginDate && !endDateValue.equals("")){
                    try {
                        datePickerDialog.getDatePicker().setMaxDate((long)Utils.convertDateIntoMillis(endDateValue) + 40000000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
                    }
                }
                if (datePickerEditText == endDate && !beginDateValue.equals("")){
                    try {
                        datePickerDialog.getDatePicker().setMinDate((long)Utils.convertDateIntoMillis(beginDateValue) + 40000000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
                    }
                }
                datePickerDialog.show();
            }
        });
    }
}
