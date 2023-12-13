package org.gfa.avustribesbackend.exceptions;

public class NewPlayerCreationException extends RuntimeException {

    public NewPlayerCreationException() {
        super("Unknown error");
    }

    public NewPlayerCreationException(String message) {
        super(message);
    }
}
