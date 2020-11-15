package com.mediscreen.note.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * Class materializing a note.
 */
@Document
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private long patientId;

    @NotBlank(message = "PatientLastName is mandatory")
    private String patientLastName;

    @NotBlank(message = "PatientFirstName is mandatory")
    private String patientFirstName;

    @NotBlank(message = "Note text is mandatory")
    private String noteText;

    public Note() {
    }

    public Note(@NotBlank(message = "PatientLastName is mandatory") String patientLastName, @NotBlank(message = "PatientFirstName is mandatory") String patientFirstName, @NotBlank(message = "Note text is mandatory") String noteText) {
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

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
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
