package ru.hse.vcsserver.service.impl;

import java.io.File;
import java.nio.file.NotDirectoryException;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean sendFiles(String directoryName) throws NotDirectoryException {
        log.info(Messages.SENDING_FILES);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        formData.add("directoryName", directoryName);

        File folder = new File(directoryName);
        if (!folder.exists()) {
            throw new DirectoryNotFoundException(Errors.FOLDER_NOT_FOUND);
        }

        if (folder.isFile()) {
            throw new NotDirectoryException(Errors.PATH_IS_NOT_DIRECTORY);
        }

        log.info(Messages.SENT_FILES);
        return true;
    }
}
