package com.entity.person;

import java.util.Date;

public class Person {
    private String name;
    private String familyName;
    private Date dateOfBirth;
    private String address;

    public Person(String name, String familyName, Date dateOfBirth, String address) {
        this.name = name;
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
