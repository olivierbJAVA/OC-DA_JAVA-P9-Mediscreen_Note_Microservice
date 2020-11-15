package com.mediscreen.note.controller;

import com.mediscreen.note.domain.Note;
import com.mediscreen.note.exception.ResourceNotFoundException;
import com.mediscreen.note.repository.INoteRepositoryCustom;
import com.mediscreen.note.service.INoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
     * Method managing the GET "/notes/list" endpoint HTTP request to get the list of all notes.
     *
     * @param model The Model containing the list of all notes
     * @return The name of the View
     */
    @GetMapping("/notes/list")
    public String getNotes(Model model) {

        logger.info("Request : GET /notes/list");

        List<Note> notes = noteService.findAllNotes();
        model.addAttribute("notes", notes);

        logger.info("Success : notes found, returning 'notes/list' view");

        return "notes/list";
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

        return "notes/list";
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

        return "notes/list";
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

        return "notes/list";
    }

    /**
     * Method managing the GET "/notes/updateform/{id}" endpoint HTTP request to update a note.
     *
     * @param id The id of the note to update
     * @param model The Model containing the note to update
     * @return The name of the View
     */
    @GetMapping("/notes/updateform/{id}")
    public String showNoteUpdateForm(@PathVariable("id") String id, Model model) {

        logger.info("Request : GET /notes/updateform/{}", id);

        Note note = noteService.findNoteById(id);
        model.addAttribute("note", note);

        logger.info("Success : note with id {} to update found, returning '/notes/updateform' view", id);

        return "notes/updateform";
    }

    /**
     * Method managing the POST "/notes/updateform/{id}" endpoint HTTP request to update a note.
     *
     * @param note The note to update
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/notes/updateform/{id}")
    public String updateNoteForm(@Valid Note note, BindingResult result) {

        logger.info("Request : POST /notes/updateform/{}", note.getId());

        if (result.hasErrors()) {

            logger.error("Error in fields validation : note with id {} not updated, returning '/notes/update' view", note.getId());

            return "notes/updateform";
        }

        noteService.updateNote(note);

        logger.info("Success : note with id {} updated, redirect to '/notes/list'", note.getId());

        return "redirect:/notes/list";
    }

    /**
     * Method managing the GET "/notes/addform" endpoint HTTP request to add a note using a form.
     *
     * @param note An empty note
     * @return The name of the View
     */
    @GetMapping("/notes/addform")
    public String addNoteForm(Note note) {

        logger.info("Request : GET /notes/addform");
        logger.info("Success : returning 'notes/addform' view");

        return "notes/addform";
    }

    /**
     * Method managing the POST "/notes/validateform" endpoint HTTP request to add a note using a form.
     *
     * @param note The note to add
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/notes/validateform")
    public String validateNoteForm(@Valid Note note, BindingResult result) {

        logger.info("Request : POST /notes/validateform");

        if (!result.hasErrors()) {

            try {
                List<Note> notes = noteService.findNotesByPatientLastNameAndFirstName(note.getPatientLastName(), note.getPatientFirstName());
                // Patient is already existing -> we get its id
                note.setPatientId(notes.get(0).getPatientId());
                //noteService.createNote(note);

            } catch (ResourceNotFoundException e) {
                // New patient -> we put its id at the max id in the database +1
                note.setPatientId(noteService.getMaxPatientId()+1L);
                //noteService.createNote(note);
            }

            noteService.createNote(note);

            logger.info("Success : new note created, redirect to '/notes/list' view");

            return "redirect:/notes/list";
        }

        logger.error("Error in fields validation : new note not created, returning '/notes/addform' view");

        return "notes/addform";
    }

    /**
     * Method managing the POST "/patHistory/add" endpoint HTTP request to add a patient using a command line HTTP client and parameters in the URL request.
     *
     * @param patId The id of the patient
     * @param note The note to add to the patient history
     * @return A ResponseEntity containing the location of the note created and the HTTP status code
     */
    @PostMapping("/patHistory/add")
    public ResponseEntity<Note> addNoteByPatientId(@RequestParam long patId, @RequestParam String note) {

        logger.info("Request : POST /patHistory/add");

        Note noteToAdd = new Note();

        try {
            List<Note> notes = noteService.findNotesByPatientId(patId);
            // Patient is already existing -> we get its id, last name and first name
            noteToAdd.setPatientId(patId);
            noteToAdd.setPatientLastName(notes.get(0).getPatientLastName());
            noteToAdd.setPatientFirstName(notes.get(0).getPatientFirstName());
            noteToAdd.setNoteText(note);
            noteService.createNote(noteToAdd);

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(patId);
        }

        logger.info("Success : new note created");

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(noteToAdd.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Method managing the POST "/patHistory/addByLastNameAndFirstName" endpoint HTTP request to add a patient using a command line HTTP client and parameters in the URL request.
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @param note The note to add to the patient history
     * @return A ResponseEntity containing the location of the note created and the HTTP status code
     */
    @PostMapping("/patHistory/addByLastNameAndFirstName")
    public ResponseEntity<Note> addNoteByPatientLastNameAndFirstName(@RequestParam String lastName, @RequestParam String firstName, @RequestParam String note) {

        logger.info("Request : POST /patHistory/addByLastNameAndFirstName");

        Note noteToAdd = new Note();

        try {
            List<Note> notes = noteService.findNotesByPatientLastNameAndFirstName(lastName, firstName);
            // Patient is already existing -> we get its id, last name and first name
            noteToAdd.setPatientId(notes.get(0).getPatientId());
        } catch (ResourceNotFoundException e) {
            // New patient -> we put its id at the max id in the database +1
            noteToAdd.setPatientId(noteService.getMaxPatientId()+1L);
        }

        noteToAdd.setPatientLastName(lastName);
        noteToAdd.setPatientFirstName(firstName);
        noteToAdd.setNoteText(note);

        noteService.createNote(noteToAdd);

        logger.info("Success : new patientToAdd created");

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(noteToAdd.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
