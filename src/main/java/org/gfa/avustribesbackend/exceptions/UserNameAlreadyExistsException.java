package org.gfa.avustribesbackend.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException{

    public UserNameAlreadyExistsException() {
        super("Username is already taken");
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
