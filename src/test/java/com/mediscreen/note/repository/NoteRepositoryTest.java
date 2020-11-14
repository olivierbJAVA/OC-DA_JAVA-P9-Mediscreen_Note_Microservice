package com.mediscreen.note.repository;

import com.mediscreen.note.domain.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class including tests for the Note entity Repository.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepositoryUnderTest;

    @Autowired
    private MongoTemplate mongoTemplate;
            
    @BeforeEach
    public void setup(){
        mongoTemplate.dropCollection(Note.class);
        //noteRepositoryUnderTest.deleteAll();
    }

    @AfterEach
    public void tearDown(){
        mongoTemplate.dropCollection(Note.class);
    }

    @Test
    public void findAllNotes() {
        // ARRANGE
        Note note1 = new Note("PatientLastName1", "PatientFirstName1","NoteText1");
        mongoTemplate.insert(note1);
        Note note2 = new Note("PatientLastName2", "PatientFirstName2","NoteText2");
        mongoTemplate.insert(note2);
        Note note3 = new Note("PatientLastName3", "PatientFirstName3","NoteText3");
        mongoTemplate.insert(note3);

        // ACT
        List<Note> notes = noteRepositoryUnderTest.findAll();

        // ASSERT
        assertEquals(3, notes.size());
    }

    @Test
    public void findNoteById() {
        // ARRANGE
        Note noteToFind = new Note("PatientLastName", "PatientFirstName","NoteText");
        mongoTemplate.insert(noteToFind);
        
        // ACT
        Optional<Note> noteFound = noteRepositoryUnderTest.findById(noteToFind.getId());

        // ASSERT
        assertTrue(noteFound.isPresent());
        assertEquals("PatientLastName", noteFound.get().getPatientLastName());
        assertEquals("PatientFirstName", noteFound.get().getPatientFirstName());
        assertEquals("NoteText", noteFound.get().getNoteText());
    }

    @Test
    public void findNotesByPatientId() {
        // ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        noteToFind1.setPatientId(1L);
        mongoTemplate.insert(noteToFind1);

        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        noteToFind2.setPatientId(1L);
        mongoTemplate.insert(noteToFind2);

        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");
        noteToFind3.setPatientId(1L);
        mongoTemplate.insert(noteToFind3);

        // ACT
        List<Note> notesFound = noteRepositoryUnderTest.findByPatientId(1L);

        // ASSERT
        assertEquals(3,notesFound.size());
    }

    @Test
    public void findNotesByLastNameAndFirstName() {
        // ARRANGE
        Note noteToFind1 = new Note("PatientLastName", "PatientFirstName","NoteText1");
        noteToFind1.setPatientId(1L);
        mongoTemplate.insert(noteToFind1);

        Note noteToFind2 = new Note("PatientLastName", "PatientFirstName","NoteText2");
        noteToFind2.setPatientId(1L);
        mongoTemplate.insert(noteToFind2);

        Note noteToFind3 = new Note("PatientLastName", "PatientFirstName","NoteText3");
        noteToFind3.setPatientId(1L);
        mongoTemplate.insert(noteToFind3);

        // ACT
        List<Note> notesFound = noteRepositoryUnderTest.findByPatientLastNameAndPatientFirstName("PatientLastName","PatientFirstName");

        // ASSERT
        assertEquals(3,notesFound.size());
    }

    @Test
    public void updateNote() {
        // ARRANGE
        Note noteToUpdate = new Note("PatientLastName", "PatientFirstName", "NoteText");
        noteToUpdate.setPatientId(1L);
        noteToUpdate = mongoTemplate.insert(noteToUpdate);

        // ACT
        noteToUpdate.setNoteText("NoteTextUpdated");
        Note noteUpdated = noteRepositoryUnderTest.save(noteToUpdate);

        // ASSERT
        assertEquals(noteToUpdate.getId(), noteUpdated.getId());
        assertEquals(noteToUpdate.getNoteText(), noteUpdated.getNoteText());
        assertEquals(noteToUpdate.getPatientId(), noteUpdated.getPatientId());
        assertEquals(noteToUpdate.getPatientLastName(), noteUpdated.getPatientLastName());
        assertEquals(noteToUpdate.getPatientFirstName(), noteUpdated.getPatientFirstName());
    }

}
