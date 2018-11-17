package com.mamode.anthony.mynews.utils;

import android.widget.CheckBox;

import com.mamode.anthony.mynews.fragments.NotificationsFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

public class NotificationTest {
    private NotificationsFragment notifFragTest = new NotificationsFragment();
    private Field checkboxCounter = NotificationsFragment.class.getDeclaredField("checkboxCounter");
    @Mock
    private CheckBox checkBox;

    public NotificationTest() throws NoSuchFieldException {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        checkboxCounter.setAccessible(true);
        Mockito.when(checkBox.isChecked()).thenReturn(true);
    }

    @Test
    public void Increase_CheckboxCount_When_CheckboxIsChecked() throws IllegalAccessException {
        // checkboxCounter.setInt(notifFragTest, 0);
        // notifFragTest.onCheckboxClicked(checkBox);
        // assertEquals(1, checkboxCounter.getInt(notifFragTest));
    }
}
