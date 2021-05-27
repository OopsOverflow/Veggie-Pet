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

    @Override
    public boolean equals(Object other) {

        // If the object is compared with itself then return true
        if (other == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(other instanceof Person)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Person c = (Person) other;

        // Compare the data members and return accordingly
        return this.name.equals(c.name)
                && this.familyName.equals(c.familyName)
                && this.address.equals(c.address)
                && this.dateOfBirth.equals(c.dateOfBirth);
    }
}
