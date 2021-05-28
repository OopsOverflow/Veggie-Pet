package com.entity.person;

import com.entity.admin.Organisation;
import com.system.Report;
import com.veggie.tree.Tree;
import org.apache.commons.lang3.tuple.ImmutablePair;


import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

public class Member extends Person {
    private Date lastRegistrationDate;
    private boolean payedContribution;
    private ArrayList<Tree> treeNominations;
    private float CurrentAccount;
    private ArrayList<ImmutablePair<LocalDate, Float>> contributionList = new ArrayList<>();

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

    public void vote(Tree choice){
        /* Pour la première action, l’association permet chaque année `a chacun de ses membres de nominer
        jusqu’`a 5 arbres. 1 La liste effectivement transmise `a la municipalit´e, limit´ee elle aussi `a 5 arbres pour
        l’association, est obtenue en identifiant les arbres ayant obtenu le plus de voix des membres de l’association.
        2 La transmission de cette liste se fait une fois par an en fin d’exercice budg´etaire. Toutefois, la
        r´eception ´eventuelle de la notification d’une classification d’arbre remarquable par le service munipal des
        espaces verts peut se faire `a tout moment. */
    }

    public void submitReport(Report r){

    }

    // on imagine que le membre s'est connecté avant et que nous sommes sûrs que ce soit bien lui
    protected void getMyData(Organisation organisation){
        // fonction qui vérifie que le membre en question est bien présent dans l'organisation
        //probablement une fonction dans la classe organisation telle que :
        // Organisation.isMember(Member m) -> test si le Member m est dans la liste des membres.
    }

    public void leaveOrganisation(Organisation organisation){

    }

    @Override
    public String toString() {
        StringBuilder memberSTB = new StringBuilder(String.format("[Member INFO]\n"));
        memberSTB.append("\tName : " + getName() + "\n");
        memberSTB.append("\tFamily Name : " + getFamilyName() + "\n");
        memberSTB.append("\tPayed Contribution ? " + isPayedContribution() + "\n");

        return memberSTB.toString();
    }

    public static void main(String[] args){
        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);
        Member m2 = new Member("Houssem", "Mahmoud", new Date(1998,03,30), "Somewhere not far from Tunis",
                new Date(2021,04,23), false, 5000);

        System.out.println(m1.equals(m2));
        System.out.println(m1.toString());
        m1.payContribution(500);
        System.out.println(m1.toString());

        System.out.println(m1.getContributionList());
    }
}
