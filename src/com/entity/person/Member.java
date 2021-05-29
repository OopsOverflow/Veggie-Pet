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

    /**
     * Permet d'obtenir la dernière date de Registration
     * @return la dernière date de Registration
     */
    public Date getLastRegistrationDate() {
        return lastRegistrationDate;
    }

    /**
     * Permet de savoir si le membre a payé sa contribution a l'association
     * @return Le booléen payedContribution
     */
    public boolean isPayedContribution() {
        return payedContribution;
    }

    /**
     * Permet d'obtenir les nominations d'arbres du membre
     * @return un array list des arbres nominés
     */
    public ArrayList<Tree> getTreeNominations() {
        return treeNominations;
    }

    /**
     * Permet d'obtenir le compte courant du membre (son argent)
     * @return un float du compte courant
     */
    public float getCurrentAccount() {
        return CurrentAccount;
    }

    /**
     * Permet d'obtenir la liste des contributions du membre
     * @return un ArrayList de pair 'Date,Float'
     */
    public ArrayList<ImmutablePair<LocalDate, Float>> getContributionList() {
        return contributionList;
    }

    /**
     * Permet d'obtenir les votes du membre
     * @return une file de 'Tree'
     */
    public Queue<Tree> getVotes() {
        return votes;
    }

    /**
     * Permet d'obtenir les votes du membre dans un StringBuilder pour faire un affichage dans la méthode toString()
     * @return un StringBuilder avec les id des arbres votés par le membre.
     */
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
    /**
     * Permet de modifier la liste des contribution du membre
     * @param contributionList la nouvelle liste de paire 'Date,Float'
     */
    public void setContributionList(ArrayList<ImmutablePair<LocalDate, Float>> contributionList) {
        this.contributionList = contributionList;
    }

    // Function
    /**
     * Méthode modélisant le paiement de la contribution du membre.
     * @param amount le montant de la contribution
     * @return un booléen pour savoir la méthode s'est bien exécuté
     */
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
    /**
     * Méthode modélisant le vote d'un membre pour un arbre. Si le membre vote pour plus de 5 arbres, il lui est
     * demandé s'il veut oui ou non validé son changement de vote, car seulement 5 votes sont possibles pour un membre.
     * Si le membre dit oui la file est actualisée et le vote le plus ancien disparait au profit du nouveau.
     * @param trees
     * @return
     */
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

    /**
     * Méthode modélisant le volontariat d'un membre pour visiter un arbre en representant une association.
     * Apelle la méthode allowOrNotVisit() de la classe Organisation
     * @param o l'organisation que le membre veut representer lors de la visite
     * @param t l'arbre que le membre visité
     * @return
     */
    public void toVolunteerOn(Organisation o, Tree t){
        o.allowOrNotVisit(this, t);
    }

    /**
     * Méthode qui permet à un membre de voir les données qu'a l'organisation
     * @param organisation le membre demande les infos qu'a 'organisation' a son sujet
     */
    protected void getMyData(Organisation organisation){
        // fonction qui vérifie que le membre en question est bien présent dans l'organisation
        //probablement une fonction dans la classe organisation telle que :
        // Organisation.isMember(Member m) -> test si le Member m est dans la liste des membres.

    }

    /**
     * Méthode modélisant le départ d'un membre d'une organisation.
     * @param organisation l'organisation quittée par le membre
     */
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
                new Float[]{(float)48.8632712288,(float)2.39435673087}, true);

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