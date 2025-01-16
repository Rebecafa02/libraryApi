package io.github.rebecafa.libraryapi.repository;

import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */


public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    //JPQL -> referencia as entidades e as propriedades
    // select l. * from livro as l order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco" )
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a. *
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query("""
    select l.genero
    from Livro l
    join l.autor a
    where a.nacionalidade = 'norte-americano'
    order by l.genero
    """)
    List<String> listarGenerosAutoresNorteAmericano();


    //usando @query para sobrescrever um query method
    @Query("select l from Livro l where l.genero = :nomeDoParametro")
    List<Livro> findByGenero(@Param("nomeDoParametro")GeneroLivro generoLivro);

    @Transactional//para abrir uma transação, diferente de situações de pesquisa
    @Modifying//para operações de escrita no jpa
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void upadteDataPublicacao(LocalDate novaData );

    boolean existsByAutor(Autor autor);

}
