package com.mediscreen.note.exception;

/**
 * Class materializing the ResourceNotFoundException.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String id;

    private long patientId;

    private String patientLastName;
    private String patientFirstName;

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param id the id of the note that is not found
     */
    public ResourceNotFoundException(String id) {
        this.id = id;
    }

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param patientId the id of the patient for which no note is found
     */
    public ResourceNotFoundException(long patientId) {
        this.patientId = patientId;
    }

    /**
     * Constructs a new ResourceNotFoundException.
     *
     * @param patientLastName the last name of the patient for which no note is found
     * @param patientFirstName the first name of the patient for which no note is found
     */
    public ResourceNotFoundException(String patientLastName, String patientFirstName) {
        this.patientLastName = patientLastName;
        this.patientFirstName = patientFirstName;
    }
}
