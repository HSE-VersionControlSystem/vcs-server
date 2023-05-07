package ru.hse.vcsserver.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.hse.vcsserver.constants.Errors;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.exception.DirectoryNotFoundException;
import ru.hse.vcsserver.service.FilesSenderService;

@Slf4j
@Service
public class FileSenderServiceImpl implements FilesSenderService {

    private final String directoriesNamesKey = "directoriesNames";

    @Override
    public MultiValueMap<String, Object> sendFiles(String directoryName)
            throws NotDirectoryException {
        log.info(Messages.SENDING_FILES);

        List<String> directoriesNames = new LinkedList<>();
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        foldersTraverse(directoryName, formData);

        log.info(Messages.SENT_FILES);
        return formData;
    }

    private void foldersTraverse(String rootDirectory, MultiValueMap<String, Object> formData)
            throws NotDirectoryException {
        formData.add(rootDirectory, getAllFilesInDirectory(rootDirectory));

        File rootFolder = new File(rootDirectory);
        File[] folders = rootFolder.listFiles(File::isDirectory);
        if (folders == null) {
            return;
        }

        for (final File folder : folders) {
            foldersTraverse(
                    rootDirectory + FileSystems.getDefault().getSeparator() + folder.getName(),
                    formData);
        }
    }

    private List<ByteArrayResource> getAllFilesInDirectory(String directoryName)
            throws NotDirectoryException {
        File folder = new File(directoryName);
        if (!folder.exists()) {
            throw new DirectoryNotFoundException(Errors.FOLDER_NOT_FOUND);
        }

        if (folder.isFile()) {
            throw new NotDirectoryException(Errors.PATH_IS_NOT_DIRECTORY);
        }

        File[] filesInFolder = folder.listFiles(File::isFile);
        if (filesInFolder == null) {
            filesInFolder = new File[0];
        }

        List<ByteArrayResource> files = new LinkedList<>();

        for (final File file : filesInFolder) {
            try {
                files.add(new ByteArrayResource(Files.readAllBytes(Path.of(file.getPath()))));
            } catch (final IOException exception) {
                log.error(exception.getMessage());
            }
        }

        return files;
    }
}
