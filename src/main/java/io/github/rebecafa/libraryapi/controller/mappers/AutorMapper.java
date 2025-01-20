package io.github.rebecafa.libraryapi.controller.mappers;

import io.github.rebecafa.libraryapi.controller.dto.AutorDTO;
import io.github.rebecafa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    //@Mapping(source = "nome", target = "nomeAutor") para quando o nome da entidade não é o mesmo no dto
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
