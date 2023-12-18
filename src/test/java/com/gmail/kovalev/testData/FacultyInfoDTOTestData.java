package com.gmail.kovalev.testData;

import com.gmail.kovalev.dto.FacultyInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class FacultyInfoDTOTestData {
    @Builder.Default
    UUID id = UUID.fromString("18a9cc59-c7c7-47e2-ac77-d3127d3b2edf");

    @Builder.Default
    String name = "Astrology";

    @Builder.Default
    String teacher = "Nostromo Actus Premi";

    @Builder.Default
    String email = "nostro@somewhere.com";

    @Builder.Default
    Integer freePlaces = 15;

    @Builder.Default
    Double pricePerDay = 9.99;

    public FacultyInfoDTO buildFacultyInfoDTO() {
        return new FacultyInfoDTO(id, name, teacher, email, freePlaces, pricePerDay);
    }
}
