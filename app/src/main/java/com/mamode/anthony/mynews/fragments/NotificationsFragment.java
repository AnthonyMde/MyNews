package com.mamode.anthony.mynews.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;

import com.mamode.anthony.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {
    private int checkboxCounter = 0;
    @BindView(R.id.notification_frag_switch)
    Switch mSwitch;
    @BindView(R.id.input_search_and_notif)
    TextInputEditText mInput;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_and_notif, container, false);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.search_group_hide).setVisibility(View.GONE);
        view.findViewById(R.id.notification_frag_switch).setVisibility(View.VISIBLE);

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
    }

    // Search button is enabled or disabled according to the required conditions.
    // At least 3 letters in the input and 1 checkbox checked
    private void enableSearchIfConditionMet(){
        mSwitch.setEnabled(mInput.getText().length()>= 1 && checkboxCounter>=1);
    }

    public void onCheckboxClicked(View view){
        checkboxCounter = ((CheckBox)view).isChecked() ? checkboxCounter+1 : checkboxCounter-1;
        enableSearchIfConditionMet();
    }
}
