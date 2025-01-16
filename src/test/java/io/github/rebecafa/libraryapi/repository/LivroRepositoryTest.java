package io.github.rebecafa.libraryapi.repository;

import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setTitulo("Noite de verão");
        livro.setIsbn("85122346997");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setPreco(BigDecimal.valueOf(40));
        livro.setDataPublicacao(LocalDate.of(2015, 10, 12));

        Autor autor = autorRepository
                .findById(UUID.fromString("efa8dc1d-75eb-478c-a499-4623dc0333cf"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){ //salva o autor ao salvar o livro, eles ficam relacionados em cascata, é perigoso porque vão está sempre relacionados
        //se deletar esse livro vai deletar o autor também
        Livro livro = new Livro();
        livro.setTitulo("Dandara dos Palmares");
        livro.setIsbn("985214763");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(75));
        livro.setDataPublicacao(LocalDate.of(2000, 11, 12));

        Autor autor = new Autor();
        autor.setNome("Lucy");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1990, 7, 9));


        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id =   UUID.fromString("be268e0b-74ec-484c-8f16-d22f1edeb24a");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("0243e095-26e1-4620-9ee5-6d8bcef19f26");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("251d7eb6-6ca2-4d5c-aeef-24f7e7035ac1");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("be268e0b-74ec-484c-8f16-d22f1edeb24a");
        repository.deleteById(id);
    }

    @Test
    void buscarLivroTest(){
        UUID id = UUID.fromString("77ab2136-9e82-4d44-8b9a-b9a07a503d47");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
       //select from livro where titulo = "percy"
        List<Livro> lista = repository.findByTitulo("Percy Jackson - O ladrao de raios");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTest(){
        List<Livro> lista = repository.findByIsbn("9521776210");
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeAutoresNorteAmericanos(){
        var resultado = repository.listarGenerosAutoresNorteAmericano();
        resultado.forEach(System.out::println);
    }


    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.BIOGRAFIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        repository.upadteDataPublicacao(LocalDate.of(2000, 1, 1));
    }

}