package org.pgorecki.zadanie2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.pgorecki.zadanie2.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskSearchDao {

    private final EntityManager entityManager;

    public List<Task> findByCriteria(List<SearchCriteria> criteriaParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);

        Root<Task> root = criteriaQuery.from(Task.class);

        Predicate predicate = criteriaBuilder.conjunction();

        SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);

        criteriaParams.forEach(searchCriteriaConsumer);

        predicate = searchCriteriaConsumer.getPredicate();
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
