package org.pgorecki.zadanie2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pgorecki.zadanie2.model.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;
    private List<UserDto> assignedUsers = new ArrayList<>();

}
