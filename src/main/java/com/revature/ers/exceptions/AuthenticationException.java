package com.revature.ers.exceptions;

import java.util.function.Supplier;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("User authentication failed!");
    }

    public AuthenticationException(String message) {
        super(message);
    }

}
