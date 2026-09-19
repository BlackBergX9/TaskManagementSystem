package org.blackbergx9.taskmanagementsystem.repository;

import org.blackbergx9.taskmanagementsystem.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTitleContainingIgnoreCase(String search, Pageable pageable);
}







// ignore....
 /* Select *
    from user
    join task
    on user.id == task.assigne_id
    where task.title like "customer %"
 * */
