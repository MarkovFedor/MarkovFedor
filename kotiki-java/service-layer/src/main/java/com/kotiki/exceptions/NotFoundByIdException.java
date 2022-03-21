package com.kotiki.exceptions;

public class NotFoundByIdException extends Exception{
    public NotFoundByIdException(String message) {
        super(message);
    }
}
