package io.github.rebecafa.libraryapi.service;

import io.github.rebecafa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.repository.AutorRepository;
import io.github.rebecafa.libraryapi.repository.LivroRepository;
import io.github.rebecafa.libraryapi.validador.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
       if(possuiLivro(autor)){
           throw new OperacaoNaoPermitidaException(
                   "Não é permitido excluir um Autor que possui livros cadastrados!");
       }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor ja esteja salvo na base de dados!");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
