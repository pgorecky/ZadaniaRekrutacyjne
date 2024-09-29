package org.pgorecki.zadanie2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.pgorecki.zadanie2.model.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AddTaskRequest {
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final LocalDate deadline;
    private final List<Long> assignedUsers = new ArrayList<>();
    
}
