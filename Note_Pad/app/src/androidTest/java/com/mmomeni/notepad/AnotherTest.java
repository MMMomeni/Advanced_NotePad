package com.mmomeni.notepad;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class AnotherTest {

    @Rule
    public ActivityTestRule<EditActivity> editActivity = new ActivityTestRule<>(EditActivity.class);

    @Test
    public void ensureTextChangesWork() {
        onView(withId(R.id.titleBox)).perform(typeText("Title of this note"), closeSoftKeyboard());
        onView(withId(R.id.DescBox)).perform(typeText("Description of this note"), closeSoftKeyboard());
        onView(withId(R.id.titleBox)).check(matches(withText("Title of this note")));
    }

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);
}
