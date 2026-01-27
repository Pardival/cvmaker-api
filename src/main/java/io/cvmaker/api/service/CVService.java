package io.cvmaker.api.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import io.cvmaker.api.exception.RessourceNotFoundException;
import io.cvmaker.api.exception.UnauthorizedAccessException;
import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.User;
import io.cvmaker.api.repository.CVRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Service
public class CVService {
    private final CVRepository cvRepository;
    private final UserService userService;
    private final PdfService pdfService;

    public CVService(CVRepository cvRepository, UserService userService, PdfService pdfService) {
        this.cvRepository = cvRepository;
        this.userService = userService;
        this.pdfService = pdfService;
    }

    public List<CV> findByUserId(String googleId) {
        User user = userService.findByGoogleId(googleId);
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

    public void deleteCV(String googleId, String cvId) {
        User user = userService.findByGoogleId(googleId);
        CV toDelete = cvRepository.findById(cvId)
                .orElseThrow(() -> new RessourceNotFoundException("CV non trouvé : " + cvId));

        if (!toDelete.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("L'utilisateur n'est pas autorisé à supprimer ce CV : " + cvId);
        }
        cvRepository.deleteById(cvId);
    }

    public byte[] generateCvPdf(String googleId, String cvId) {
        User user = userService.findByGoogleId(googleId);
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RessourceNotFoundException("CV non trouvé : " + cvId));

        if (!cv.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("L'utilisateur n'est pas autorisé à supprimer ce CV : " + cvId);
        }
        return pdfService.generatePdf(cv);
    }
}
