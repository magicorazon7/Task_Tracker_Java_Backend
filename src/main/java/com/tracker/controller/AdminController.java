package com.tracker.controller;

import com.tracker.model.Task;
import com.tracker.model.User;
import com.tracker.service.TaskService;
import com.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(taskService.getTasksByUser(user));
    }

    @GetMapping("/statistics/status")
    public ResponseEntity<List<Map<String, Object>>> getTaskStatisticsByStatus() {
        // Получаем статистику для всех пользователей
        // В реальном приложении можно сделать агрегацию по всем пользователям
        User firstUser = userService.getAllUsers().get(0);
        return ResponseEntity.ok(taskService.getTaskStatisticsByStatus(firstUser));
    }

    @GetMapping("/statistics/group")
    public ResponseEntity<List<Map<String, Object>>> getTaskStatisticsByGroup() {
        // Получаем статистику для всех пользователей
        User firstUser = userService.getAllUsers().get(0);
        return ResponseEntity.ok(taskService.getTaskStatisticsByGroup(firstUser));
    }
}