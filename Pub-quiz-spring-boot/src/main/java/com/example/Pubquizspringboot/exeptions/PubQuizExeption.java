package com.example.Pubquizspringboot.exeptions;

public class PubQuizExeption extends RuntimeException{
    public PubQuizExeption(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public PubQuizExeption(String exMessage) {
        super(exMessage);
    }
}
