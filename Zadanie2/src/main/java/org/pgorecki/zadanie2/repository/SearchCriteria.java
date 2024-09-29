package org.pgorecki.zadanie2.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
