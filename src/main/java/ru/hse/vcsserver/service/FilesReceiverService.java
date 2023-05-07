package ru.hse.vcsserver.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FilesReceiverService {

    /**
     * Saves files in theirs directory.
     *
     * @param directoryName Directory's name.
     * @param files         Files.
     */
    void saveFiles(String directoryName, List<MultipartFile> files);
}
