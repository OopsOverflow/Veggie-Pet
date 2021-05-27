package com.entity.admin;

import com.entity.Donor;
import com.entity.person.Member;
import com.system.Report;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import java.io.File;




public class Organisation{
    private String name;
    private Float budget;
    private static int numMembers = 0;
    ArrayList<Donor> donorsList;

    File financialRecord = createFile(this.getName() + "FinancialRecord");
    ArrayList<MutablePair<Integer, Member>> membersList = new ArrayList<>();



    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, Member... members) {
        this.name = name;
        this.budget = budget;
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
        }
    }

    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, ArrayList<Member> members) {
        this.name = name;
        this.budget = budget;
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
        }
    }

    public Organisation(String name, Float budget) {
        this.name = name;
        this.budget = budget;
    }

    public Organisation(String name, ArrayList<Donor> donorsList, Member ...members) {
        this.name = name;
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
        }
    }

    public Organisation(String name, Float budget, Member ... members) {
        this.name = name;
        this.budget = budget;
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
        }

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
                return this.financialRecord;
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
            return this.financialRecord;
        }
    }
    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

        if(checkMemberInMemberList(m).getLeft()){
            m.payContribution(amount);
            setBudget(budget+amount);
            System.out.println("[ORGANISATION] Got " + amount + "$ from " + m.getName() + " " + m.getFamilyName() + "\n");
        }
        else{
            System.err.println("[ORGANISATION] Error : " + m.getName() + " " + m.getFamilyName() + " is not a member of \"" + name + "\". Contribution Failed\n");
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
            return true;
        }
        else{
            System.err.println("[ORGANISATION] Error : " + member.getName() + " " + member.getFamilyName() + " could not be added to \"" + name + "\".\n");
        }

        return false;
    }

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
            writer.write(String.format("[%s]\nNEW FINANCIAL OPERATION==========\n",this.name));
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
                writeToRecord(financialRecord, "Refund MEMBER " + aux.getRight(), amount);
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
        writeToRecord(financialRecord,"Recieved Payment", amount);
    }

    @Override
    public String toString() {
        StringBuilder OrganisationSTB = new StringBuilder(String.format("[Organisation INFO]\n"));
        OrganisationSTB.append("\tOrganisation name : " + getName() + "\n");
        OrganisationSTB.append("\tOrganisation budget : " + getBudget() + "\n");
        OrganisationSTB.append("\tMember List : " + getNameOfMemberInMemberList() + "\n");

        return OrganisationSTB.toString();
    }

    public static void main(String[] args) {

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998, 04, 30), "Somewhere not far from Tunis",
                new Date(2021, 05, 23), false, 5000);

        Member m2 = new Member("Esteban", "Neraudau", new Date(2001, 10, 29), "Antony",
                new Date(2021, 05, 23), false, 5000);

        Member m3 = new Member("Mohamed", "Mahmoud", new Date(2002, 10, 28), "Somewhere not far from Tunis",
                new Date(2021, 05, 24), false, 10000);

        Member m4 = new Member("Rayane", "Hammadou",
                new Date(2000, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

        Organisation org = new Organisation("Tree Lovers", 1000.0f, m1);

        org.addMember(m2);
//        System.out.println(org.toString());
//        org.addMoneyFromMemberContribution(m1, 200);
//
//        org.addMember(m3);
//        System.out.println(org.toString());
//        System.out.println(org.checkMemberInMemberList(m3).getLeft());

        org.refundMember(m2,75);
        org.payBill(50);
        org.recieveFunds(500);

        Report r1 = new Report(org, "fincialReport", LocalDate.now(), org.financialRecord);
        System.out.println(r1);
        org.financialRecord.delete();
        //System.out.println(org.getMembersList());




    }
}
