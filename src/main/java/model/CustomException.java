package model;

public class CustomException extends RuntimeException{
    public CustomException(String message, Throwable reason){
        super(message, reason);
    }
}
