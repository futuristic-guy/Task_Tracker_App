package com.example.todoapp;

public class seeAllTaskCardDatatype {

    private String text;
    private String id;

    seeAllTaskCardDatatype(String text,String id){
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
