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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.utils.NewsDate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    public interface onResearchListener {
        void displaySearchResults(HashMap<String, String> query);
    }

    private int mActualYear, mActualMonth, mActualDay;
    private String mBeginDateValue = "", mEndDateValue = "";
    private String mQueryBeginDateValue = "", mQueryEndDateValue = "";
    private int mCheckboxCounter = 0;
    private HashMap<String, String> mCheckBoxesChecked = new HashMap<>();
    private HashMap<String, String> mQuery = null;
    private onResearchListener mOnResearchListener;

    @BindView(R.id.begin_date) EditText beginDate;
    @BindView(R.id.end_date) EditText endDate;
    @BindView(R.id.search_frag_button) Button mSearchButton;
    @BindView(R.id.input_search_and_notif) TextInputEditText mInput;
    @BindView(R.id.textInputLayout) TextInputLayout mInputLayout;

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
            mOnResearchListener = (onResearchListener) getActivity();
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
            mQuery = new HashMap<>();
            addQueryDataToTheMap();
            if (mOnResearchListener != null)
                mOnResearchListener.displaySearchResults(mQuery);
        });
        this.setActualDate();
        this.configureDatePicker(beginDate);
        this.configureDatePicker(endDate);
    }

    private void addQueryDataToTheMap() {
        if (mInput.getText() != null) {
            mQuery.put("q", mInput.getText().toString());
        }
        if (!mQueryBeginDateValue.equals("")) {
            mQuery.put("begin_date", mQueryBeginDateValue);
        }
        if (!mQueryEndDateValue.equals("")) {
            mQuery.put("end_date", mQueryEndDateValue);
        }
        mQuery.put("fq", setQueryThemes(mCheckBoxesChecked));
        mQuery.put("hl", "true");
    }

    private void setActualDate() {
        Calendar calendar = Calendar.getInstance();
        this.mActualYear = calendar.get(Calendar.YEAR);
        this.mActualMonth = calendar.get(Calendar.MONTH);
        this.mActualDay = calendar.get(Calendar.DAY_OF_MONTH);
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
                    (view, year, month, dayOfMonth) -> displaySelectedDate(year, month, dayOfMonth, datePickerEditText), mActualYear, mActualMonth, mActualDay);

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
            if (datePickerEditText == beginDate && !mEndDateValue.equals(""))
                datePickerDialog.getDatePicker().setMaxDate((long) NewsDate.convertDateIntoMillis(mEndDateValue) + 40000000);
            else if (datePickerEditText == endDate && !mBeginDateValue.equals(""))
                datePickerDialog.getDatePicker().setMinDate((long) NewsDate.convertDateIntoMillis(mBeginDateValue) + 40000000);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
        }

    }

    // Display the selected date and store it (those data are used in setDatePickerBoundaries)
    private void displaySelectedDate(int year, int month, int dayOfMonth, EditText datePickerEditText) {
        if (datePickerEditText == beginDate){
            mBeginDateValue = NewsDate.addZeroToDate(dayOfMonth)+"/"+ NewsDate.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(mBeginDateValue);
            mQueryBeginDateValue = "" + year + (NewsDate.addZeroToDate(month+1)) + NewsDate.addZeroToDate(dayOfMonth);
        }else {
            mEndDateValue = NewsDate.addZeroToDate(dayOfMonth)+"/"+ NewsDate.addZeroToDate(month+1)+"/"+year;
            datePickerEditText.setText(mEndDateValue);
            mQueryEndDateValue = "" + year + (NewsDate.addZeroToDate(month+1)) + NewsDate.addZeroToDate(dayOfMonth);
        }
    }

    // Search button is enabled or disabled according to the required conditions.
    // At least 1 letter in the input and 1 checkbox checked
    private void enableSearchIfConditionMet() {
        mSearchButton.setEnabled(
                mInput.getText().length() >= 1
                        && mCheckboxCounter >= 1
        );
    }

    /**
     * Method called when the user clicks on the checkboxes which
     * implemented the onClick method.
     * @param view The checkbox that have been clicked.
     */
    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        String checkboxName = checkBox.getText().toString();
        if (checkBox.isChecked()) {
            mCheckboxCounter++;
            mCheckBoxesChecked.put(checkboxName, checkboxName);
        } else {
            mCheckboxCounter--;
            mCheckBoxesChecked.remove(checkboxName);
        }
        enableSearchIfConditionMet();
    }

    private String setQueryThemes(HashMap<String, String> themesQueryMap) {
        StringBuilder sb = new StringBuilder();
        for(String key : themesQueryMap.keySet()) {
            sb.append(String.format("\"%s\" ", key));
        }
        return String.format("news_desk(%s)", sb);
    }
}
