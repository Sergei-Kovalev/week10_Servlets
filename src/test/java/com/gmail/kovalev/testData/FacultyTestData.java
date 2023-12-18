package com.gmail.kovalev.testData;

import com.gmail.kovalev.entity.Faculty;
import lombok.Builder;

import java.util.UUID;

@Builder(setterPrefix = "with")
public class FacultyTestData {
    @Builder.Default
    private UUID id = UUID.fromString("773dcbc0-d2fa-45b4-acf8-485b682adedd");
    @Builder.Default
    private String name = "Geography";
    @Builder.Default
    private String teacher = "Ivanov Pert Sidorovich";
    @Builder.Default
    private String email = "geography@gmail.com";
    @Builder.Default
    private Integer actualVisitors = 7;
    @Builder.Default
    private Integer maxVisitors = 20;
    @Builder.Default
    private Double pricePerDay = 6.72;

    public Faculty buildFaculty() {
        return new Faculty(id, name, teacher, email, actualVisitors, maxVisitors, pricePerDay);
    }
}
