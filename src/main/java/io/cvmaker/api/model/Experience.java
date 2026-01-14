package io.cvmaker.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    String company;
    String position;
    String Location;
    int startYear;
    int endYear;
    List<String> achievements;
    Boolean isCurrent;
}
