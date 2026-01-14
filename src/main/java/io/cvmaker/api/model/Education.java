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
public class Education {
    String institution;
    String degree;
    List<String> achievements;
    String Location;
    LocalDate startYear;
    LocalDate endYear;
}
