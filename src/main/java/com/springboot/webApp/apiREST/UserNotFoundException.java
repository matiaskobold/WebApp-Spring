package com.springboot.webApp.apiREST;

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
