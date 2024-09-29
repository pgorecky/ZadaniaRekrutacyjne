package org.pgorecki.zadanie2.dto;

import lombok.AllArgsConstructor;
import org.pgorecki.zadanie2.model.TaskStatus;
import org.pgorecki.zadanie2.repository.SearchCriteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TaskSearchRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate deadlineFrom;
    private LocalDate deadlineTo;

    public List<SearchCriteria> getCriteriaParams() {
        List<SearchCriteria> params = new ArrayList<>();

        if (this.title != null) {
            params.add(new SearchCriteria("title", ":", title));
        }

        if (this.description != null) {
            params.add(new SearchCriteria("description", ":", description));
        }

        if (this.status != null) {
            params.add(new SearchCriteria("status", ":", status));
        }

        if (this.deadlineFrom != null) {
            params.add(new SearchCriteria("deadline", ">", deadlineFrom));
        }

        if (this.deadlineTo != null) {
            params.add(new SearchCriteria("deadline", "<", deadlineTo));
        }

        return params;
    }
}
