package io.cvmaker.api.dto.request;

import io.cvmaker.api.model.*;

import java.util.List;

public class CVRequest {
    private String cvName;
    private String summary;

    private PersonalDetailsRequest personalDetails;
    private List<EducationRequest> educations;
    private List<ExperienceRequest> experiences;
    private List<SkillRequest> skills;
    private List<LanguageRequest> languages;
}

