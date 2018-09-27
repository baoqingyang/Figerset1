package com.example.intent.figerset.Model;

public class Contacts {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
    public Contacts(String name,String number){
        this.name = name;
        this.number = number;
    }
}
