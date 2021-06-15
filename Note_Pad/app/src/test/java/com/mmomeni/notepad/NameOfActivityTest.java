package com.mmomeni.notepad;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class NameOfActivityTest {

    @Mock
    MainActivity mainActivity = Mockito.mock(MainActivity.class);
    EditActivity editActivity = Mockito.mock(EditActivity.class);
    AboutActivity aboutActivity = Mockito.mock(AboutActivity.class);

    @Test
    public void testActivityName() {
        assertTrue(mainActivity.getClass().getSimpleName().startsWith("MainActivity"));
        assertTrue(editActivity.getClass().getSimpleName().startsWith("EditActivity"));
        assertTrue(aboutActivity.getClass().getSimpleName().startsWith("AboutActivity"));
    }

}