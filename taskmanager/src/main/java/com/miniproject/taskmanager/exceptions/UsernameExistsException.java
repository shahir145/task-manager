package com.miniproject.taskmanager.exceptions;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String username) {
        super("Username "+username+" already exists" );
    }
}