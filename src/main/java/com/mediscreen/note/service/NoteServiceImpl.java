package com.mediscreen.note.service;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.exception.ResourceNotFoundException;
import com.mediscreen.note.repository.INoteRepositoryCustom;
import com.mediscreen.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class in charge of managing the services for Note entities.
 */
@Service
public class NoteServiceImpl implements INoteService {

    private NoteRepository noteRepository;
    private INoteRepositoryCustom noteRepositoryCustom;

    public NoteServiceImpl(NoteRepository noteRepository, INoteRepositoryCustom noteRepositoryCustom) {
        this.noteRepository = noteRepository;
        this.noteRepositoryCustom = noteRepositoryCustom;
    }

    /**
     * Return a note given its id.
     *
     * @param id The id of the note
     * @return The note corresponding to the id
     * @throws ResourceNotFoundException if no note is found for the given id
     */
    @Override
    public Note findNoteById(String id) throws ResourceNotFoundException {
        return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Return the list of notes given patient id.
     *
     * @param patientId The id of the patient
     * @return The list of notes corresponding to the patient id
     * @throws ResourceNotFoundException if no note is found for the given patient id
     */
    @Override
    public List<Note> findNotesByPatientId(long patientId) throws ResourceNotFoundException {

        List<Note> notes = noteRepository.findByPatientId(patientId);

        if(notes.isEmpty()) {
            throw new ResourceNotFoundException(patientId);
        }

        return notes;
    }

    /**
     * Return the list of notes given patient last name and first name.
     *
     * @param patientLastName The last name of the patient
     * @param patientFirstName The first name of the patient
     * @return The list of notes corresponding to the patient last name and first name
     * @throws ResourceNotFoundException if no note is found for the given patient last name and first name
     */
    @Override
    public List<Note> findNotesByPatientLastNameAndFirstName(String patientLastName, String patientFirstName) throws ResourceNotFoundException {

        List<Note> notes = noteRepository.findByPatientLastNameAndPatientFirstName(patientLastName, patientFirstName);

        if(notes.isEmpty()) {
            throw new ResourceNotFoundException(patientLastName, patientFirstName);
        }

        return notes;
    }

    /**
     * Return all notes.
     *
     * @return The list of all notes
     */
    @Override
    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Update a note.
     *
     * @param note The note to update
     * @return The note updated
     * @throws ResourceNotFoundException if the note to update does not exist
     */
    @Override
    public Note updateNote(Note note) throws ResourceNotFoundException {

        noteRepository.findById(note.getId()).orElseThrow(() -> new ResourceNotFoundException(note.getId()));

        return noteRepository.save(note);

    }

    /**
     * Create a note.
     *
     * @param note The note to create
     * @return The note created
     */
    @Override
    public Note createNote(Note note) {

        return noteRepository.save(note);
    }

    /**
     * Return the maximum (highest) patient id in the mongo database.
     *
     * @return The max patient id
     */
    @Override
    public long getMaxPatientId() {

        return noteRepositoryCustom.findMaxPatientId();
    }
}
