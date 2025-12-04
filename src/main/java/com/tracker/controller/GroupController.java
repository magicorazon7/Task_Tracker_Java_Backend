package com.tracker.controller;

import com.tracker.model.TaskGroup;
import com.tracker.model.User;
import com.tracker.service.TaskGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final TaskGroupService taskGroupService;

    @GetMapping // получение всех групп пользователя
    public ResponseEntity<List<TaskGroup>> getAllGroups(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.getGroupsByUser(user));
    }

    @PostMapping // создать новую группу
    public ResponseEntity<TaskGroup> createGroup(
            @RequestParam String name,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.createGroup(name, user));
    }

    @PutMapping("/{id}") // изменить имя существующей группы
    public ResponseEntity<TaskGroup> updateGroup(
            @PathVariable Long id,
            @RequestParam String name,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.updateGroup(id, name, user));
    }

    @DeleteMapping("/{id}") // удалить группу
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        taskGroupService.deleteGroup(id, user);
        return ResponseEntity.noContent().build();
    }
}