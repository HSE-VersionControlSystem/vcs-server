package ru.hse.vcsserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.vcsserver.service.FilesSender;

@Slf4j
@Service
public class FileSenderImpl implements FilesSender {
    @Override
    public boolean sendFiles(String directoryName) {
        return false;
    }
}
