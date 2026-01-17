package io.cvmaker.api.service;

import io.cvmaker.api.model.CV;
import io.cvmaker.api.repository.CVRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVService {
    private static CVRepository cvRepository;

    public CVService(CVRepository cvRepository) {
        CVService.cvRepository = cvRepository;
    }

    public CV saveCV(CV cv, String userId) {
        cv.setUserId(userId);
        return cvRepository.save(cv);
    }

    public List<CV> findByUserId(String userId) {
        return cvRepository.findByUserId(userId);
    }
}
