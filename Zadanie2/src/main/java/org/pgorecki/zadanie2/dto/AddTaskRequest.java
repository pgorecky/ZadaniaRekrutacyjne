package org.pgorecki.zadanie2.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import org.pgorecki.zadanie2.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public record AddTaskRequest(

        @NotBlank(message = "Title should not be blank")
        String title,

        @NotBlank(message = "Description should not be blank")
        String description,
        @Enumerated(EnumType.STRING)
        TaskStatus status,
        LocalDate deadline,
        List<Long> assignedUsers) {

}
