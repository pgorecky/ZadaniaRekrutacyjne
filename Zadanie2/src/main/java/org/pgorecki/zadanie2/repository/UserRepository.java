package org.pgorecki.zadanie2.repository;

import org.pgorecki.zadanie2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
