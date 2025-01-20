package io.github.rebecafa.libraryapi.controller.common;


import io.github.rebecafa.libraryapi.controller.dto.ErroCampoDTO;
import io.github.rebecafa.libraryapi.controller.dto.ErroRespostaDTO;
import io.github.rebecafa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.rebecafa.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //para quando não desejamos utilizar o response entity
    public ErroRespostaDTO handleMethodArgumentNotValidException( MethodArgumentNotValidException exception) {
       List<FieldError> fieldErrors = exception.getFieldErrors();
        List<ErroCampoDTO> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampoDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroRespostaDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros);

    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroRespostaDTO handleRegistroDuplicadoException( RegistroDuplicadoException e) {
        return ErroRespostaDTO.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handleOperacaoNaoPermitidaException( OperacaoNaoPermitidaException e) {
        return ErroRespostaDTO.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDTO handleErrosNaoTratadosException(RuntimeException e){
        return new ErroRespostaDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, entre em contato com a administração do sistema.",
                List.of());
    }
}
