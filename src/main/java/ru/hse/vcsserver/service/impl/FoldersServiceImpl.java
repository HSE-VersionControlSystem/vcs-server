package ru.hse.vcsserver.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import ru.hse.vcsserver.model.dto.RepositoriesListDto;
import ru.hse.vcsserver.service.FoldersService;

@Service
public class FoldersServiceImpl implements FoldersService {

    private final Set<String> systemFolders = new HashSet<>(
            Arrays.asList("out", ".idea", ".git", "src", ".mvn", "target", "mvnw", "mvnw.cmd",
                          "pom.xml"));

    @Override
    public RepositoriesListDto getAllFoldersNames() {
        List<String> foldersNames;

        File[] folders = new File(".").listFiles(File::isDirectory);
        if (folders == null) {
            return new RepositoriesListDto(new LinkedList<>());
        }

        foldersNames = new LinkedList<>();

        for (File folder : folders) {
            if (!systemFolders.contains(folder.getName())) {
                foldersNames.add(folder.getName());
            }
        }

        return new RepositoriesListDto(foldersNames);
    }
}
