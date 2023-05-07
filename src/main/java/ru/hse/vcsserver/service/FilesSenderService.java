package ru.hse.vcsserver.service;

import java.nio.file.NotDirectoryException;

public interface FilesSenderService {

    /**
     * Sends files from a specified directory.
     *
     * @param directoryName Directory's name.
     * @return true if all files sent and the response code is >= 200 && < 300, otherwise false.
     */
    boolean sendFiles(String directoryName) throws NotDirectoryException;
}
