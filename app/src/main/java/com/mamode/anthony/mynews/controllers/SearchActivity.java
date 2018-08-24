package com.mamode.anthony.mynews.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mamode.anthony.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private int actualYear;
    private int actualMonth;
    private int actualDay;

    @BindView(R.id.begin_date) EditText beginDate;
    @BindView(R.id.end_date) EditText endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureBeginDate();
        this.configureEndDate();

        //todo: refacto the beginData and add endDate computation methods
        //todo: beginData shall not exceed endDate value and conversely
        Calendar calendar = Calendar.getInstance();
        actualYear = calendar.get(Calendar.YEAR);
        actualMonth = calendar.get(Calendar.MONTH);
        actualDay = calendar.get(Calendar.DAY_OF_MONTH);

        beginDate.setText(actualDay+"/"+actualMonth+"/"+actualYear);
        endDate.setText(actualDay+"/"+actualMonth+"/"+actualYear);

    }

    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureBeginDate() {
        beginDate.setKeyListener(null);
        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(SearchActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        beginDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, actualYear, actualMonth, actualDay);
                datePicker.show();
            }
        });
    }

    private void configureEndDate() {
        endDate.setKeyListener(null);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(SearchActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, actualYear, actualMonth, actualDay);
                datePicker.show();
            }
        });
    }


}
