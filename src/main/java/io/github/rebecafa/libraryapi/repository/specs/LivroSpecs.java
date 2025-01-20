package io.github.rebecafa.libraryapi.repository.specs;

import io.github.rebecafa.libraryapi.model.GeneroLivro;
import io.github.rebecafa.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        //upper(livro.titulo) like (%:param%)
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")),
                "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        return (root, query, cb) ->
                cb.equal(root.get("genero"),
                        anoPublicacao.toString());
    }
}
