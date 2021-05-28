package com.entity;

import com.entity.admin.Organisation;

public class Donor{
    private String name;
    protected StringBuilder letterBox = new StringBuilder();

    public Donor(String name) {
        this.name = name;
    }

    public void donate(float amount, Organisation org){
        org.recieveFunds(amount);
    }

}
