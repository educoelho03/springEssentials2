package br.com.study.springEssentials.handler;

import br.com.study.springEssentials.exceptions.BadRequestException;
import br.com.study.springEssentials.exceptions.BadRequestExceptionDetails;
import br.com.study.springEssentials.exceptions.FieldMessage;
import br.com.study.springEssentials.exceptions.ValidationExceptionDetails;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice // Esta dizendo para todos os controllers que voce tem q usar oq vc tiver aqui dentro dessa classe
public class RestExceptionHandler {


    // Estamos falando para os controller que caso tenhamos uma exceção do tipo BAD REQUEST, vai utilizar esse EXCEPTION HANDLER, e vai retornar oq tem aqui
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException exception){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<FieldMessage> fieldMessages = fieldErrors.stream().map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .fieldErrors(fieldMessages)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
