package org.pgorecki.zadanie2.dto;

import org.pgorecki.zadanie2.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public record AddTaskRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDate deadline,
        List<Long> assignedUsers) {

}
