package com.mamode.anthony.mynews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mamode.anthony.mynews.controllers.NotificationsActivity;
import com.mamode.anthony.mynews.controllers.SearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class NotificationActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<NotificationsActivity> rule = new ActivityTestRule<>(NotificationsActivity.class);

    @Test
    public void Given_JustText_When_SearchButtonIsDisabled_Then_SwitchIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Carapuce"));
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_JustCheckboxChecked_When_SearchButtonIsDisabled_Then_SwitchIsDisabled() {
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_TextAndCheckboxChecked_When_SearchButtonIsDisabled_Then_SwitchIsEnabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Pikachu"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.checkbox2))
                .perform(click());
        onView(withId(R.id.notification_frag_switch))
                .check(matches((isEnabled())));
    }

    @Test
    public void Given_CheckboxDeselection_When_SearchButtonWasEnable_Then_SwitchIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Bulbizarre"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_DeleteText_When_SearchButtonWasEnable_Then_SwitchIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Tortank"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.input_search_and_notif))
                .perform(clearText());
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_RemoveText_When_SwitchIsChecked_Then_SwitchIsDisabledAndNotChecked() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Salameche"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.notification_frag_switch))
                .perform(click());
        onView(withId(R.id.input_search_and_notif))
                .perform(clearText());
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isEnabled())));
        onView(withId(R.id.notification_frag_switch))
                .check(matches(not(isChecked())));
    }
}
