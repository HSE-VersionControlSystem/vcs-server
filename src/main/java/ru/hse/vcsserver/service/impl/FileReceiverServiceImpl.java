package ru.hse.vcsserver.service.impl;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.constants.Errors;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.service.FilesReceiverService;

@Slf4j
@Service
public class FileReceiverServiceImpl implements FilesReceiverService {

    private final String splitterRegex = "[\\\\/]";

    @Override
    public void saveFiles(String directoryName, List<MultipartFile> files) {
        log.info(Messages.RECEIVING_FILES + " for directory " + directoryName);

        final String[] directories = directoryName.split(splitterRegex);
        String fullPath = String.join(FileSystems.getDefault().getSeparator(), directories);

        createFoldersIfNotExists(directories);

        for (final MultipartFile file : files) {
            try {
                Path filePath = Path.of(fullPath, file.getOriginalFilename());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
                Files.copy(file.getInputStream(), filePath);
                file.getInputStream().close();
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
                } catch (final FileAlreadyExistsException exception) {
                    log.info(Errors.FILE_ALREADY_EXISTS);
                } catch (final SecurityException securityException) {
                    throw securityException;
                } catch (final Exception exception) {
                    log.error(exception.getClass().getName() + "\t" + exception.getMessage());
                }
            }

            folder.append(FileSystems.getDefault().getSeparator());
        }
    }
}
