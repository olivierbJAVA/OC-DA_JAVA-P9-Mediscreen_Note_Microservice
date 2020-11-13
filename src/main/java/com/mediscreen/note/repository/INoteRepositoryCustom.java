package com.mediscreen.note.repository;

/**
 * Interface used to define custom repository methods not provided by the Spring data MongoRepository interface.
 */
public interface INoteRepositoryCustom {

    /**
     * Return the maximum (highest) patient id in the mongo database.
     *
     * @return The max patient id
     */
    long getMaxPatientId();

}
