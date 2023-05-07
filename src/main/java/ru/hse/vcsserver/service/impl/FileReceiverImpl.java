package ru.hse.vcsserver.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.service.FilesReceiver;

@Slf4j
@Service
public class FileReceiverImpl implements FilesReceiver {

    private final String splitterRegex = "[\\\\/]";

    @Override
    public void saveFiles(String directoryName, List<MultipartFile> files) {
        log.info(Messages.RECEIVING_FILES + " for directory " + directoryName);

        final String[] directories = directoryName.split(splitterRegex);
        String fullPath = String.join(FileSystems.getDefault().getSeparator(),
                                      directoryName.split(splitterRegex));

        createFoldersIfNotExists(directories);

        for (final MultipartFile file : files) {
            try {
                Path filePath = Path.of(fullPath, file.getOriginalFilename());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
                Files.copy(file.getInputStream(), filePath);
            } catch (final IOException exception) {
                log.error(exception.getMessage());
            }
        }

        log.info(Messages.RECEIVED_FILES);
    }

    private synchronized void createFoldersIfNotExists(final String[] directories) {
        log.info(Messages.CREATING_FOLDERS);

        StringBuilder folder = new StringBuilder();
        for (final String directory : directories) {
            folder.append(directory);
            Path path = Path.of(folder.toString());
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (final IOException exception) {
                    log.error(exception.getClass().getName() + "\t" + exception.getMessage());
                }
            }

            folder.append(FileSystems.getDefault().getSeparator());
        }
    }
}
