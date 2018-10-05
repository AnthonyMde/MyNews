package com.mamode.anthony.mynews.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import com.mamode.anthony.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity {
    private int checkboxCounter = 0;

    @BindView(R.id.notification_frag_switch)
    Switch mSwitch;
    @BindView(R.id.input_search_and_notif)
    TextInputEditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ButterKnife.bind(this);
        this.configureToolbar();

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

    // Search button is enabled or disabled according to the required conditions.
    // At least 3 letters in the input and 1 checkbox checked
    private void enableSearchIfConditionMet(){
        mSwitch.setEnabled(mInput.getText().length()>= 1 && checkboxCounter>=1);
    }

    // Method call from the fragment_search_and_notif layout file
    public void onCheckboxClicked(View view){
        checkboxCounter = ((CheckBox)view).isChecked() ? checkboxCounter+1 : checkboxCounter-1;
        enableSearchIfConditionMet();
    }
}
