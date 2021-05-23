package com.entity.person;

import com.entity.entity;

import java.util.Date;

public class Person extends entity {
    private String familyName;
    private Date dateOfBirth;
    private String address;

    public Person(String name, String familyName, Date dateOfBirth, String address) {
        super(name);
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }
}
