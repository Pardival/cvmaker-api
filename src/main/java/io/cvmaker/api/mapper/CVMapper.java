package io.cvmaker.api.mapper;

import io.cvmaker.api.dto.request.*;
import io.cvmaker.api.dto.response.*;
import io.cvmaker.api.model.*;
import org.mapstruct.Mapper;

@Mapper
public interface CVMapper {
    CV toEntity(CVRequest cvRequest);
    CVResponse toResponse(CV cv);

    Education toEntity(EducationRequest educationRequest);
    EducationResponse toResponse(Education education);

    Experience toEntity(ExperienceRequest experienceRequest);
    ExperienceResponse toResponse(Experience experience);

    Language toEntity(LanguageRequest languageRequest);
    LanguageResponse toResponse(Language language);

    PersonalDetails toEntity(PersonalDetailsRequest personalDetailsRequest);
    PersonalDetailsResponse toResponse(PersonalDetails personalDetails);

    Skill toEntity(SkillRequest skillRequest);
    SkillResponse toResponse(Skill skill);
}
