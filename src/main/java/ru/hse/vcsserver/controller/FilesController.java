package ru.hse.vcsserver.controller;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hse.vcsserver.service.FilesReceiverService;
import ru.hse.vcsserver.service.FilesSenderService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("files")
public class FilesController {

    private final FilesReceiverService filesReceiver;
    private final FilesSenderService filesSender;

    @PostMapping("push")
    public ResponseEntity<Void> receiveFiles(@RequestPart("directory_name") String directoryName,
                                             @RequestPart("files") List<MultipartFile> files) {
        filesReceiver.saveFiles(directoryName, files);
        return ResponseEntity.ok().build();
    }

    @GetMapping("pull/{folderName}")
    public ResponseEntity<Void> sendFiles(@PathVariable String folderName)
            throws NotDirectoryException {
        filesSender.sendFiles(folderName);
        return ResponseEntity.ok().build();
    }
}
