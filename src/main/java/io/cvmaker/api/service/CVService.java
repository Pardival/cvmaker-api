package io.cvmaker.api.service;

import io.cvmaker.api.model.CV;
import io.cvmaker.api.repository.CVRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVService {
    private static CVRepository cvRepository;
    private static UserService userService;

    public CVService(CVRepository cvRepository, UserService userService) {
        CVService.cvRepository = cvRepository;
        CVService.userService = userService;
    }

    public CV saveCV(CV cv, Jwt jwt) {
        userService.syncUser(jwt);
        cv.setUserId(jwt.getSubject());
        return cvRepository.save(cv);
    }

    public List<CV> findByUserId(String userId) {
        return cvRepository.findByUserId(userId);
    }
}
