package io.github.rebecafa.libraryapi.controller.dto;

import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autor
) {

}
