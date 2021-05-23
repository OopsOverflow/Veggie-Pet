package com.entity.person;

import com.entity.admin.Organisation;
import com.veggie.pet.Tree;
import org.omg.CORBA.Current;

import java.util.ArrayList;
import java.util.Date;

public class Member extends Person {
    private Date lastRegistrationDate;
    private boolean payedContribution;
    private ArrayList<Tree> treeNominations;
    private float CurrentAccount;

    public Member(String name, String familyName, Date dateOfBirth, String address, Date lastRegistrationDate,
                  boolean payedContribution, ArrayList<Tree> treeNominations, float CurrentAccount) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
        this.treeNominations = treeNominations;
        this.CurrentAccount = CurrentAccount;
    }

    public Member(String name, String familyName, Date dateOfBirth, String address,
                  Date lastRegistrationDate, boolean payedContribution, float CurrentAccount) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
        this.CurrentAccount = CurrentAccount;
    }

    public boolean payContribution(float amount){
        boolean success = false ;
        if(amount <= CurrentAccount) {
            CurrentAccount -= amount;
            payedContribution = true;
            success = true;
        }
        else{
            System.err.println("[Member] Insufficient funds\n");
        }
        return success;
    }

    public void vote(Tree choice){

    }

    protected void getMyData(Organisation organisation){

    }

    public void leaveOrganisation(Organisation organisation){

    }

    public static void main(String[] args){
        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);

    }
}
