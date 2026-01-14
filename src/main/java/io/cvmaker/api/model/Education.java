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
public class Education {
    private String institution;
    private String degree;
    private List<String> achievements;
    private String Location;
    private int startYear;
    private int endYear;
}
