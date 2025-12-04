package com.tracker.controller;

import com.tracker.model.Task;
import com.tracker.model.TaskStatus;
import com.tracker.model.User;
import com.tracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping // получить задачи пользователя
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTasksByUser(user));
    }

    @GetMapping("/group/{groupId}") // получить задачи группы
    public ResponseEntity<List<Task>> getTasksByGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTasksByGroup(user, groupId));
    }

    @PostMapping // создание задачм
    public ResponseEntity<Task> createTask(
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long groupId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.createTask(title, description, groupId, user));
    }

    @PutMapping("/{id}") // обновление задачи
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam TaskStatus status,
            @RequestParam(required = false) Long groupId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.updateTask(id, title, description, status, groupId, user));
    }

    @PutMapping("/{id}/status") // обновить статус задачи
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Удалено"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "Нет доступа") // описание с респонсами вместо 204
    })// удалить задачу
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }
}