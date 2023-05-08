package ru.hse.vcsserver.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RepositoriesListDto {

    @Getter @Setter private List<String> repositoriesNames;
}
