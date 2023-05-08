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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import ru.hse.vcsserver.constants.Errors;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.exception.DirectoryNotFoundException;
import ru.hse.vcsserver.model.dto.FileDto;
import ru.hse.vcsserver.service.FilesSenderService;

@Slf4j
@Service
public class FileSenderServiceImpl implements FilesSenderService {

    @Override
    public List<FileDto> sendFiles(String directoryName) throws NotDirectoryException {
        log.info(Messages.SENDING_FILES);

        List<FileDto> files = new LinkedList<>();

        foldersTraverse(directoryName, files);

        log.info(Messages.SENT_FILES);

        return files;
    }

    private void foldersTraverse(String rootDirectory, List<FileDto> files)
            throws NotDirectoryException {
        traverseAllFilesInDirectory(rootDirectory, files);

        File rootFolder = new File(rootDirectory);
        File[] folders = rootFolder.listFiles(File::isDirectory);
        if (folders == null) {
            return;
        }

        for (final File folder : folders) {
            String directoryName =
                    rootDirectory + FileSystems.getDefault().getSeparator() + folder.getName();
            foldersTraverse(directoryName, files);
        }
    }

    private void traverseAllFilesInDirectory(String directoryName, List<FileDto> files)
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

        for (final File file : filesInFolder) {
            try {
                String fullPath =
                        directoryName + FileSystems.getDefault().getSeparator() + file.getName();
                files.add(new FileDto(fullPath, ArrayUtils.toObject(
                        Files.readAllBytes(Path.of(file.getPath())))));
            } catch (final IOException exception) {
                log.error(exception.getMessage());
            }
        }
    }
}
