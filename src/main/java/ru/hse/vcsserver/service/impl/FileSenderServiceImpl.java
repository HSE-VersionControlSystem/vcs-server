package ru.hse.vcsserver.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.constants.Errors;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.exception.DirectoryNotFoundException;
import ru.hse.vcsserver.service.FilesSenderService;

@Slf4j
@Service
public class FileSenderServiceImpl implements FilesSenderService {

    @Override
    public MultiValueMap<String, Object> sendFiles(String directoryName)
            throws NotDirectoryException {
        log.info(Messages.SENDING_FILES);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        File folder = new File(directoryName);
        if (!folder.exists()) {
            throw new DirectoryNotFoundException(Errors.FOLDER_NOT_FOUND);
        }

        if (folder.isFile()) {
            throw new NotDirectoryException(Errors.PATH_IS_NOT_DIRECTORY);
        }

        formData.add("directoryName", directoryName);

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

        formData.add("files", files);

        log.info(Messages.SENT_FILES);

        return formData;
    }
}
