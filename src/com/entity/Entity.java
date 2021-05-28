package com.entity;

import com.entity.admin.Organisation;

public class Entity {
    private String name;
    protected StringBuilder letterBox = new StringBuilder();

    public Entity(String name){
        this.name = name;
    }

    public void donate(float amount, Organisation org){
        org.recieveFunds(amount);
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringBuilder getLetterBox() {
        return letterBox;
    }

    public void setLetterBox(StringBuilder letterBox) {
        this.letterBox = letterBox;
    }
}
