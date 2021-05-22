package com.entity.person;

import com.entity.admin.Organisation;
import com.veggie.pet.Tree;

import java.util.ArrayList;
import java.util.Date;

public class Member extends Person {
    private Date lastRegistrationDate;
    private boolean payedContribution;
    private ArrayList<Tree> treeNominations;

    public Member(String name, String familyName, Date dateOfBirth, String address, Date lastRegistrationDate,
                  boolean payedContribution, ArrayList<Tree> treeNominations) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
        this.treeNominations = treeNominations;
    }

    public Member(String name, String familyName, Date dateOfBirth, String address,
                  Date lastRegistrationDate, boolean payedContribution) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
    }

    public void payContribution(float amount){

    }

    public void vote(Tree choice){

    }

    protected void getMyData(Organisation organisation){

    }

    public void leaveOrganisation(Organisation organisation){

    }
}
