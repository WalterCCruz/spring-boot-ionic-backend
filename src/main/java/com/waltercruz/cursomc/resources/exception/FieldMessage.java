package com.waltercruz.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    private String fieldMessage;
    private String message;


    public FieldMessage(){

    }


    public String getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(String fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FieldMessage(String fieldMessage, String message) {
        this.fieldMessage = fieldMessage;
        this.message = message;
    }


}
