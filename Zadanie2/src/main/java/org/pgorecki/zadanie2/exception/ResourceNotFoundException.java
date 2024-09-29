package org.pgorecki.zadanie2.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long userId) {
        super(String.format("%s with id: %s not found", resource, userId));
    }

}