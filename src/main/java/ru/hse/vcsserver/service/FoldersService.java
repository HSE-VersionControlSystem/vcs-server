package ru.hse.vcsserver.service;

import ru.hse.vcsserver.model.dto.RepositoriesListDto;

public interface FoldersService {

    /**
     * Finds all folders-repositories names.
     *
     * @return List of folder names.
     */
    RepositoriesListDto getAllFoldersNames();
}
