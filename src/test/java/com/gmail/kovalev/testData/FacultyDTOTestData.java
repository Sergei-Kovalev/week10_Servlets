package com.gmail.kovalev.testData;

import com.gmail.kovalev.dto.FacultyDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class FacultyDTOTestData {

    @Builder.Default
    String name = "Astrology";

    @Builder.Default
    String teacher = "Nostromo Actus Premi";

    @Builder.Default
    String email = "nostro@somewhere.com";

    @Builder.Default
    Integer actualVisitors = 19;

    @Builder.Default
    Integer maxVisitors = 25;

    @Builder.Default
    Double pricePerDay = 9.99;

    public FacultyDTO buildFacultyDTO() {
        return new FacultyDTO(name, teacher, email, actualVisitors, maxVisitors, pricePerDay);
    }
}
