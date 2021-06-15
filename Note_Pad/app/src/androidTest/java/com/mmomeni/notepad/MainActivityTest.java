package com.mmomeni.notepad;

import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import static org.junit.Assert.assertNotEquals;


@MediumTest
@RunWith(JUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void componentCheckInMainActivity() throws Exception {
        MainActivity activity = rule.getActivity();
        RecyclerView recyclerView = activity.findViewById(R.id.recycler);
        assertNotEquals(recyclerView, null);
    }



}
