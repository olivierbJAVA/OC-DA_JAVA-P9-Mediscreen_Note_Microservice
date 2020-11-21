package com.mediscreen.note.repository;

import com.mediscreen.note.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface extending the MongoRepository interface to manage CRUD methods for Note entities, using Spring Data.
 */
public interface NoteRepository extends MongoRepository<Note, String> {

    /**
     * Return a list of notes given the patient last name and first name.
     *
     * @param patientLastName The last name of the patient
     * @param patientFirstName The first name of the patient
     * @return The list of notes corresponding to the patient last name and first name
     */
    List<Note> findByPatientLastNameAndPatientFirstName (String patientLastName, String patientFirstName);

    /**
     * Return a list of notes given the patient id.
     *
     * @param patientId The id of the patient
     * @return The list of notes corresponding to the patient id
     */
    List<Note> findByPatientId (long patientId);
}
