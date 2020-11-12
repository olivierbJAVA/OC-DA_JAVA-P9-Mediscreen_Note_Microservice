package com.mediscreen.note.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Class materializing a note.
 */
@Document
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String patientLastName;
    private String patientFirstName;

    private String noteText;

    public Note() {
    }

    public Note(String patientLastName, String patientFirstName, String noteText) {
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
        this.noteText = noteText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
