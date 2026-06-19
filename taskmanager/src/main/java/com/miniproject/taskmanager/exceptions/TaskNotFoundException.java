package com.miniproject.taskmanager.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String id) {
        super("Task "+id+" does not exist" );
    }
}