package com.kotiki.exceptions;

import javax.persistence.Access;

public class AccessToStrangersEntityException extends Exception{
    public AccessToStrangersEntityException(String message) {
        super(message);
    }
}
