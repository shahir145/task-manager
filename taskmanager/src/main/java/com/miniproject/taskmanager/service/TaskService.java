package com.miniproject.taskmanager.service;

import com.miniproject.taskmanager.dto.CreateTaskRequest;
import com.miniproject.taskmanager.exceptions.TaskNotFoundException;
import com.miniproject.taskmanager.model.Priority;
import com.miniproject.taskmanager.model.Status;
import com.miniproject.taskmanager.model.Task;
import com.miniproject.taskmanager.model.User;
import com.miniproject.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(User u, CreateTaskRequest req) {
        Task t = new Task();
        t.setTitle(req.title());
        t.setDescription(req.description());
        t.setStatus(req.status());
        t.setPriority(req.priority());
        t.setDueAt(req.dueAt());
        t.setUser(u);
        return taskRepository.save(t);
    }

    public List<Task> getAllTasksForUser(User u) {
        return u.getTasks();
    }

    public Task getTaskById(UUID id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id.toString()));
    }

    public Task updateTitle(Task t, String title) {
        t.setTitle(title);
        return taskRepository.save(t);
    }

    public Task updateDescription(Task t, String description) {
        t.setDescription(description);
        return taskRepository.save(t);
    }

    public Task updateStatus(Task t, Status status) {
        t.setStatus(status);
        return taskRepository.save(t);
    }

    public Task updatePriority(Task t, Priority priority) {
        t.setPriority(priority);
        return taskRepository.save(t);
    }

    public Task updateDueAt(Task t, LocalDateTime dueAt) {
        t.setDueAt(dueAt);
        return taskRepository.save(t);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}