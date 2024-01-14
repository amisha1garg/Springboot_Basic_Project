package com.school.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class StudentDto {

    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String studentName;

    @NotNull(message = "Father name is required")
    @Size(min = 3, max = 50, message = "Father name must be between 3 and 50 characters")
    private String fatherName;

    @NotNull(message = "Mother name is required")
    @Size(min = 3, max = 50, message = "Mother name must be between 3 and 50 characters")
    private String motherName;

    @NotNull(message = "Address is required")
    @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
    private String address;

    @NotNull(message = "Class name is required")
    @Pattern(regexp = "^([1-8])(st|nd|rd|th)$", message = "Class name must be between 1st and 8th")
    private String className;

    @NotNull(message = "Section is required")
    @Pattern(regexp = "^[A-C]$", message = "Section must be A, B, or C")
    private String section;

}
