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

import static com.mamode.anthony.mynews.utils.NewsDate.formatDate;
import static com.mamode.anthony.mynews.utils.NewsDate.setQueryDateFormat;
import static com.mamode.anthony.mynews.utils.Utils.concatenateQueryThemes;

public class SearchFragment extends Fragment {

    public interface onResearchListener {
        void displaySearchResults(HashMap<String, String> query);
    }

    private int mActualYear, mActualMonth, mActualDay;

    /**
     * String use to display dates in the google calendar.
     */
    private String mBeginDateValue = "", mEndDateValue = "";

    /**
     * Formatted strings used in the API call to search articles.
     */
    private String mQueryBeginDateValue = "", mQueryEndDateValue = "";

    /**
     * This counter is used to know if at least
     * one checkbox have been checked.
     */
    private int mCheckboxCounter = 0;

    /**
     * HashMap keys represent the themes used in the user research. We add/delete
     * a theme each time the user (un)check a checkbox.
     */
    private HashMap<String, String> mCheckBoxesChecked = new HashMap<>();

    /**
     * Query containing all the params for the articles research.
     */
    private HashMap<String, String> mQuery = null;

    /**
     * Activity callback needed to launch the SectionFragment
     * research with the user search params.
     */
    private onResearchListener mOnResearchListener;

    @BindView(R.id.begin_date) EditText beginDateEditText;
    @BindView(R.id.end_date) EditText endDateEditText;
    @BindView(R.id.search_frag_button) Button mSearchButton;
    @BindView(R.id.input_search_and_notif) TextInputEditText mInput;
    @BindView(R.id.textInputLayout) TextInputLayout mInputLayout;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    /**
     * We set the SearchActivity callback in onAttach method
     * to be sure we do not get a null pointer when getting the activity.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null)
            mOnResearchListener = (onResearchListener) getActivity();
    }

    /**
     * Bind our views when we are sure the fragment view is created.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_and_notif, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Set a listener on input text changes. We enable/disable the search button
     * according to the input size.
     */
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

        /* Trigger SearchActivity callback to launch the research
         * with all the user parameters.
         */
        mSearchButton.setOnClickListener(view1 -> {
            mQuery = new HashMap<>();
            addQueryDataToTheMap();
            if (mOnResearchListener != null)
                mOnResearchListener.displaySearchResults(mQuery);
        });
        this.setActualDate();
        this.configureDatePicker(beginDateEditText);
        this.configureDatePicker(endDateEditText);
    }

    /**
     * Add all the user inputs in a HashMap which will used
     * for the articles search.
     */
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
        mQuery.put("fq", concatenateQueryThemes(mCheckBoxesChecked));
        mQuery.put("hl", "true");
    }

    private void setActualDate() {
        Calendar calendar = Calendar.getInstance();
        mActualYear = calendar.get(Calendar.YEAR);
        mActualMonth = calendar.get(Calendar.MONTH);
        mActualDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Custom an EditText into a DatePicker.
     * @param datePickerEditText the EditText to custom.
     */
    private void configureDatePicker(final EditText datePickerEditText) {
        // The EditText is custom : we can not type any text in...
        datePickerEditText.setKeyListener(null);
        // ...we can just click on it
        datePickerEditText.setOnClickListener((View v) -> onDatePickerClick(datePickerEditText));
    }

    /**
     * On click open the google calendar (create the datePickerDialog) and use
     * the user input to set max/min date boundaries.
     * @param datePickerEditText the custom EditText which will handle the click
     */
    private void onDatePickerClick(final EditText datePickerEditText) {
        if (getContext() != null) {
            final DatePickerDialog datePickerDialog =
                    new DatePickerDialog(getContext(), R.style.DialogTheme,
                    (view, year, month, dayOfMonth) -> displaySelectedDate(year, month, dayOfMonth, datePickerEditText),
                    mActualYear, mActualMonth, mActualDay);
            
            setDatePickerBoundaries(datePickerDialog, datePickerEditText);
            datePickerDialog.show();
        }
    }

    /**
     * Logic which prevents the begin and the end date to pass each other.
     * @param datePickerDialog The dialog containing the date picker.
     * @param datePickerEditText EditText where the boundaries are set.
     */
    private void setDatePickerBoundaries(DatePickerDialog datePickerDialog, EditText datePickerEditText) {
        // Max date is the current day by default
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        try {
            if (datePickerEditText == beginDateEditText && !mEndDateValue.equals(""))
                datePickerDialog.getDatePicker().setMaxDate((long) NewsDate.convertDateIntoMillis(mEndDateValue));
            else if (datePickerEditText == endDateEditText && !mBeginDateValue.equals(""))
                datePickerDialog.getDatePicker().setMinDate((long) NewsDate.convertDateIntoMillis(mBeginDateValue));
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "The date can't be parsed, please contact support", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Save the selected date and display it to the user.
     */
    private void displaySelectedDate(int year, int month, int dayOfMonth, EditText datePickerEditText) {
        if (datePickerEditText == beginDateEditText) {
            mBeginDateValue = formatDate(year, month, dayOfMonth);
            mQueryBeginDateValue = setQueryDateFormat(year, month, dayOfMonth);
            datePickerEditText.setText(mBeginDateValue);
        } else {
            mEndDateValue = formatDate(year, month, dayOfMonth);
            mQueryEndDateValue = setQueryDateFormat(year, month, dayOfMonth);
            datePickerEditText.setText(mEndDateValue);
        }
    }

    /**
     * Search button is enabled or disabled according to the required conditions.
     * At least 1 letter for the input text and 1 checkbox checked
     */
    private void enableSearchIfConditionMet() {
        if (mInput.getText() != null) {
            mSearchButton.setEnabled(
                    mInput.getText().length() >= 1
                            && mCheckboxCounter >= 1
            );
        }
    }

    /**
     * Method called when the user clicks on the checkboxes which
     * implemented the onClick method.
     * We update our counter up or down according to the view is checked or not.
     * We enable/disable the search button according to the counter.
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
}
