package com.mamode.anthony.mynews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mamode.anthony.mynews.controllers.SearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<SearchActivity> rule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void Given_JustText_When_SearchButtonIsDisabled_Then_SearchButtonIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Carapuce"));
        onView(withId(R.id.search_frag_button))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_JustCheckboxChecked_When_SearchButtonIsDisabled_Then_SearchButtonIsDisabled() {
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.search_frag_button))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_TextAndCheckboxChecked_When_SearchButtonIsDisabled_Then_SearchButtonIsEnabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Pikachu"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.checkbox2))
                .perform(click());
        onView(withId(R.id.search_frag_button))
                .check(matches((isEnabled())));
    }

    @Test
    public void Given_CheckboxDeselection_When_SearchButtonWasEnable_Then_SearchButtonIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Bulbizarre"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.search_frag_button))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void Given_DeleteText_When_SearchButtonWasEnable_Then_SearchButtonIsDisabled() {
        onView(withId(R.id.input_search_and_notif))
                .perform(typeText("Tortank"));
        onView(withId(R.id.checkbox1))
                .perform(click());
        onView(withId(R.id.input_search_and_notif))
                .perform(clearText());
        onView(withId(R.id.search_frag_button))
                .check(matches(not(isEnabled())));
    }
}
