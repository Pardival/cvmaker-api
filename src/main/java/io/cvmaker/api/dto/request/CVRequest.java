package io.cvmaker.api.dto.request;

import io.cvmaker.api.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CVRequest {
    private String cvName;
    private String summary;

    private PersonalDetailsRequest personalDetails;
    private List<EducationRequest> educations;
    private List<ExperienceRequest> experiences;
    private List<SkillRequest> skills;
    private List<LanguageRequest> languages;
}

