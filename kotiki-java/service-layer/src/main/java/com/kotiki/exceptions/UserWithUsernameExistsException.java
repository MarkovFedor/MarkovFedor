package com.kotiki.exceptions;

public class UserWithUsernameExistsException extends Exception{
    public UserWithUsernameExistsException(String message){
        super(message);
    }
}
