package com.mamode.anthony.mynews.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.controllers.SearchActivity;
import com.mamode.anthony.mynews.model.Constants;
import com.mamode.anthony.mynews.utils.NewsDate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    public interface onResearchListener {
        void displaySearchResults(HashMap<String, String> query);
    }

    private int actualYear, actualMonth, actualDay;
    private String beginDateValue = "", endDateValue = "";
    private String queryBeginDateValue = "", queryEndDateValue = "";
    private int checkboxCounter = 0;
    private HashMap<String, String> checkBoxesChecked = new HashMap<>();
    private HashMap<String, String> query = null;
    private onResearchListener onResearchListener;

    @BindView(R.id.begin_date) EditText beginDate;
    @BindView(R.id.end_date) EditText endDate;
    @BindView(R.id.search_frag_button) Button mSearchButton;
    @BindView(R.id.input_search_and_notif) TextInputEditText mInput;
    @BindView(R.id.textInputLayout) TextInputLayout mInputLayout;
    @BindView(R.id.checkbox1) CheckBox checkBox1;
    @BindView(R.id.checkbox2) CheckBox checkBox2;
    @BindView(R.id.checkbox3) CheckBox checkBox3;
    @BindView(R.id.checkbox4) CheckBox checkBox4;
    @BindView(R.id.checkbox5) CheckBox checkBox5;
    @BindView(R.id.checkbox6) CheckBox checkBox6;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null)
            onResearchListener = (onResearchListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_and_notif, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        mSearchButton.setOnClickListener(view1 -> {
            query = new HashMap<>();
            addQueryDataToTheMap();
            if(onResearchListener != null)
                Log.e("APITRY", "Just before trigger method in frag : "+query.get("api-key"));
                onResearchListener.displaySearchResults(query);
        });
        this.setActualDate();
        this.configureDatePicker(beginDate);
        this.configureDatePicker(endDate);
    }

    private void addQueryDataToTheMap() {
        query.put("api-key", Constants.API_KEY);
        Log.e("APITRY", "In the PUT method : "+query.get("api-key"));
        query.put("q", mInput.getText().toString());
        // query.put("begin_date", queryBeginDateValue);
        // query.put("end_date", queryEndDateValue);
        // TODO: Add themes query
    }

    private void setActualDate() {
        Calendar calendar = Calendar.getInstance();
        this.actualYear = calendar.get(Calendar.YEAR);
        this.actualMonth = calendar.get(Calendar.MONTH);
        this.actualDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Set default google calendar with max and min date logic
    private void configureDatePicker(final EditText datePickerEditText) {
        datePickerEditText.setKeyListener(null);
        datePickerEditText.setOnClickListener((View v) -> onDatePickerClick(datePickerEditText));
    }

    private void onDatePickerClick(final EditText datePickerEditText) {
        if(getContext() != null){
            final DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme,
                    (view, year, month, dayOfMonth) -> displaySelectedDate(year, month, dayOfMonth, datePickerEditText), actualYear, actualMonth, actualDay);

            // Max date by default is the current day
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            // Max and Min date depends on what is selected in begin DatePicker and end DatePicker
            setDatePickerBoundaries(datePickerDialog, datePickerEditText);
            datePickerDialog.show();
        }
    }

    // Logic for avoid the begin date and the end date to pass each other
    private void setDatePickerBoundaries(DatePickerDialog datePickerDialog, EditText datePickerEditText) {
        try {
            if (datePickerEditText == beginDate && !endDateValue.equals(""))
                datePickerDialog.getDatePicker().setMaxDate((long) NewsDate.convertDateIntoMillis(endDateValue) + 40000000);
            else if (datePickerEditText == endDate && !beginDateValue.equals(""))
                datePickerDialog.getDatePicker().setMinDate((long) NewsDate.convertDateIntoMillis(beginDateValue) + 40000000);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
        }

    }

    // Display the selected date and store it (those data are used in setDatePickerBoundaries)
    private void displaySelectedDate(int year, int month, int dayOfMonth, EditText datePickerEditText) {
        if (datePickerEditText == beginDate){
            beginDateValue = NewsDate.addZeroToDate(dayOfMonth)+"/"+ NewsDate.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(beginDateValue);
            queryBeginDateValue = "" + year + month + dayOfMonth;
        }else {
            endDateValue = NewsDate.addZeroToDate(dayOfMonth)+"/"+ NewsDate.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(endDateValue);
            queryEndDateValue = "" + year + month + dayOfMonth;
        }
    }

    // Search button is enabled or disabled according to the required conditions.
    // At least 3 letters in the input and 1 checkbox checked
    private void enableSearchIfConditionMet() {
        mSearchButton.setEnabled(mInput.getText().length()>= 1 && checkboxCounter>=1);
    }

    public void onCheckboxClicked(View view) {
        // TODO: When it's OK for the query strategy, clean up this method
        CheckBox checkBox = (CheckBox) view;
        String checkboxName = checkBox.getText().toString();
        if(checkBox.isChecked()) {
            checkboxCounter++;
            checkBoxesChecked.put(checkboxName, checkboxName);
        } else {
            checkboxCounter--;
            checkBoxesChecked.remove(checkboxName);
        }
        enableSearchIfConditionMet();
    }
}
