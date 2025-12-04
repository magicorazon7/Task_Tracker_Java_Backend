package com.tracker.service;

import com.tracker.model.Task;
import com.tracker.model.TaskGroup;
import com.tracker.model.TaskStatus;
import com.tracker.model.User;
import com.tracker.repository.TaskGroupRepository;
import com.tracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUserId(user.getId());
    }

    public List<Task> getTasksByGroup(User user, Long groupId) {
        TaskGroup group = taskGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        if (!group.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to access tasks in this group");
        }

        return taskRepository.findByUserIdAndGroupId(user.getId(), groupId);
    }

    public Task createTask(String title, String description, Long groupId, User user) {
        Task task = Task.builder()
                .title(title)
                .description(description)
                .status(TaskStatus.PLANNED)
                .user(user)
                .build();

        if (groupId != null) {
            TaskGroup group = taskGroupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
            task.setGroup(group);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, String title, String description, TaskStatus status, Long groupId, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to update this task");
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);

        if (groupId != null) {
            TaskGroup group = taskGroupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
            task.setGroup(group);
        } else {
            task.setGroup(null);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to delete this task");
        }

        taskRepository.delete(task);
    }

    public List<Map<String, Object>> getTaskStatisticsByStatus(User user) {
        return taskRepository.getTaskStatisticsByStatus(user.getId());
    }

    public List<Map<String, Object>> getTaskStatisticsByGroup(User user) {
        return taskRepository.getTaskStatisticsByGroup(user.getId());
    }

    public Task updateTaskStatus(Long id, TaskStatus status, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to update this task");
        }

        task.setStatus(status);
        return taskRepository.save(task);
    }
}
