package ru.hse.vcsserver.controller.advices;

import java.nio.file.NotDirectoryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hse.vcsserver.constants.Errors;
import ru.hse.vcsserver.exception.DirectoryNotFoundException;

@RestControllerAdvice
public class ExceptionAdvices {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SecurityException.class)
    public String handleAccessRightException(SecurityException exception) {
        return Errors.MISSING_ACCESS_RIGHTS + "\n" + exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotDirectoryException.class)
    public String handleNotDirectoryException(NotDirectoryException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DirectoryNotFoundException.class)
    public String handleDirectoryNotFoundException(DirectoryNotFoundException exception) {
        return exception.getMessage();
    }
}
