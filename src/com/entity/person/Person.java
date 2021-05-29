package com.entity.person;

import com.entity.Entity;

import java.sql.Date;

public class Person extends Entity {
    private String familyName;
    private Date dateOfBirth;
    private String address;

    public Person(String name, String familyName, Date dateOfBirth, String address) {
        super(name);
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    /**
     * Permet d'obtenir le nom de famille d'une Personne
     * @return le nom de famille d'une Personne
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Permet d'obtenir la date de naissance d'une personne
     * @return la date de naissance d'une personne
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Permet d'obtenir l'adresse d'une personne
     * @return l'adresse d'une personne
     */
    public String getAddress() {
        return address;
    }

    /**
     * Méthode modélisant le test d'égalité d'une personne avec une autre
     * @param other l'autre personne
     * @return un booléen modélisant l'égalité ou non d'une personne avec une autre
     */
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
        return this.getName().equals(c.getName())
                && this.familyName.equals(c.familyName)
                && this.address.equals(c.address)
                && this.dateOfBirth.equals(c.dateOfBirth);
    }
}
