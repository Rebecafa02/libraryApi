package io.github.rebecafa.libraryapi.service;

import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import io.github.rebecafa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.rebecafa.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){
        //where isbn = :isbn

        Specification<Livro> specs = Specification.where(((root, query, cb) -> cb.conjunction() ));

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        return repository.findAll(specs);

    }
}
