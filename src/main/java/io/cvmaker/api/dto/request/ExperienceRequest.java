package io.cvmaker.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceRequest {
    private String company;
    private String position;
    private String Location;
    private int startYear;
    private int endYear;
    private List<String> achievements;
    private Boolean isCurrent;
}
