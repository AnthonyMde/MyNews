package com.mamode.anthony.mynews.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.fragments.SearchFragment;
import com.mamode.anthony.mynews.utils.Utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private int actualYear, actualMonth, actualDay;
    private String beginDateValue = "", endDateValue = "";
    private int checkboxCounter = 0;

    @BindView(R.id.begin_date)
    EditText beginDate;
    @BindView(R.id.end_date)
    EditText endDate;
    @BindView(R.id.search_frag_button)
    Button mSearchButton;
    @BindView(R.id.input_search_and_notif)
    TextInputEditText mInput;
    @BindView(R.id.textInputLayout)
    TextInputLayout mInputLayout;

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
                enableSearchIfConditionMet();
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

    private void setActualDate(){
        Calendar calendar = Calendar.getInstance();
        this.actualYear = calendar.get(Calendar.YEAR);
        this.actualMonth = calendar.get(Calendar.MONTH);
        this.actualDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Set default google calendar with max and min date logic
    private void configureDatePicker(final EditText datePickerEditText){
        datePickerEditText.setKeyListener(null);
        datePickerEditText.setOnClickListener((View v) -> onDatePickerClick(datePickerEditText));
    }

    private void onDatePickerClick(final EditText datePickerEditText) {
        final DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(SearchActivity.this, R.style.DialogTheme,
                (view, year, month, dayOfMonth) -> displaySelectedDate(year, month, dayOfMonth, datePickerEditText), actualYear, actualMonth, actualDay);

        // Max date by default is the current day
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        // Max and Min date depends on what is selected in begin DatePicker and end DatePicker
        setDatePickerBoundaries(datePickerDialog, datePickerEditText);
        datePickerDialog.show();
    }

    // Logic for avoid the begin date and the end date to pass each other
    private void setDatePickerBoundaries(DatePickerDialog datePickerDialog, EditText datePickerEditText) {
            try {
                if (datePickerEditText == beginDate && !endDateValue.equals(""))
                    datePickerDialog.getDatePicker().setMaxDate((long) Utils.convertDateIntoMillis(endDateValue) + 40000000);
                else if (datePickerEditText == endDate && !beginDateValue.equals(""))
                    datePickerDialog.getDatePicker().setMinDate((long) Utils.convertDateIntoMillis(beginDateValue) + 40000000);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
            }

    }

    // Display the selected date and store it (those data are used in setDatePickerBoundaries)
    private void displaySelectedDate(int year, int month, int dayOfMonth, EditText datePickerEditText) {
        if (datePickerEditText == beginDate){
            beginDateValue = Utils.addZeroToDate(dayOfMonth)+"/"+ Utils.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(beginDateValue);
        }else {
            endDateValue = Utils.addZeroToDate(dayOfMonth)+"/"+ Utils.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(endDateValue);
        }
    }

    // Search button is enabled or disabled according to the required conditions.
    // At least 3 letters in the input and 1 checkbox checked
    private void enableSearchIfConditionMet(){
        mSearchButton.setEnabled(mInput.getText().length()>= 1 && checkboxCounter>=1);
    }

    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view){
        checkboxCounter = ((CheckBox)view).isChecked() ? checkboxCounter+1 : checkboxCounter-1;
        enableSearchIfConditionMet();
    }
}
