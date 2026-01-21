package io.cvmaker.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cvs")
public class CV {
    @Id
    private String id;
    private String userId;
    private String cvName;
    private String summary;

    private PersonalDetails personalDetails;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Skill> skills;
    private List<Language> languages;
}
