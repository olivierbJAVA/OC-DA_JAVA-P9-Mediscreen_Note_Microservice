package com.mediscreen.note.repository;

import com.mediscreen.note.domain.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class including tests for the Note entity Custom Repository.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@DataMongoTest
public class NoteRepositoryCustomImplTest {
/*
    @TestConfiguration
    static class NoteRepositoryCustomImplTestContextConfiguration {
        @Bean
        public INoteRepositoryCustom iNoteRepositoryCustom() {
            return new NoteRepositoryCustomImpl(mongoTemplate);
        }
    }
*/
    @Autowired
    private INoteRepositoryCustom noteRepositoryCustomUnderTest;

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
    public void findMaxPatientId() {
        // ARRANGE
        Note noteTest = new Note("PatientLastName", "PatientFirstName","NoteText");
        mongoTemplate.save(noteTest);

        // ACT
        long maxPatientId = noteRepositoryCustomUnderTest.findMaxPatientId();

        // ASSERT
        assertEquals(0, maxPatientId);
    }
}
