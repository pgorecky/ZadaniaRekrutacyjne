package org.pgorecki.zadanie2.repository;

import org.pgorecki.zadanie2.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
