package com.nikitatunkel.eff_mob.repository;


import com.nikitatunkel.eff_mob.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorUsername(String username);
    List<Task> findByAssigneeUsername(String username);
}
