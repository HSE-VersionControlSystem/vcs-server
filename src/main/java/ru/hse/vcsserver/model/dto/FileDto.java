package ru.hse.vcsserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    @Getter @Setter private String name;
    @Getter @Setter private Byte[] file;
}
