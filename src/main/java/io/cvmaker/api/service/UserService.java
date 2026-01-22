package io.cvmaker.api.service;

import io.cvmaker.api.exception.RessourceNotFoundException;
import io.cvmaker.api.model.User;
import io.cvmaker.api.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new RessourceNotFoundException("User not found with googleId: " + googleId));
    }

    public User syncUser(Jwt jwt) {
        String googleId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");
        String profilePictureUrl = jwt.getClaimAsString("picture");
        String phoneNumber = jwt.getClaimAsString("phone_number");

       return  userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.setLastLogin(LocalDateTime.now());
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    User newUser = User
                            .builder()
                            .googleId(googleId)
                            .username(name)
                            .email(email)
                            .phoneNumber(phoneNumber)
                            .profilePictureUrl(profilePictureUrl)
                            .role("ROLE_USER")
                            .createdAt(LocalDateTime.now())
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
