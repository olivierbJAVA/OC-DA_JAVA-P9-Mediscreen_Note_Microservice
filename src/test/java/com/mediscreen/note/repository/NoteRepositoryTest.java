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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        List<Note> listNotes = noteRepositoryUnderTest.findAll();

        // ASSERT
        assertEquals(3, listNotes.size());
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
    public void findNoteByLastNameAndFirstName() {
        // ARRANGE
        Note noteToFind = new Note("PatientLastName", "PatientFirstName","NoteText");
        mongoTemplate.insert(noteToFind);

        // ACT
        Note noteFound = noteRepositoryUnderTest.findByPatientLastNameAndPatientFirstName("PatientLastName", "PatientFirstName");

        // ASSERT
        assertNotNull(noteFound);
        assertEquals("PatientLastName", noteFound.getPatientLastName());
        assertEquals("PatientFirstName", noteFound.getPatientFirstName());
        assertEquals("NoteText", noteFound.getNoteText());
    }
}
