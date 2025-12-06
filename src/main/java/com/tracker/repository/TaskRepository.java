package com.tracker.repository;

import com.tracker.dto.GroupCount;
import com.tracker.dto.StatCount;
import com.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndGroupId(Long userId, Long groupId);

    @Query("""
    SELECT NEW com.tracker.dto.StatCount(t.status, COUNT(t))
    FROM Task t
    GROUP BY t.status
""")
    List<StatCount> getTaskStatisticsByStatusForAllUsers();

    @Query("""
    SELECT NEW com.tracker.dto.GroupCount(
        COALESCE(g.name, 'Без группы'), 
        COUNT(t)
    )
    FROM Task t
    LEFT JOIN t.group g
    GROUP BY g.name
""")
    List<GroupCount> getTaskStatisticsByGroupForAllUsers();

}