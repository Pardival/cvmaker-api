package io.cvmaker.api.dto.response;

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
public class CVResponse {
    private String id;
    private String cvName;
    private String summary;

    private PersonalDetailsResponse personalDetails;
    private List<EducationResponse> educations;
    private List<ExperienceResponse> experiences;
    private List<SkillResponse> skills;
    private List<LanguageResponse> languages;
}
