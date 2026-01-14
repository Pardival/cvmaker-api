package io.cvmaker.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    private String company;
    private String position;
    private String Location;
    private int startYear;
    private int endYear;
    private List<String> achievements;
    private Boolean isCurrent;
}
