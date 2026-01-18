package io.cvmaker.api.controller;

import io.cvmaker.api.model.CV;
import io.cvmaker.api.model.User;
import io.cvmaker.api.service.CVService;
import io.cvmaker.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CVController {
    private final CVService cvService;
    private final UserService userService;


    public CVController(CVService cvService, UserService userService) {
        this.cvService = cvService;
        this.userService = userService;
    }

    @GetMapping()
    public List<CV> findCVByUserId(@AuthenticationPrincipal Jwt jwt) {
        return cvService.findByUserId(jwt.getSubject());
    }

    @PostMapping()
    public CV saveCV(@AuthenticationPrincipal Jwt jwt, @RequestBody CV cv) {
        User user = userService.syncUser(jwt);
        return cvService.saveCV(cv, user.getId());
    }

    @PutMapping("/{id}")
    public CV updateCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id, @RequestBody CV cv) {
        return cvService.updateCV(jwt.getSubject(), id, cv);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        cvService.deleteCV(jwt.getSubject(), id);
    }
}
