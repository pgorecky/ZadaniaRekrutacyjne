package org.pgorecki.zadanie2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.pgorecki.zadanie2.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSearchDao {

    private final EntityManager entityManager;

    public List<User> findByCriteria(List<SearchCriteria> criteriaParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();

        SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);

        criteriaParams.forEach(searchCriteriaConsumer);

        predicate = searchCriteriaConsumer.getPredicate();
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
