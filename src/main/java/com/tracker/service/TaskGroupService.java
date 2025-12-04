package com.tracker.service;

import com.tracker.model.TaskGroup;
import com.tracker.model.User;
import com.tracker.repository.TaskGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;

    public List<TaskGroup> getGroupsByUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user id is null");
        }
        return taskGroupRepository.findByUserId(user.getId());
    }

    public TaskGroup createGroup(String name, User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user id is null");
        }
        TaskGroup group = TaskGroup.builder()
                .name(name)
                .user(user)
                .build();
        return taskGroupRepository.save(group);
    }

    public TaskGroup updateGroup(Long id, String name, User user) {
        TaskGroup group = taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        if (!group.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to update this group");
        }

        group.setName(name);
        return taskGroupRepository.save(group);
    }

    public void deleteGroup(Long id, User user) {
        TaskGroup group = taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        if (!group.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to delete this group");
        }

        taskGroupRepository.delete(group);
    }
}
