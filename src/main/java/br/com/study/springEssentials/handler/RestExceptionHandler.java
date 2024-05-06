package br.com.study.springEssentials.handler;

import br.com.study.springEssentials.exceptions.BadRequestException;
import br.com.study.springEssentials.exceptions.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice // Esta dizendo para todos os controllers que voce tem q usar oq vc tiver aqui dentro dessa classe
public class RestExceptionHandler {


    // Estamos falando para os controller que caso tenhamos uma exceção do tipo BAD REQUEST, vai utilizar esse EXCEPTION HANDLER, e vai retornar oq tem aqui
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);

    }
}
