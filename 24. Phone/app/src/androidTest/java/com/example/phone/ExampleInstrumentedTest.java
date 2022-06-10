package com.example.phone;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();
        assertEquals("com.example.phone.test",
                appContext.getPackageName());
    }

    @Test
    public void iterateSpinnerItems() {
        String[] myArray = mActivityRule.getActivity().getResources()
                .getStringArray(R.array.labels_array);
        int size = myArray.length;
        for (int i=0; i<size; i++) {
            onView(withId(R.id.label_spinner)).perform(click());
            onData(is(myArray[i])).perform(click());
            onView(withId(R.id.text_phonelabel))
                    .check(matches(withText(containsString(myArray[i]))));
        }
    }
}