package io.cvmaker.api.dto.response;

import io.cvmaker.api.model.*;

import java.util.List;

public class CVResponse {
    private String id;
    private String cvName;
    private String summary;

    private PersonalDetails personalDetails;
    private List<EducationResponse> educations;
    private List<ExperienceResponse> experiences;
    private List<SkillResponse> skills;
    private List<LanguageResponse> languages;
}
