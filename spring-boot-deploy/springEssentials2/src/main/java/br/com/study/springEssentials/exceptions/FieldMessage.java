package br.com.study.springEssentials.exceptions;

public class FieldMessage {
    private String fieldName;
    private String fieldMessage;

    public FieldMessage(String fieldName, String fieldMessage) {
        this.fieldName = fieldName;
        this.fieldMessage = fieldMessage;
    }
}
