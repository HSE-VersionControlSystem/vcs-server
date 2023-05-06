package ru.hse.vcsserver.controller;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.service.FilesReceiver;
import ru.hse.vcsserver.service.FilesSender;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("files")
public class FilesController {

    private final FilesReceiver filesReceiver;
    private final FilesSender filesSender;

    @PostMapping("push")
    public void receiveFiles(@RequestPart("directory_name") String directoryName,
                             @RequestPart("files") List<MultipartFile> files) {
        filesReceiver.saveFiles(directoryName, files);
    }

    @PostMapping("pull/{folderName}")
    public void sendFiles(@PathVariable String folderName) {

    }
}
