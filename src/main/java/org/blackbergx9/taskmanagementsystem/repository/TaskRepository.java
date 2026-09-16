package org.blackbergx9.taskmanagementsystem.repository;

import org.blackbergx9.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
 /* Select *
    from user
    join task
    on user.id == task.assigne_id
    where task.title like "customer %"
 * */
