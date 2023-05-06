package ru.hse.vcsserver.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.constants.Messages;
import ru.hse.vcsserver.service.FilesReceiver;

@Slf4j
@Service
public class FileReceiverImpl implements FilesReceiver {
    @Override
    public void saveFiles(String directoryName, List<MultipartFile> files) {
        log.info(Messages.RECEIVING_FILES + " for directory " + directoryName);
        //        for (MultipartFile file : files) {
        //            log.info("Saving " + file.getOriginalFilename() + "...");
        //        }
        log.info(Messages.RECEIVED_FILES);
    }
}
