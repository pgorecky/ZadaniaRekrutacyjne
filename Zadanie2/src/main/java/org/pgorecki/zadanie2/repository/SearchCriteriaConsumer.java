package org.pgorecki.zadanie2.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.function.Consumer;

@AllArgsConstructor
@Getter
@Setter
public class SearchCriteriaConsumer implements Consumer<SearchCriteria> {
    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root<?> r;
    @Override
    public void accept(SearchCriteria searchCriteria) {
        if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
            if (r.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(
                        r.get(searchCriteria.getKey()), (LocalDate) searchCriteria.getValue()));
            } else {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(
                        r.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
            }
        } else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            if (r.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                        r.get(searchCriteria.getKey()), (LocalDate) searchCriteria.getValue()));
            } else {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                        r.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
            }
        } else if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
            if (r.get(searchCriteria.getKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(
                        builder.lower(r.get(searchCriteria.getKey())),
                        "%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
            } else if (r.get(searchCriteria.getKey()).getJavaType() == LocalDate.class) {
                predicate = builder.and(predicate, builder.equal(
                        r.get(searchCriteria.getKey()), searchCriteria.getValue()));
            } else {
                predicate = builder.and(predicate, builder.equal(
                        r.get(searchCriteria.getKey()), searchCriteria.getValue()));
            }
        }
    }
}
