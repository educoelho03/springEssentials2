package br.com.study.springEssentials.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    List<FieldMessage> fieldErrors;
}
