package com.entity.person;

import com.entity.org.Organisation;
import com.system.Report;
import com.veggie.Tree;

import org.apache.commons.lang3.tuple.ImmutablePair;


import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public class Member extends Person {
    private Date lastRegistrationDate;
    private boolean payedContribution;
    private ArrayList<Tree> treeNominations;
    private float CurrentAccount;
    private ArrayList<ImmutablePair<LocalDate, Float>> contributionList = new ArrayList<>();
    private Deque<Tree> votes = new LinkedList<>();

    // Constructeur
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

    // Getter
    public Date getLastRegistrationDate() {
        return lastRegistrationDate;
    }

    public boolean isPayedContribution() {
        return payedContribution;
    }

    public ArrayList<Tree> getTreeNominations() {
        return treeNominations;
    }

    public float getCurrentAccount() {
        return CurrentAccount;
    }

    public ArrayList<ImmutablePair<LocalDate, Float>> getContributionList() {
        return contributionList;
    }

    public String getTextVotes() {
        return String.format("[%s] a voté pour les arbres : " + getIdVotes(), this.getName());
    }

    public Queue<Tree> getVotes() {
        return votes;
    }

    private StringBuilder getIdVotes (){
        StringBuilder s = new StringBuilder();
        int cpt = 0;
        for(Tree t : votes){
            if(cpt<4)
                s.append(t.getTreeID() + " - ");
            else
                s.append(t.getTreeID());
            cpt++;
        }
        return s;
    }

    // Setter
    public void setContributionList(ArrayList<ImmutablePair<LocalDate, Float>> contributionList) {
        this.contributionList = contributionList;
    }

    // Function
    public boolean payContribution(float amount){
        boolean success = false ;
        if(amount <= CurrentAccount) {
            CurrentAccount -= amount;
            contributionList.add(new ImmutablePair<>(LocalDate.now(), amount));
            payedContribution = true;
            success = true;
        }
        else{
            System.err.println("[Member] Insufficient funds\n");
        }
        return success;
    }

    public void vote(Tree ...trees){
        for(Tree t : trees){
            if(votes.size() >= 5){
                // Peut etre ajouter le nouveau vote (id de l'arbre + prénom) au profit du plus ancient pour prévenir l'utilisateur (id de l'arbre + prénom)
                System.out.println(String.format("\n[%s] Do you really want to replace you oldest vote ? " +
                        "(You already voted for 5 Trees)", this.getName()));

                Scanner sc = new Scanner(System.in);
                String answer;
                do{
                    System.out.println("\n\tVote Replace : yes or no ?");
                    answer = sc.next();
                    answer = answer.toLowerCase();
                    System.out.println("answer=" + answer);
                } while (!(answer.equals("yes") || answer.equals("no")));
                if (answer.equals("yes")){
                    votes.poll();
                    votes.offer(t);
                }
            }
            else{
                votes.add(t);
            }
        }
    }

    public void submitReport(Report r){

    }

    protected void getMyData(Organisation organisation){
        // fonction qui vérifie que le membre en question est bien présent dans l'organisation
        //probablement une fonction dans la classe organisation telle que :
        // Organisation.isMember(Member m) -> test si le Member m est dans la liste des membres.

    }

    public void leaveOrganisation(Organisation organisation){
        if (organisation.removeMember(this))
            System.out.println("Successfully Left Organisation " + organisation.getName());
        else
            System.out.println("You are not a member of this organisation.");
    }

    @Override
    public String toString() {
        StringBuilder memberSTB = new StringBuilder(String.format("[Member INFO]\n"));
        memberSTB.append("{\n");
        memberSTB.append("\tName : " + getName() + "\n");
        memberSTB.append("\tFamily Name : " + getFamilyName() + "\n");
        memberSTB.append("\tPayed Contribution ? " + isPayedContribution() + "\n");
        memberSTB.append("\tVote : " + getIdVotes() + "\n");
        memberSTB.append("}\n");

        return memberSTB.toString();
    }

    public static void main(String[] args){
        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);
        Member m2 = new Member("Houssem", "Mahmoud", new Date(1998,03,30), "Somewhere not far from Tunis",
                new Date(2021,04,23), false, 5000);

        Tree t1 = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t2 = new Tree(1, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t3 = new Tree(2, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t4 = new Tree(3, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t5 = new Tree(4, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t6 = new Tree(5, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t7 = new Tree(6, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        System.out.println(m1.equals(m2));
        System.out.println(m1.toString());
        m1.payContribution(500);
        System.out.println(m1.toString());

        System.out.println(m1.getContributionList());

        m1.vote(t1,t2,t3,t4,t5);

        //m1.vote(t6,t7);
        System.out.println(m1.toString());

        /*Iterator iteratorVals = m1.getVotes().iterator();
        while(iteratorVals.hasNext()){
            Tree next = (Tree) iteratorVals.next();
            System.out.println(next.getTreeID());
        }*/
    }
}