package io.github.rebecafa.libraryapi.service;

import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import io.github.rebecafa.libraryapi.repository.AutorRepository;
import io.github.rebecafa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional //dessa forma as alterações feitas aqui são enviadas para o banco de dados, sem a necessidade de chamar o metodo save,
    //as alterações na entidade managed são enviadas para o bd
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("285fa061-6af5-4fac-8a86-d86aff6afc3e"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2017, 6, 24));


    }
    @Transactional //so funciona em metodos publicos
    public void executar(){
        //Salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisca");
        autor.setNacionalidade("canadense");
        autor.setDataNascimento(LocalDate.of(1975, 12, 15));

        autorRepository.saveAndFlush(autor); //é possivel visualizar o sistema realizando as operações

        //salva o livro
        Livro livro = new Livro();
        livro.setTitulo("Livro da Francisca");
        livro.setIsbn("9788584390274");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setPreco(BigDecimal.valueOf(29));
        livro.setDataPublicacao(LocalDate.of(2015, 11, 30));

        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro);

        if(autor.getNome().equals("Teste Francisca")){
            throw new RuntimeException("Rollback");
        }
    }
}
