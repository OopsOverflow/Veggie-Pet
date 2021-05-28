package com.entity.admin;

import com.entity.Donor;
import com.entity.Entity;
import com.entity.person.Member;
import com.system.Report;
import com.veggie.Tree;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.Date;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class Organisation extends Entity {
    private Float budget;
    private static int numMembers = 0;
    private String DBURL;
    ArrayList<Donor> donorsList;

    // Financial Record
    File financialRecord;
    private ArrayList<MutablePair<Integer, Member>> membersList = new ArrayList<>();

    // Vote
    private ArrayList<Queue<Tree>> listVoteMember = new ArrayList<>();
    private Map<Integer, Integer> mapTreeVote = new HashMap<>();

    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, Member... members) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    (java.sql.Date) m.getLastRegistrationDate(), "");
        }
        this.financialRecord = createFile(name.replaceAll("\\s+","")  + "FinancialRecord");
    }

    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, ArrayList<Member> members) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    (java.sql.Date) m.getLastRegistrationDate(), "");
        }
        // .replaceAll() removes white spaces from name
        // Better indexing on cross platform
        this.financialRecord = createFile(name.replaceAll("\\s+","")  + "FinancialRecord");
    }

    public Organisation(String name, Float budget) {
        super(name);
        this.budget = budget;
        this.DBURL = OrganisationDB.connect(name);
        this.financialRecord = createFile(name.replaceAll("\\s+","")  + "FinancialRecord");
    }

    public Organisation(String name, ArrayList<Donor> donorsList, Member ...members) {
        super(name);
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    (java.sql.Date) m.getLastRegistrationDate(), "");
        }
        this.financialRecord = createFile(name.replaceAll("\\s+","")  + "FinancialRecord");
    }

    public Organisation(String name, Float budget, Member ... members) {
        super(name);
        this.budget = budget;
        this.DBURL = OrganisationDB.connect(name);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    (java.sql.Date) m.getLastRegistrationDate(), "");
        }
        this.financialRecord = createFile(name.replaceAll("\\s+","")  + "FinancialRecord");
    }

    /**@TODO Ficher Exercice Budgétaire
     * @TODO Fichier Exercice chaque année
     */

    private File createFile(String fileName) {
        try {
            File myObj = new File(fileName + ".txt");
            if (myObj.createNewFile()) {
                return myObj;
            } else {
                System.err.println("File already exists.");
                return new File(fileName + ".txt");
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
            return this.financialRecord;
        }
    }
    // Getters & Setters

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public ArrayList<Donor> getDonorsList() {
        return donorsList;
    }

    public void setDonorsList(ArrayList<Donor> donorsList) {
        this.donorsList = donorsList;
    }

    public ArrayList<MutablePair<Integer, Member>> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<MutablePair<Integer, Member>> membersList) {
        this.membersList = membersList;
    }

    public ArrayList<Queue<Tree>> getListVoteMember() {
        return listVoteMember;
    }

    // Organisation Operations

    public StringBuilder getNameOfMemberInMemberList(){
        StringBuilder s = new StringBuilder();
        if (membersList.isEmpty()){
                System.err.println("[ORGANISATION] Error : Get Name of Member List is impossible -> " +
                        "The MemberList is empty");
        }
        else{
            for(int i = 0 ; i < membersList.size() ; i++){
                if(i == membersList.size()-1){
                    s.append(membersList.get(i).getRight().getName());
                }
                else{
                    s.append(membersList.get(i).getRight().getName());
                    s.append(" ; ");
                }
            }
        }
        return s;
    }
    // Function

    public boolean addMoneyFromMemberContribution(Member m,float amount){
        // Aux value to locally store value
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(m);
        if(aux.getLeft()){
            m.payContribution(amount);
            setBudget(budget+amount);
            System.out.println(String.format("[%s] Got " + amount + "$ from " + m.getName() + " " +
                    m.getFamilyName() + "\n", this.getName()));
            writeToRecord(financialRecord, String.format("Recieved @Member %d Contribution",
                    membersList.get(aux.getRight()).getLeft()), amount);
        }
        else{
            System.err.println(String.format("[%s] Error : " + m.getName() + " " + m.getFamilyName() +
                    " is not a member of \"" + this.getName() + "\". Contribution Failed\n", this.getName()));
        }

        return false;
    }


    private ImmutablePair<Boolean, Integer> checkMemberInMemberList(Member member){
        int index =0;
        while (index < membersList.size()){
            if (membersList.get(index).getRight() == member)
                return new ImmutablePair<>(true, index);
            else
                index++;
        }
        return new ImmutablePair<>(false, -1);
    }

    public boolean addMember(Member member){
        if (!checkMemberInMemberList(member).getLeft()){
            membersList.add(new MutablePair<Integer, Member>(++numMembers, member));
            System.out.println("Affichage du membre quand il a été ajouté : index = " + numMembers + " ; prénom du membre : " + member.getName());
            OrganisationDB.insertMemberData(DBURL, numMembers, member.getName(), member.getFamilyName(),
                    (java.sql.Date) member.getLastRegistrationDate(), "");
            return true;
        }
        else{
            System.err.println("[ORGANISATION] Error : " + member.getName() + " " + member.getFamilyName() + " could not be added to \"" + this.getName() + "\".\n");
        }

        return false;
    }


    // VOTE's FUNCTION

    // Une fonction pour récupérer tous les votes de chaque membre de l'organisation
    // La fonction récupère tous les votes de tous les membres de l'association et stocke ça dans listVoteMember
    private void getVotesFromMember(){
        for(MutablePair<Integer, Member> m : membersList){
            listVoteMember.add(m.getRight().getVotes());
        }
    }

    // Une fonction pour compter les votes de chaque membre - mapTreeVote
    private void countVote(){
        for(int i = 0 ; i < listVoteMember.size() ; i++){
            for(int j = 0 ; i < listVoteMember.get(i).size() ; j++){
                if(!(mapTreeVote.containsKey(listVoteMember.get(i).element().getTreeID()))){
                    mapTreeVote.put(listVoteMember.get(i).element().getTreeID(), new Integer(1));
                }
                else{
                    // récupérer le int du vote et faire ++ comme ça on aura le nombre de vote
                    mapTreeVote.get("");
                }
            }
        }
    }

    // Une fonction pour parcourir et avoir le classement des votes


    // Une fonction pour récuperer les 5 arbres les plus votés et les stocker dans un tableau


    // TODO: 28/05/2021 Add remove member to DB
    public boolean removeMember(Member member){
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            // get the member in the the member list using his index
            // leave the member ID, but delete all his info.
            membersList.get(aux.getRight()).setRight(null);
            System.out.println("Member successfully removed");
            return true;
        }
        System.err.println("FATAL ERROR : MEMBER IS NOT A PART OF THE ORGANISATION");
        return false;
    }

    private boolean writeToRecord(File record, String typeOfOperation, float amount){
        try(FileWriter writer = new FileWriter(financialRecord.getName(),true)){
            writer.write(String.format("[%s]\nNEW FINANCIAL OPERATION==========\n",this.getName()));
            writer.write("\tDate : " + LocalDateTime.now() + "\n");
            writer.write("\tType : " + typeOfOperation + "\n");
            writer.write("\tAmount : " + amount + "\n");
            writer.write("\tNew Budget : " + budget + "\n"); // Change to budget must be done before
            writer.write("END -----------------\n");

            return true;
        }
        catch (IOException e) {
            System.err.println("I/O ERROR : CAN'T WRITE TO FILE" + record.getAbsolutePath());
            e.printStackTrace();
            return false;
        }

    }

    public void askForDonations(){
//        et qui peuvent ^etre de dierentes natures (ex. services municipaux, entreprises, associations, individus),
//        mais qui doivent tous pouvoir recevoir une demande ecrite de subvention/don emanant de l'association et,
        Report report = new Report(this, "Your Donations Keep Us Going",
                LocalDate.now(), this.financialRecord);
        //@TODO: NOTIFICATION SYSTEM
        //donorsList.forEach(x -> sendNotification(this, x, report));
    }


    //    private String getRecord() {
