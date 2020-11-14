package com.mediscreen.note.service;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interface to be implemented to manage the services for Note entities.
 */
public interface INoteService {

    /**
     * Return a note given its id.
     *
     * @param id The id of the note
     * @return The note corresponding to the id
     * @throws ResourceNotFoundException if no note is found for the given id
     */
    Note findNoteById (String id) throws ResourceNotFoundException;

    /**
     * Return the list of notes given patient id.
     *
     * @param patientId The id of the patient
     * @return The list of notes corresponding to the patient id
     * @throws ResourceNotFoundException if no note is found for the given patient id
     */
    List<Note> findNotesByPatientId(long patientId) throws ResourceNotFoundException;

    /**
     * Return the list of notes given patient last name and first name.
     *
     * @param patientLastName The last name of the patient
     * @param patientFirstName The first name of the patient
     * @return The list of notes corresponding to the patient last name and first name
     * @throws ResourceNotFoundException if no note is found for the given patient last name and first name
     */
    List<Note> findNotesByPatientLastNameAndFirstName(String patientLastName, String patientFirstName) throws ResourceNotFoundException;

    /**
     * Return all notes.
     *
     * @return The list of all notes
     */
    List<Note> findAllNotes();

    /**
     * Update a note.
     *
     * @param note The note to update
     * @return The note updated
     * @throws ResourceNotFoundException if the note to update does not exist
     */
    Note updateNote(Note note) throws ResourceNotFoundException;

    /**
     * Create a note.
     *
     * @param note The note to create
     * @return The note created
     * @throws ResourceNotFoundException if the note to create does not exist
     */
    Note createNote(Note note);
}
