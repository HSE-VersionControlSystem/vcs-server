package ru.hse.vcsserver.service;

import java.nio.file.NotDirectoryException;
import java.util.List;
import ru.hse.vcsserver.model.dto.FileDto;

public interface FilesSenderService {

    /**
     * Sends files from a specified directory.
     *
     * @param directoryName Directory's name.
     * @return form/data
     *
     * @throws NotDirectoryException Throws exceptions if file exists but is a directory.
     */
    List<FileDto> sendFiles(String directoryName) throws NotDirectoryException;
}
