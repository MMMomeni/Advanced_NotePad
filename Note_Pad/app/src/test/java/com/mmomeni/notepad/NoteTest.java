package com.mmomeni.notepad;

import static org.mockito.Mockito.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NoteTest {

    @Test
    public void noteCreationTest() {
        Date date = new Date();
        Note note = new Note("Title of this note", "Description of this note");
        assertNotNull(note.getLastDate());
        assertNotNull(note.getLastDate());
        assertNotNull(note.getLastDate());
    }

}
