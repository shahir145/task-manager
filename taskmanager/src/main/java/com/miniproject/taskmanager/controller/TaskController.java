package com.miniproject.taskmanager.controller;

import com.miniproject.taskmanager.dto.*;
import com.miniproject.taskmanager.model.Task;
import com.miniproject.taskmanager.model.User;
import com.miniproject.taskmanager.security.JwtUtil;
import com.miniproject.taskmanager.service.TaskService;
import com.miniproject.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public TaskController(TaskService taskService, JwtUtil jwtUtil, UserService userService) {
        this.taskService = taskService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Task> create(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody CreateTaskRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                taskService.createTask(currentUser, req));
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getTasks(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        return ResponseEntity.ok(currentUser.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/title")
    public ResponseEntity<Task> updateTitle(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable UUID id, @RequestBody UpdateTitleRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(taskService.updateTitle(task, req.title()));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<Task> updateDescription(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable UUID id, @RequestBody UpdateDescriptionRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(taskService.updateDescription(task, req.description()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@RequestHeader("Authorization") String authHeader,
                                             @PathVariable UUID id, @RequestBody UpdateStatusRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(taskService.updateStatus(task, req.status()));
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Task> updatePriority(@RequestHeader("Authorization") String authHeader,
                                             @PathVariable UUID id, @RequestBody UpdatePriorityRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(taskService.updatePriority(task, req.priority()));
    }

    @PutMapping("/{id}/dueAt")
    public ResponseEntity<Task> updateDueAt(@RequestHeader("Authorization") String authHeader,
                                               @PathVariable UUID id, @RequestBody UpdateDueAtRequest req) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(taskService.updateDueAt(task, req.dueAt()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable UUID id) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User currentUser = userService.loadUserByUsername(username);
        Task task = taskService.getTaskById(id);
        if (!task.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}