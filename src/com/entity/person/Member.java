package com.entity.person;

import com.entity.admin.Municipality;
import com.entity.org.Organisation;
import com.system.EncryptionDecryptionUtil;
import com.system.Report;
import com.veggie.Tree;

import org.apache.commons.lang3.tuple.ImmutablePair;


import java.security.NoSuchAlgorithmException;
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
    private String  decryptKey;
    // Constructeur
    public Member(String name, String familyName, Date dateOfBirth, String address, Date lastRegistrationDate,
                  boolean payedContribution, ArrayList<Tree> treeNominations, float CurrentAccount) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
        this.treeNominations = treeNominations;
        this.CurrentAccount = CurrentAccount;
        try{
            this.decryptKey = EncryptionDecryptionUtil.generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Member(String name, String familyName, Date dateOfBirth, String address,
                  Date lastRegistrationDate, boolean payedContribution, float CurrentAccount) {
        super(name, familyName, dateOfBirth, address);
        this.lastRegistrationDate = lastRegistrationDate;
        this.payedContribution = payedContribution;
        this.CurrentAccount = CurrentAccount;
        try{
            this.decryptKey = EncryptionDecryptionUtil.generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Getter

    /**
     * Permet d'obtenir la derni??re date de Registration
     * @return la derni??re date de Registration
     */
    public Date getLastRegistrationDate() {
        return lastRegistrationDate;
    }

    /**
     * Permet de savoir si le membre a pay?? sa contribution a l'association
     * @return Le bool??en payedContribution
     */
    public boolean isPayedContribution() {
        return payedContribution;
    }

    /**
     * Permet d'obtenir les nominations d'arbres du membre
     * @return un array list des arbres nomin??s
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
     * Permet d'obtenir les votes du membre dans un StringBuilder pour faire un affichage dans la m??thode toString()
     * @return un StringBuilder avec les id des arbres vot??s par le membre.
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
     * M??thode mod??lisant le paiement de la contribution du membre.
     * @param amount le montant de la contribution
     * @param org l'organisation
     * @return un bool??en pour savoir la m??thode s'est bien ex??cut??
     */
    public boolean payContribution(Organisation org, float amount){
        boolean success = false ;
        if(amount <= CurrentAccount) {
            CurrentAccount -= amount;
            contributionList.add(new ImmutablePair<>(LocalDate.now(), amount));
            org.addMoneyFromMemberContribution(this, amount);
            payedContribution = true;
            success = true;


        }
        else{
            System.err.println("[Member] Insufficient funds\n");
        }
        return success;
    }
    /**
     * M??thode mod??lisant le vote d'un membre pour un arbre. Si le membre vote pour plus de 5 arbres, il lui est
     * demand?? s'il veut oui ou non valid?? son changement de vote, car seulement 5 votes sont possibles pour un membre.
     * Si le membre dit oui la file est actualis??e et le vote le plus ancien disparait au profit du nouveau.
     * @param trees l'??numeration d'arbres
     */
    public void vote(Tree ...trees){
        for(Tree t : trees){
            if(votes.size() >= 5){
                // Peut etre ajouter le nouveau vote (id de l'arbre + pr??nom) au profit du plus ancient pour pr??venir l'utilisateur (id de l'arbre + pr??nom)
                System.out.println(String.format("\n[%s] Do you really want to replace you oldest vote ? " +
                        "(You already voted for 5 Trees)", this.getName()));

                Scanner sc = new Scanner(System.in);
                String answer;
                do{
                    System.out.println("\n\tVote Replace : yes or no ?");
                    answer = sc.next();
                    answer = answer.toLowerCase();
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

    /**
     * Submits activity report after a tree visit to the organisation
     * @param tree Visited Tree
     * @param organisation the organisation
     * @param date date of visit
     */
    public void submitReport(Tree tree, Date date, Organisation organisation){
        Report report = new Report(tree, this, "Activity : Visit Report", LocalDate.now(),
                "Visited Tree " + tree.getTreeID() +
                "\n on " + date.toString() +
                "\nLocation " + tree.getAddress());
        organisation.appendToActivity(this, report);
    }

    /**
     * M??thode mod??lisant le volontariat d'un membre pour visiter un arbre en representant une association.
     * Apelle la m??thode allowOrNotVisit() de la classe Organisation
     * @param o l'organisation que le membre veut representer lors de la visite
     * @param t l'arbre que le membre visit??
     * @return un bool??en mod??lisant la reussite
     */
    public boolean toVolunteerOn(Organisation o, Tree t){
        return o.allowOrNotVisit(this, t);
    }

    /**
     * M??thode qui permet ?? un membre de voir les donn??es crypt??es qu'a l'organisation
     * @param organisation le membre demande les infos qu'a 'organisation' a son sujet
     * @return un String avec les donn??es
     */
    public String getMyData(Organisation organisation){
        return organisation.sendData(this, decryptKey);
    }

    /**
     * M??thode qui permet de decrypter les data
     * @param organisation l'organisation concern??es par le decryptage de donn??es
     * @return un String qui contient les data
     */
    private String decryptData(Organisation organisation){
        try{
            String str = getMyData(organisation);
            return EncryptionDecryptionUtil.decrypt(decryptKey, str);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Decrypt Data. Check your key.");
            return null;
        }
    }

    /**
     * M??thode mod??lisant le d??part d'un membre d'une organisation.
     * @param organisation l'organisation quitt??e par le membre
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
        Member m2 = new Member("Aaa", "Mahmoud", new Date(1998,03,30), "Somewhere not far from Tunis",
                new Date(2021,04,23), false, 5000);


        Municipality paris = new Municipality("Paris", "SomeWhere");
        Organisation TreeLovers = new Organisation("Tree Lovers", 10000.0f, paris, m1, m2);

        System.out.println(m1.decryptData(TreeLovers));

//        System.out.println(m1.equals(m2));
//        System.out.println(m1.toString());
//
//        System.out.println(m1.toString());
//
//        System.out.println(m1.getContributionList());
//
//        m1.vote(t1,t2,t3,t4,t5);
//
//        m1.vote(t6,t7);
//        System.out.println(m1.toString());

        //m1.toVolunteerOn(t1);


        /*Iterator iteratorVals = m1.getVotes().iterator();
        while(iteratorVals.hasNext()){
            Tree next = (Tree) iteratorVals.next();
            System.out.println(next.getTreeID());
        }*/
    }
}