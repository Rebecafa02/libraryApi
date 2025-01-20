package io.github.rebecafa.libraryapi.controller;

import io.github.rebecafa.libraryapi.controller.dto.AutorDTO;
import io.github.rebecafa.libraryapi.controller.dto.ErroRespostaDTO;
import io.github.rebecafa.libraryapi.controller.mappers.AutorMapper;
import io.github.rebecafa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.rebecafa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.rebecafa.libraryapi.model.Autor;
import io.github.rebecafa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody @Valid AutorDTO dto){
            Autor autor = mapper.toEntity(dto);
            service.salvar(autor);

            URI location = gerarHeaderLocation(autor.getId());
                    /*ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();*/

            return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){

        var idAutor = UUID.fromString(id);
        //Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());

        /*if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();*/
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        //try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
       // } catch (OperacaoNaoPermitidaException e){
         //   var erroDTO = ErroRespostaDTO.respostaPadrao(e.getMessage()); //é o que pede no contrato
        //    return ResponseEntity.status(erroDTO.status()).body(erroDTO);
       // }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, //não faz sentido utilizar o mapper pq ele iria sobrescrever alguns parametros
             @RequestBody @Valid AutorDTO dto){

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }

}
