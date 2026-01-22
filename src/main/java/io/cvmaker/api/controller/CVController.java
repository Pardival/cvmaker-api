package io.cvmaker.api.controller;

import io.cvmaker.api.dto.request.CVRequest;
import io.cvmaker.api.dto.response.CVResponse;
import io.cvmaker.api.mapper.CVMapper;
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
    private final CVMapper cvMapper;


    public CVController(CVService cvService, UserService userService, CVMapper cvMapper) {
        this.cvService = cvService;
        this.userService = userService;
        this.cvMapper = cvMapper;
    }

    @GetMapping()
    public List<CVResponse> findCVByUserId(@AuthenticationPrincipal Jwt jwt) {
        return cvMapper.toResponse(cvService.findByUserId(jwt.getSubject()));
    }

    @PostMapping()
    public CVResponse saveCV(@AuthenticationPrincipal Jwt jwt, @RequestBody CVRequest request) {
        User user = userService.syncUser(jwt);
        return cvMapper.toResponse(cvService.saveCV(cvMapper.toEntity(request), user.getId()));
    }

    @PutMapping("/{id}")
    public CVResponse updateCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id, @RequestBody CV request) {
        return cvMapper.toResponse(cvService.updateCV(jwt.getSubject(), id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCV(@AuthenticationPrincipal Jwt jwt, @PathVariable String id) {
        cvService.deleteCV(jwt.getSubject(), id);
    }
}
