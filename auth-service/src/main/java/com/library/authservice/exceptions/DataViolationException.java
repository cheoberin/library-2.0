package com.library.authservice.exceptions;

public class DataViolationException extends RuntimeException{

    public DataViolationException(String message){
        super(message);
    }

}
