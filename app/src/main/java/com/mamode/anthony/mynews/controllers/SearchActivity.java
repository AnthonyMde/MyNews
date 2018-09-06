package com.mamode.anthony.mynews.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.utils.Utils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private int actualYear;
    private int actualMonth;
    private int actualDay;
    private String beginDateValue = "";
    private String endDateValue = "";

    @BindView(R.id.begin_date) EditText beginDate;
    @BindView(R.id.end_date) EditText endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        ButterKnife.bind(this);

        this.configureToolbar();
        this.setActualDate();
        this.configureDatePicker(beginDate);
        this.configureDatePicker(endDate);
    }

    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
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
                    datePickerDialog.getDatePicker().setMaxDate((long) Utils.convertDateIntoMillis(endDateValue) + 40000000);
                }
                if (datePickerEditText == endDate && !beginDateValue.equals("")){
                    datePickerDialog.getDatePicker().setMinDate((long)(Utils.convertDateIntoMillis(beginDateValue) + 40000000));
                }
                datePickerDialog.show();
            }
        });
    }
}
