package io.cvmaker.api.controller;

import io.cvmaker.api.model.CV;
import io.cvmaker.api.service.CVService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CVController {
    private static CVService cvService;

    public CVController(CVService cvService) {
        cvService = cvService;
    }

    @GetMapping()
    public List<CV> findCVByUserId(@AuthenticationPrincipal Jwt jwt) {
        return cvService.findByUserId(jwt.getSubject());
    }

    @PostMapping("/create")
    public CV saveCV(@AuthenticationPrincipal Jwt jwt, @RequestBody CV cv) {
        String googleId = jwt.getSubject();
        return cvService.saveCV(cv, googleId);
    }
}
