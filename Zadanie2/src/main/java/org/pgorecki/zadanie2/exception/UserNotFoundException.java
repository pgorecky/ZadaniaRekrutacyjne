package org.pgorecki.zadanie2.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(String.format("User with id: %s not found", userId));
    }

}