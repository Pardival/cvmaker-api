package io.cvmaker.api.repository;

import io.cvmaker.api.model.CV;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVRepository extends MongoRepository<CV, String> {
    List<CV> findByUserId(String userId);
}
