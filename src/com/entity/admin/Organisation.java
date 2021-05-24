package com.entity.admin;

import com.entity.Donor;
import com.entity.person.Member;
import com.system.Report;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Organisation{
    private String name;
    private Float budget;
    private static int numMembers = 0;
    ArrayList<Donor> donorsList;
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

    public Vector<String> getNameOfMemberInMemberList(){
        Vector vecName = new Vector();
        if (membersList.isEmpty()){
                System.err.println("[ORGANISATION] Error : Get Name of Member List is impossible -> " +
                        "The MemberList is empty");
        }
        else{
            for(int i = 0 ; i < membersList.size() ; i++){
                vecName.add(membersList.get(i).getRight().getName());
            }
        }
        return vecName;
    }
    // Function

    public boolean addMoneyFromMemberContribution(Member m,float amount){

        if(checkMemberInMemberList(m).getLeft()){
            m.payContribution(amount);
            setBudget(budget+amount);
        }
        else{
            System.err.println("[ORGANISATION] Error : " + m.getName() + " "+ m.getFamilyName() + " is not a member of \"" + name + "\". Contribution Failed");
        }

        return false;
    }

    private ImmutablePair<Boolean, Integer> checkMemberInMemberList(Member member){
        int index =0;
        while (index < membersList.size() - 1){
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
            return true;
        }

        return false;
    }

    public boolean removeMember(Member member){
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            membersList.get(aux.getRight()).setLeft(null);
            return true;
        }
        return false;
    }

    public void askForDonations(){
//        et qui peuvent ^etre de dierentes natures (ex. services municipaux, entreprises, associations, individus),
//        mais qui doivent tous pouvoir recevoir une demande ecrite de subvention/don emanant de l'association et,

    }
    
    /*public void displayVector(Vector v){
        for ( :
             ) {
            
        }
    }*/

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
//    void dfrayerviste(Member){
////        membre ayant eectue la visite est defraye pour celle-ci d'un montant xe,
////        nombre maximum de visites par an.
//    }
//
//    void sendData(Member){
////        representation (dans un format a specier) de l'en-
////        semble de ses donnees personnelles (nom, prenom, date de naissance, adresse, date de derniere inscription,
////                liste de ses cotisations annuelles).
////        Est joint a cette demande
////        le dernier rapport d'activite de l'association qui contient un point budgetaire (recettes et depenses) et
////        une synthese des activites pour l'exercice precedent.
//    }
//
//    void payfacture(){
////        Le type de compte en banque de l'association ne lui
////        permettant d'avoir un solde negatif, un paiement ne pourra ^etre eectue que si la somme correspondante
////        existe bien sur le compte bancaire.
//    }

    @Override
    public String toString() {
        Vector v = getNameOfMemberInMemberList();
        StringBuilder OrganisationSTB = new StringBuilder(String.format("[Organisation INFO]\n"));
        OrganisationSTB.append("\tOrganisation name : " + getName() + "\n");
        OrganisationSTB.append("\tOrganisation budget : " + getBudget() + "\n");
        OrganisationSTB.append("\tMember List : " +  "\n");

        return OrganisationSTB.toString();
    }

    public static void main(String[] args) {

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998, 04, 30), "Somewhere not far from Tunis",
                new Date(2021, 05, 23), false, 5000);

        Member m2 = new Member("Esteban", "Neraudau", new Date(2001, 10, 29), "Antony",
                new Date(2021, 05, 23), false, 5000);

        Member m3 = new Member("Mohamed", "Mahmoud", new Date(2002, 10, 28), "Somewhere not far from Tunis",
                new Date(2021, 05, 24), false, 10000);

        Organisation org = new Organisation("Tree Lovers", 100.0f, m1);

        org.addMember(m2);

        //System.out.println(org.getMembersList());

        System.out.println(org.toString());
        org.addMoneyFromMemberContribution(m1, 200);
        System.out.println(org.toString());
        org.addMoneyFromMemberContribution(m3, 200);
        System.out.println(org.toString());
    }
}
