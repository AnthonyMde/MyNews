package com.mamode.anthony.mynews.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;

import com.mamode.anthony.mynews.R;
import com.mamode.anthony.mynews.utils.NotificationWorker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {
    /**
     * This counter is used to know if at least
     * one checkbox have been checked.
     */
    private int mCheckboxCounter = 0;
    /**
     * SharedPreferences editor used to saved the notifications
     * preferences of the user
     */
    private SharedPreferences.Editor mSharedPrefEditor = null;
    @BindView(R.id.notification_frag_switch)
    Switch mSwitch;
    @BindView(R.id.input_search_and_notif)
    TextInputEditText mInput;
    @BindView(R.id.checkboxScience) CheckBox mCheckboxScience;
    @BindView(R.id.checkboxHealth) CheckBox mCheckboxHealth;
    @BindView(R.id.checkboxWorld) CheckBox mCheckboxWorld;
    @BindView(R.id.checkboxTechnology) CheckBox mCheckboxTechnology;
    @BindView(R.id.checkboxFinancial) CheckBox mCheckboxFinancial;
    @BindView(R.id.checkboxEducation) CheckBox mCheckboxEducation;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext() != null) {
            mSharedPrefEditor = getContext().getSharedPreferences("SAVED_NOTIFICATION", Context.MODE_PRIVATE).edit();
        }
    }

    /**
     * We use the same layout for the NotificationFragment and the SearchFragment
     * so we hide the unnecessary parts in the onCreateView().
     * If the user have saved a research to be notified, we retrieve it from sharedPref.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_and_notif, container, false);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.search_group_hide).setVisibility(View.GONE);
        view.findViewById(R.id.notification_frag_switch).setVisibility(View.VISIBLE);

        if (getContext() != null) {
            displayNotificationPreferences();
        }

        return view;
    }

    /**
     * Set a listener on input text changes. We enable/disable the search button
     * according to the input size. The switch is automatically unchecked when the
     * user modify is request.
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
                mSwitch.setChecked(false);
                enableSearchIfConditionMet();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * We save/delete the user preferences and launch/cancel
     * according to the user notification is on/off.
     */
    @Override
    public void onPause() {
        super.onPause();
        manageNotificationPreferences();
        manageNotificationWork();
    }

    /**
     * Switch button is enabled or disabled according to the required conditions.
     * At least 1 letter in the input text and 1 checkbox checked
     */
    private void enableSearchIfConditionMet(){
        if (mInput.getText() != null) {
            mSwitch.setEnabled(
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
     * We save if the checkbox is checked or not in the sharedPrefs.
     * @param view The checkbox that have been clicked.
     */
    public void onCheckboxClicked(View view){
        mSwitch.setChecked(false);
        CheckBox checkBox = (CheckBox) view;
        String checkboxName = checkBox.getText().toString();
        if (checkBox.isChecked()) {
            mCheckboxCounter++;
            mSharedPrefEditor.putBoolean(checkboxName, true);
        } else {
            mCheckboxCounter--;
            mSharedPrefEditor.putBoolean(checkboxName, false);
        }
        enableSearchIfConditionMet();
    }

    /**
     * If the user wants to be notified for his research (switch on),
     * we save it into sharePrefs. If not, we clean up the SearchPrefs.
     */
    private void manageNotificationPreferences() {
        if (mSwitch.isChecked() && mInput.getText() != null) {
            mSharedPrefEditor.putString("query", mInput.getText().toString());
            mSharedPrefEditor.putBoolean("isSwitchActive", mSwitch.isEnabled());
        } else {
            mSharedPrefEditor.clear();
        }
        mSharedPrefEditor.apply();
    }

    /**
     * When user came back to the notification activity and have saved
     * a research, we retrieve this research from the sharedPrefs and
     * display it into the view.
     */
    private void displayNotificationPreferences() {
        if (getContext() != null) {
            SharedPreferences sharedPrefs = getContext().getSharedPreferences("SAVED_NOTIFICATION", Context.MODE_PRIVATE);
            Boolean isSwitchActive = sharedPrefs.getBoolean("isSwitchActive", false);
            if (isSwitchActive) {
                String query = sharedPrefs.getString("query", "");
                mInput.setText(query);
                mSwitch.setChecked(true);
                mSwitch.setEnabled(true);
                if (sharedPrefs.getBoolean("Science", false)) {
                    mCheckboxScience.setChecked(true);
                    mCheckboxCounter++;
                }
                if (sharedPrefs.getBoolean("Health", false)) {
                    mCheckboxHealth.setChecked(true);
                    mCheckboxCounter++;
                }
                if (sharedPrefs.getBoolean("World", false)) {
                    mCheckboxWorld.setChecked(true);
                    mCheckboxCounter++;
                }
                if (sharedPrefs.getBoolean("Technology", false)) {
                    mCheckboxTechnology.setChecked(true);
                    mCheckboxCounter++;
                }
                if (sharedPrefs.getBoolean("Financial", false)) {
                    mCheckboxFinancial.setChecked(true);
                    mCheckboxCounter++;
                }
                if (sharedPrefs.getBoolean("Education", false)) {
                    mCheckboxEducation.setChecked(true);
                    mCheckboxCounter++;
                }
            }
        }
    }

    /**
     * Create a PeriodicWorkRequest and active/cancel a periodic
     * work to send notifications to the user according to his choice
     * (switch on/off).
     */
    private void manageNotificationWork() {
        Constraints notificationsContraints =
                new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build();
        PeriodicWorkRequest notificationCheckWork =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.HOURS)
                        .setConstraints(notificationsContraints)
                        .build();
        if (mSwitch.isChecked()) {
            WorkManager.getInstance().enqueue(notificationCheckWork);
        } else {
            UUID notificationWorkID = notificationCheckWork.getId();
            WorkManager.getInstance().cancelWorkById(notificationWorkID);
        }
    }
}
