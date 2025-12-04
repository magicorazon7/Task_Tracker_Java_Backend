package com.tracker.controller;

import com.tracker.model.TaskGroup;
import com.tracker.model.User;
import com.tracker.service.TaskGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Получить группы пользователя")
    public ResponseEntity<List<TaskGroup>> getAllGroups(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.getGroupsByUser(user));
    }

    @PostMapping // создать новую группу
    @Operation(summary = "Создать группу")
    public ResponseEntity<TaskGroup> createGroup(
            @RequestParam String name,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.createGroup(name, user));
    }

    @PutMapping("/{id}") // изменить имя существующей группы
    @Operation(summary = "Редактировать группу")
    public ResponseEntity<TaskGroup> updateGroup(
            @PathVariable Long id,
            @RequestParam String name,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskGroupService.updateGroup(id, name, user));
    }

    @DeleteMapping("/{id}") // удалить группу
    @Operation(summary = "Удалить группу")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Удалено"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
            @ApiResponse(responseCode = "403", description = "Нет доступа") // описание с респонсами вместо 204
    })
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        taskGroupService.deleteGroup(id, user);
        return ResponseEntity.noContent().build();
    }
}