package org.pgorecki.zadanie2.repository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSearchRequest {
    private String firstName;
    private String lastName;
    private String email;

    public List<SearchCriteria> getCriteriaParams() {
        List<SearchCriteria> params = new ArrayList<>();

        if (this.firstName != null) {
            params.add(new SearchCriteria("firstName", ":", firstName));
        }

        if (this.lastName != null) {
            params.add(new SearchCriteria("lastName", ":", lastName));
        }

        if (this.email != null) {
            params.add(new SearchCriteria("email", ":", email));
        }

        return params;
    }
}
