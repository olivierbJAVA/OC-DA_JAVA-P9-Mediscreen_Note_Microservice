package com.mediscreen.note.controller;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.exception.ResourceNotFoundException;
import com.mediscreen.note.service.INoteService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class including unit tests for the NoteController Class.
 */
@WebMvcTest(value = NoteController.class)
@ActiveProfiles("test")
public class NoteControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(NoteControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private INoteService mockNoteService;

    @Test
    public void getNotes() {
        //ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteService).findAllNotes();

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notes"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findAllNotes();
    }

    @Test
    public void getNoteById_whenIdExist() {
        //ARRANGE
        Note noteToFind = new Note("PatientLastName", "PatientFirstName","NoteText");
        noteToFind.setId("5fa9cd63681c104404d45c23");

        doReturn(noteToFind).when(mockNoteService).findNoteById("5fa9cd63681c104404d45c23");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notes/5fa9cd63681c104404d45c23"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", noteToFind))
                    .andExpect(view().name("notes"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNoteById("5fa9cd63681c104404d45c23");
    }

    @Test
    public void getNoteById_whenIdNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockNoteService).findNoteById("5fa9cd63681c104404d45c24");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notes/5fa9cd63681c104404d45c24"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNoteById("5fa9cd63681c104404d45c24");
    }

    @Test
    public void getNotesByPatientsId_whenPatientExist() {
        //ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        noteToFind1.setPatientId(1L);
        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        noteToFind2.setPatientId(1L);
        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");
        noteToFind3.setPatientId(1L);

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteService).findNotesByPatientId(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notesByPatientId")
                    .param("patientId","1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
        verify(mockNoteService, times(1)).findNotesByPatientId(1L);
    }

    @Test
    public void getNotesByPatientId_whenPatientNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockNoteService).findNotesByPatientId(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notesByPatientId")
                    .param("patientId","1"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNotesByPatientId(1L);
    }

    @Test
    public void getNotesByPatientLastNameAndFirstName_whenPatientExist() {
        //ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteService).findNotesByPatientLastNameAndFirstName("PatientLastName","PatientFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notesByPatientLastNameAndFirstName")
                    .param("patientLastName","PatientTestLastName")
                    .param("patientFirstName","PatientTestFirstName"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
        verify(mockNoteService, times(1)).findNotesByPatientLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
    }

    @Test
    public void getNotesByPatientLastNameAndFirstName_whenPatientNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockNoteService).findNotesByPatientLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notesByPatientLastNameAndFirstName")
                    .param("patientLastName","PatientTestLastName")
                    .param("patientFirstName","PatientTestFirstName"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNotesByPatientLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");
    }

}