//
//    }
//
//    void planifiervisite(){
////        l'association planie des visites d'arbres remarquables. Disposant de peu de
////        moyens, celles-ci sont faites sur la base du volontariat de ses membres, qui
//    }
//
//    void affectervisite(Member){
////        Une visite est automatiquement validee et donc aectee a un membre si
////        aucun autre membre n'a deja programme une visite pour cet arbre remarquable. Pour
//    }
//

    public void refundMember(Member member, float amount){
//        membre ayant eectue la visite est defraye pour celle-ci d'un montant xe,
//        nombre maximum de visites par an.

        // Check if member is in member list
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            // Check if the amount to refund is within budget
            if (budget - amount > 0){
                budget -= amount;
                writeToRecord(financialRecord, "Refund @MEMBER " + aux.getRight(), amount);
            }
            else {
                System.err.println("INSUFFICIENT FUNDS");
            }
        }
        else{
            System.err.println("FATAL ERROR : MEMBER IS NOT A PART OF THE ORGANISATION");
        }
    }

//    void sendData(Member){
////        representation (dans un format a specier) de l'en-
////        semble de ses donnees personnelles (nom, prenom, date de naissance, adresse, date de derniere inscription,
////                liste de ses cotisations annuelles).
////        Est joint a cette demande
////        le dernier rapport d'activite de l'association qui contient un point budgetaire (recettes et depenses) et
////        une synthese des activites pour l'exercice precedent.
//    }
//
    public void payBill(float amount){
//        Le type de compte en banque de l'association ne lui
//        permettant d'avoir un solde negatif, un paiement ne pourra ^etre eectue que si la somme correspondante
//        existe bien sur le compte bancaire.
        if (budget - amount > 0){
            budget -= amount;
            writeToRecord(financialRecord, "Bill Payment", amount);
        }
        else{
            System.err.println("INSUFFICIENT FUNDS");
        }
    }

    public void recieveFunds(float amount){
        this.budget += amount;
        writeToRecord(financialRecord,"Received Donor Payment", amount);
    }

    @Override
    public String toString() {
        StringBuilder OrganisationSTB = new StringBuilder(String.format("[%s INFO]\n",this.getName()));
        OrganisationSTB.append("\tOrganisation name : " + getName() + "\n");
        OrganisationSTB.append("\tOrganisation budget : " + getBudget() + "\n");
        OrganisationSTB.append("\tMember List : " + getNameOfMemberInMemberList() + "\n");

        return OrganisationSTB.toString();
    }

    public static void main(String[] args) throws InterruptedException {

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998, 04, 30), "Somewhere not far from Tunis",
                new Date(2021, 05, 23), false, 5000);

        Member m2 = new Member("Esteban", "Neraudau", new Date(2001, 10, 29), "Antony",
                new Date(2021, 05, 23), false, 5000);

        Member m3 = new Member("Mohamed", "Mahmoud", new Date(2002, 10, 28), "Somewhere not far from Tunis",
                new Date(2021, 05, 24), false, 10000);

        Member m4 = new Member("Rayane", "Hammadou",
                new Date(2000, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

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

        // Organisation org = new Organisation("Tree Lovers", 1000.0f, m1);

        // org.addMember(m2);
        // System.out.println(org.toString());
        /*org.getVotesFromMember();
        System.out.println(org.getListVoteMember());*/

        m2.vote(t1,t2,t3);
        m1.vote(t1,t3,t5);
        m3.vote(t1,t4,t5);
        m4.vote(t1,t6,t7);

        //org.addMoneyFromMemberContribution(m1, 200);
        // Added time delays for dramatic effect
        //TimeUnit.SECONDS.sleep(2);
        //org.addMember(m3);
        //System.out.println(org.toString());
        //System.out.println(org.checkMemberInMemberList(m3).getLeft());

        //org.refundMember(m2,75);
        //TimeUnit.SECONDS.sleep(2);
        //org.payBill(50);
        //org.recieveFunds(500);
        //TimeUnit.SECONDS.sleep(2);

        //Report r1 = new Report(org, "financialReport", LocalDate.now(), org.financialRecord);
        //System.out.println(r1);
        //org.financialRecord.delete();
        //System.out.println(org.getMembersList());

        Map<String, Integer> map = new HashMap<>();

        map.put("key1", 1);
        map.put("key2", 1);
        map.put("key3", new Integer(1));

        System.out.println(map.get("key3")); // Trouver comment incrémenter une valeur dans une map a partir de sa clé
    }
}
