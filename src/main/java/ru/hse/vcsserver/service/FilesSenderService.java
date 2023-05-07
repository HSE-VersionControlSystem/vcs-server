package ru.hse.vcsserver.service;

import java.nio.file.NotDirectoryException;
import org.springframework.util.MultiValueMap;

public interface FilesSenderService {

    /**
     * Sends files from a specified directory.
     *
     * @param directoryName Directory's name.
     * @return form/data
     *
     * @throws NotDirectoryException Throws exceptions if file exists but is a directory.
     */
    MultiValueMap<String, Object> sendFiles(String directoryName) throws NotDirectoryException;
}
