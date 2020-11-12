package com.mediscreen.note.domain;

import org.springframework.data.annotation.Id;

/**
 * Class materializing a note.
 */
public class Note {

    @Id
    public String id;

    public String patientFirstName;
    public String patientLastName;

    public String noteText;

    public Note() {
    }

    public Note(String patientFirstName, String patientLastName, String noteText) {
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.noteText = noteText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
