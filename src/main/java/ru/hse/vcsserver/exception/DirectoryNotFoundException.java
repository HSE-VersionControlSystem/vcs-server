package ru.hse.vcsserver.exception;

public class DirectoryNotFoundException extends RuntimeException {

    public DirectoryNotFoundException(String message) {
        super(message);
    }
}
