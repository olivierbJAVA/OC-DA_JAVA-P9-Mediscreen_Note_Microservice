package com.mediscreen.note.repository;

import com.mediscreen.note.domain.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *  Class in charge of managing the custom repository methods not provided by the Spring data MongoRepository interface.
 */
@Repository
public class NoteRepositoryCustomImpl implements INoteRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public NoteRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Return the maximum (highest) patient id in the mongo database.
     *
     * @return The max patient id
     */
    @Override
    public long findMaxPatientId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "patientId"));
        query.limit(1);
        Note maxObject = mongoTemplate.findOne(query, Note.class);
        if (maxObject == null) {
            return 0L;
        }
        return maxObject.getPatientId();
    }
}
