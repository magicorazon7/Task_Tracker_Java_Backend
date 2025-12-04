package com.tracker.repository;

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

    // пример изменения в TaskRepository
    @Query("SELECT t.status as status, COUNT(t) as count FROM Task t WHERE t.user.id = :userId GROUP BY t.status")
    List<Map<String, Object>> getTaskStatisticsByStatus(Long userId);

    @Query("SELECT COALESCE(g.name, 'Без группы') as groupName, COUNT(t) as count " +
            "FROM Task t LEFT JOIN t.group g WHERE t.user.id = :userId GROUP BY g.name")
    List<Map<String, Object>> getTaskStatisticsByGroup(Long userId);
}