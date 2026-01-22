package io.cvmaker.api.service;

import io.cvmaker.api.exception.RessourceNotFoundException;
import io.cvmaker.api.exception.UnauthorizedAccessException;
import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.User;
import io.cvmaker.api.repository.CVRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CVService {
    private final CVRepository cvRepository;
    private final UserService userService;

    public CVService(CVRepository cvRepository, UserService userService) {
        this.cvRepository = cvRepository;
        this.userService = userService;
    }

    public List<CV> findByUserId(String userId) {
        User user = userService.findByGoogleId(userId);
        return cvRepository.findByUserId(user.getId());
    }

    public CV saveCV(CV cv, String userId) {
        cv.setUserId(userId);
        return cvRepository.save(cv);
    }

    public CV updateCV(String userId, String cvId, CV toUpdate ) {
        User user = userService.findByGoogleId(userId);
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RessourceNotFoundException("CV non trouvé : " + cvId));

        if (!cv.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("L'utilisateur n'est pas autorisé à modifier ce CV : " + cvId);
        }

        toUpdate.setId(cvId);
        toUpdate.setId(user.getId());
        return cvRepository.save(toUpdate);
    }

    public void deleteCV(String userId, String cvId) {
        CV toDelete = cvRepository.findById(cvId)
                .orElseThrow(() -> new RessourceNotFoundException("CV non trouvé : " + cvId));

        if (!toDelete.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("L'utilisateur n'est pas autorisé à supprimer ce CV : " + cvId);
        }
        cvRepository.deleteById(cvId);
    }
}
