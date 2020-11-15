package com.mediscreen.note.service;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.exception.ResourceNotFoundException;
import com.mediscreen.note.repository.INoteRepositoryCustom;
import com.mediscreen.note.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Class including unit tests for the NoteServiceImpl Class.
 */
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteServiceImpl noteServiceImplUnderTest;

    @Mock
    private NoteRepository mockNoteRepository;

    @Mock
    private INoteRepositoryCustom mockNoteRepositoryCustomImpl;

    @Test
    public void findNoteById_whenIdExist() {
        // ARRANGE
        Note noteToFind = new Note("NoteTestPatientLastName", "NoteTestPatientFirstName", "NoteTestText");
        noteToFind.setId("idNoteTest");
        doReturn(Optional.of(noteToFind)).when(mockNoteRepository).findById(noteToFind.getId());

        // ACT
        Note noteFound = noteServiceImplUnderTest.findNoteById("idNoteTest");

        // ASSERT
        verify(mockNoteRepository, times(1)).findById("idNoteTest");
        assertEquals(noteToFind, noteFound);
    }

    @Test
    public void findNoteById_whenIdNotExist() {
        // ARRANGE
        doReturn(Optional.empty()).when(mockNoteRepository).findById("idNotExist");

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            noteServiceImplUnderTest.findNoteById("idNotExist");
        });
        verify(mockNoteRepository, times(1)).findById("idNotExist");
    }

    @Test
    public void findNotesByPatientId_whenExist() {
        // ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        noteToFind1.setPatientId(1L);
        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        noteToFind1.setPatientId(1L);
        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");
        noteToFind1.setPatientId(1L);

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteRepository).findByPatientId(1L);

        // ACT
        List<Note> notesFound = noteServiceImplUnderTest.findNotesByPatientId(1L);

        // ASSERT
        verify(mockNoteRepository, times(1)).findByPatientId(1L);
        assertEquals(notesToFind, notesFound);
    }

    @Test
    public void findNotesByPatientId_whenNotExist() {
        // ARRANGE
        List<Note> emptyListNotesToFind = new ArrayList<>();
        doReturn(emptyListNotesToFind).when(mockNoteRepository).findByPatientId(1L);

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            noteServiceImplUnderTest.findNotesByPatientId(1L);
        });
        verify(mockNoteRepository, times(1)).findByPatientId(1L);
    }

    @Test
    public void findNotesByPatientLastNameAndFirstName_whenExist() {
        // ARRANGE
        Note noteToFind1 = new Note("NoteTestPatientLastName", "NoteTestPatientFirstName","NoteText1");
        Note noteToFind2 = new Note("NoteTestPatientLastName", "NoteTestPatientFirstName","NoteText2");
        Note noteToFind3 = new Note("NoteTestPatientLastName", "NoteTestPatientFirstName","NoteText3");

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteRepository).findByPatientLastNameAndPatientFirstName("NoteTestPatientLastName", "NoteTestPatientFirstName");

        // ACT
        List<Note> notesFound = noteServiceImplUnderTest.findNotesByPatientLastNameAndFirstName("NoteTestPatientLastName", "NoteTestPatientFirstName");

        // ASSERT
        verify(mockNoteRepository, times(1)).findByPatientLastNameAndPatientFirstName("NoteTestPatientLastName", "NoteTestPatientFirstName");
        assertEquals(notesToFind, notesFound);
    }

    @Test
    public void findNotesByPatientLastNameAndFirstName_whenNotExist() {
        // ARRANGE
        List<Note> emptyListNotesToFind = new ArrayList<>();
        doReturn(emptyListNotesToFind).when(mockNoteRepository).findByPatientLastNameAndPatientFirstName("PatientTestLastNameNotExist", "PatientTestFirstNameNotExist");

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            noteServiceImplUnderTest.findNotesByPatientLastNameAndFirstName("PatientTestLastNameNotExist", "PatientTestFirstNameNotExist");
        });
        verify(mockNoteRepository, times(1)).findByPatientLastNameAndPatientFirstName("PatientTestLastNameNotExist", "PatientTestFirstNameNotExist");
    }

    @Test
    public void findAllNotes() {
        // ARRANGE
        Note noteToFind1 = new Note("NoteTestPatientLastName1", "NoteTestPatientFirstName1", "NoteTestText1");
        noteToFind1.setId("idNoteTest1");
        Note noteToFind2 = new Note("NoteTestPatientLastName2", "NoteTestPatientFirstName2", "NoteTestText2");
        noteToFind2.setId("idNoteTest2");
        Note noteToFind3 = new Note("NoteTestPatientLastName1", "NoteTestPatientFirstName1", "NoteTestText1");
        noteToFind1.setId("idNoteTest1");

        List<Note> notesToFind = new ArrayList<>();
        notesToFind.add(noteToFind1);
        notesToFind.add(noteToFind2);
        notesToFind.add(noteToFind3);

        doReturn(notesToFind).when(mockNoteRepository).findAll();

        // ACT
        List<Note> notesFound = noteServiceImplUnderTest.findAllNotes();

        // ASSERT
        verify(mockNoteRepository, times(1)).findAll();
        assertEquals(notesToFind, notesFound);
    }

    @Test
    public void updateNote_whenIdExist() {
        // ARRANGE
        Note noteToUpdate = new Note("PatientLastName", "PatientFirstName", "NoteText");
        noteToUpdate.setPatientId(1L);
        noteToUpdate.setId("5fa9cd63681c104404d45c23");
        doReturn(Optional.of(noteToUpdate)).when(mockNoteRepository).findById("5fa9cd63681c104404d45c23");
        doReturn(noteToUpdate).when(mockNoteRepository).save(noteToUpdate);

        // ACT
        Note noteUpdated = noteServiceImplUnderTest.updateNote(noteToUpdate);

        // ASSERT
        verify(mockNoteRepository, times(1)).save(noteToUpdate);
        assertEquals(noteToUpdate, noteUpdated);
    }

    @Test
    public void updateNote_whenIdNotExist() {
        // ARRANGE
        doReturn(Optional.empty()).when(mockNoteRepository).findById("5fa9cd63681c104404d45c24");

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            noteServiceImplUnderTest.findNoteById("5fa9cd63681c104404d45c24");
        });
        verify(mockNoteRepository, never()).save(any(Note.class));
    }

    @Test
    public void createNote() {
        // ARRANGE
        Note noteToCreate = new Note("PatientLastName", "PatientFirstName", "NoteText");
        doReturn(noteToCreate).when(mockNoteRepository).save(noteToCreate);

        // ACT
        Note noteCreated = noteServiceImplUnderTest.createNote(noteToCreate);

        // ASSERT
        verify(mockNoteRepository, times(1)).save(noteToCreate);
        assertEquals(noteToCreate, noteCreated);
    }

    @Test
    public void getMaxPatientId() {
        // ARRANGE
        doReturn(1L).when(mockNoteRepositoryCustomImpl).findMaxPatientId();

        // ACT
        long masPatientIdFound = noteServiceImplUnderTest.getMaxPatientId();

        // ASSERT
        verify(mockNoteRepositoryCustomImpl, times(1)).findMaxPatientId();
        assertEquals(1L, masPatientIdFound);
    }
}
