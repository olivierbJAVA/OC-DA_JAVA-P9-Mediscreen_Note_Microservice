package com.mediscreen.note.repository;

import com.mediscreen.note.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface extending the MongoRepository interface to manage CRUD methods for Note entities, using Spring Data.
 */
public interface NoteRepository extends MongoRepository<Note, String> {

    Note findByPatientLastNameAndPatientFirstName (String patientLastName, String patientFirstName);

}
