package io.cvmaker.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class PersonalDetailsRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String linkedInUrl;
        private String githubUrl;
        private String city;
        private String country;
        private String currentJobTitle;
    }