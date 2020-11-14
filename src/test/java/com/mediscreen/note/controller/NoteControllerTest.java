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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
            mockMvc.perform(get("/notes/list"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes/list"));
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
                    .andExpect(view().name("notes/list"));
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
    public void getPatientHistoryByPatientId_whenPatientExist() {
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
            mockMvc.perform(get("/patHistoryByPatientId")
                    .param("patId","1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
        verify(mockNoteService, times(1)).findNotesByPatientId(1L);
    }

    @Test
    public void getPatientHistoryByPatientId_whenPatientNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockNoteService).findNotesByPatientId(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patHistoryByPatientId")
                    .param("patId","1"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNotesByPatientId(1L);
    }

    @Test
    public void getPatientHistoryByPatientLastNameAndFirstName_whenPatientExist() {
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
            mockMvc.perform(get("/patHistoryByPatientLastNameAndFirstName")
                    .param("lastName","PatientLastName")
                    .param("firstName","PatientFirstName"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("notes", notesToFind))
                    .andExpect(view().name("notes/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
        verify(mockNoteService, times(1)).findNotesByPatientLastNameAndFirstName("PatientLastName", "PatientFirstName");
    }

    @Test
    public void getPatientHistoryByPatientLastNameAndFirstName_whenPatientNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockNoteService).findNotesByPatientLastNameAndFirstName("PatientLastName","PatientFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patHistoryByPatientLastNameAndFirstName")
                    .param("lastName","PatientLastName")
                    .param("firstName","PatientFirstName"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNotesByPatientLastNameAndFirstName("PatientLastName","PatientFirstName");
    }

    @Test
    public void showNoteUpdateForm() {
        //ARRANGE
        Note noteTest = new Note("PatientLastName", "PatientFirstName","NoteText");
        noteTest.setPatientId(1L);
        noteTest.setId("5fa9cd63681c104404d45c23");

        doReturn(noteTest).when(mockNoteService).findNoteById("5fa9cd63681c104404d45c23");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notes/updateform/5fa9cd63681c104404d45c23"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("note", noteTest))
                    .andExpect(view().name("notes/updateform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).findNoteById("5fa9cd63681c104404d45c23");
    }

    @Test
    public void updateNoteForm_whenNoError() {
        //ARRANGE
        Note noteTest = new Note("PatientLastName", "PatientFirstName","NoteText");
        noteTest.setPatientId(1L);
        noteTest.setId("5fa9cd63681c104404d45c23");

        doReturn(noteTest).when(mockNoteService).updateNote(noteTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/notes/updateform/5fa9cd63681c104404d45c23")
                    .param("patientId","1")
                    .param("patientLastName", "PatientLastName")
                    .param("patientFirstName", "PatientFirstName")
                    .param("noteText", "NoteText"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", "/notes/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).updateNote(any(Note.class));
    }

    @Test
    public void updateNoteForm_whenError() {
        //ARRANGE
        Note noteTest = new Note("PatientLastName", "PatientFirstName","NoteText");
        noteTest.setPatientId(1L);
        noteTest.setId("5fa9cd63681c104404d45c23");

        doReturn(noteTest).when(mockNoteService).updateNote(noteTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/notes/updateform/5fa9cd63681c104404d45c23")
                    .param("patientId","1")
                    .param("patientLastName", "PatientLastName")
                    .param("patientFirstName", "PatientFirstName"))
                    // error : mandatory note text is missing
                    .andExpect(model().attributeHasFieldErrors("note", "noteText"))
                    .andExpect(view().name("notes/updateform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, never()).updateNote(any(Note.class));
    }

    @Test
    public void addNoteForm() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/notes/addform"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("notes/addform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
    }

    @Test
    public void validateNoteForm_whenNoErrorInFields() {
        //ARRANGE
        Note noteTest = new Note("PatientLastName", "PatientFirstName","NoteText");
        noteTest.setPatientId(1L);

        doReturn(noteTest).when(mockNoteService).createNote(noteTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/notes/validateform")
                    .param("patientId","1")
                    .param("patientLastName", "PatientLastName")
                    .param("patientFirstName", "PatientFirstName")
                    .param("noteText", "NoteText"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", "/notes/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, times(1)).createNote(any(Note.class));
    }

    @Test
    public void validateNoteForm_whenErrorInFields() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/notes/validateform")
                    .param("patientId","1")
                    .param("patientLastName", "PatientLastName")
                    .param("patientFirstName", "PatientFirstName"))
                    // error : mandatory note text is missing
                    .andExpect(model().attributeHasFieldErrors("note", "noteText"))
                    .andExpect(view().name("notes/addform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockNoteService, never()).createNote(any(Note.class));
    }
}
