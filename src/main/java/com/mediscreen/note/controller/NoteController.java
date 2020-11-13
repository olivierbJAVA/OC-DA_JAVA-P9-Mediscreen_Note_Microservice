package com.mediscreen.note.controller;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.service.INoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller in charge of managing the endpoints for Note entities.
 */
@Controller
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Method managing the GET "/notes" endpoint HTTP request to get the list of all notes.
     *
     * @param model The Model containing the list of all notes
     * @return The name of the View
     */
    @GetMapping("/notes")
    public String getNotes(Model model) {

        logger.info("Request : GET /notes");

        List<Note> notes = noteService.findAllNotes();
        model.addAttribute("notes", notes);

        logger.info("Success : notes found, returning 'notes' view");

        return "notes";
    }

    /**
     * Method managing the GET "/notes/{id}" endpoint HTTP request to get a note given its id.
     *
     * @param id The id of the note to get
     * @param model The Model containing the note to get
     * @return The name of the View
     */
    @GetMapping("/notes/{id}")
    public String getNoteById(@PathVariable("id") String id, Model model) {

        logger.info("Request : GET /notes/{}", id);

        Note note = noteService.findNoteById(id);
        model.addAttribute("notes", note);

        logger.info("Success : note with id {} found, returning '/notes' view", id);

        return "notes";
    }

    /**
     * Method managing the GET "/patHistoryByPatientId" endpoint HTTP request to get notes for a patient given its id.
     *
     * @param patientId The id of the patient to get the notes
     * @param model The Model containing the notes
     * @return The name of the View
     */
    @GetMapping("/patHistoryByPatientId")
    public String getPatientHistoryByPatientId(@RequestParam("patId") long patientId, Model model) {

        logger.info("Request : GET /patHistoryByPatientId with patient id = {}", patientId);

        List<Note> notes = noteService.findNotesByPatientId(patientId);
        model.addAttribute("notes", notes);

        logger.info("Success : notes for patient with patient id {}, returning '/notes' view", patientId);

        return "notes";
    }

    /**
     * Method managing the GET "/patHistoryByPatientLastNameAndFirstName" endpoint HTTP request to get notes for a patient given its last name and first name.
     *
     * @param patientLastName The last name of the patient to get the notes
     * @param patientFirstName The first name of the patient to get the notes
     * @param model The Model containing the notes
     * @return The name of the View
     */
    @GetMapping("/patHistoryByPatientLastNameAndFirstName")
    public String getPatientHistoryByPatientLastNameAndFirstName(@RequestParam("lastName") String patientLastName, @RequestParam("firstName") String patientFirstName, Model model) {

        logger.info("Request : GET /patHistoryByPatientLastNameAndFirstName with patient last name = {} & first name = {}", patientLastName, patientFirstName);

        List<Note> notes = noteService.findNotesByPatientLastNameAndFirstName(patientLastName, patientFirstName);
        model.addAttribute("notes", notes);

        logger.info("Success : notes for patient with patient last name {} and first name {} found, returning '/notes' view", patientLastName, patientFirstName);

        return "notes";
    }
}
