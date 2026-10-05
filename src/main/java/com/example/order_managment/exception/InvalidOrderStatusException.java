package com.example.order_managment.exception;

public class InvalidOrderStatusException extends RuntimeException{
    public InvalidOrderStatusException(String message){
        super(message);
    }
}
