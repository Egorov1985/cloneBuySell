package com.example.clonebuysell.exception.userException;

public class UserActiveBannedException extends RuntimeException{
    public UserActiveBannedException(String message){
        super(message);
    }

    public UserActiveBannedException(String message, Throwable cause) {
        super(message, cause);
    }
}
