package com.revature.ers.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("no resources found using the specified criteria.");
    }
}
