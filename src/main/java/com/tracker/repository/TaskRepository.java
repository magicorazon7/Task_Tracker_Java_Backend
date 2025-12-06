package com.tracker.repository;

import com.tracker.dto.GroupCount;
import com.tracker.dto.StatCount;
import com.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndGroupId(Long userId, Long groupId);

    @Query("""
        SELECT t.status AS status,
               COUNT(t) AS count
        FROM Task t
        WHERE t.user.id = :userId
        GROUP BY t.status
    """)
    List<StatCount> getTaskStatisticsByStatus(Long userId); // стата по статусам задач

    @Query("""
        SELECT COALESCE(g.name, 'Без группы') AS groupName,
               COUNT(t) AS count
        FROM Task t
        LEFT JOIN t.group g
        WHERE t.user.id = :userId
        GROUP BY g.name
    """)
    List<GroupCount> getTaskStatisticsByGroup(Long userId); // стата по группам
}