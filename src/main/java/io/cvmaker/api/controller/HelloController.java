package io.cvmaker.api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo(@AuthenticationPrincipal Jwt jw) {
        return Map.of(
                "google_id", jw.getSubject(),
                "name", jw.getClaimAsString("name"),
                "email", jw.getClaimAsString("email")
        );
    }
}
