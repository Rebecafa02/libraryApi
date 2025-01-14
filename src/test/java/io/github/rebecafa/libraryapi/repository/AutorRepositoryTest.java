package io.github.rebecafa.libraryapi.repository;

import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Anna");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1990, 05, 15));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("c71e5f0e-9a20-47de-a2f5-4017d4567350");

        Optional<Autor> possivelAutor = repository.findById(id);
        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: \n" + autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1985, 05, 15));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTeste(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTeste(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("c71e5f0e-9a20-47de-a2f5-4017d4567350");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){ //deletar por objeto
        var id = UUID.fromString("2b133bed-72f6-4b8c-bd09-4ea79410ea39");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Rick Riordan");
        autor.setNacionalidade("norte-americano");
        autor.setDataNascimento(LocalDate.of(1964, 6, 5));

        Livro livro = new Livro();
        livro.setTitulo("Percy Jackson - O ladrao de raios");
        livro.setIsbn("9521776210");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(40));
        livro.setDataPublicacao(LocalDate.of(2005, 6, 28));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Percy Jackson - O mar de monstros");
        livro2.setIsbn("524613789");
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setPreco(BigDecimal.valueOf(35));
        livro2.setDataPublicacao(LocalDate.of(2006, 4, 1));
        livro2.setAutor(autor);
        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosPorAutor (){
        var id = UUID.fromString("4ebf6cd5-14a2-48b4-84c1-311e39481610");
        var autor = repository.findById(id).get();

        //buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);
        autor.getLivros().forEach(System.out::println);
    }


}
