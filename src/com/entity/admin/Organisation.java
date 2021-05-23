package com.entity.admin;

import com.entity.Donor;
import com.entity.entity;
import com.entity.person.Member;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Organisation extends entity {
    private Float budget;
    private static int numMembers = 0;
    ArrayList<Donor> donorsList;
    ArrayList<MutablePair<Member, Integer>> membersList = new ArrayList<>();


    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, Member... members) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(m, ++numMembers));
        }
    }

    public Organisation(String name, Float budget, ArrayList<Donor> donorsList, ArrayList<Member> members) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(m, ++numMembers));
        }
    }

    public Organisation(String name, Float budget) {
        super(name);
        this.budget = budget;
    }

    public Organisation(String name, ArrayList<Donor> donorsList, Member ...members) {
        super(name);
        this.donorsList = donorsList;
        for (Member m : members){
            membersList.add(new MutablePair<>(m, ++numMembers));
        }
    }

    public Organisation(String name, Float budget, Member ... members) {
        super(name);
        this.budget = budget;
        for (Member m : members){
            membersList.add(new MutablePair<>(m, ++numMembers));
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

    public ArrayList<MutablePair<Member, Integer>> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<MutablePair<Member, Integer>> membersList) {
        this.membersList = membersList;
    }

    private ImmutablePair<Boolean, Integer> checkMemberInMemberList(Member member){
        int index =0;
        while (index < membersList.size() - 1){
            if (membersList.get(index).getLeft() == member)
                return new ImmutablePair<>(true, index);
            else
                index++;
        }
        return new ImmutablePair<>(false, -1);
    }

    public boolean addMember(Member member){
        if (!checkMemberInMemberList(member).getLeft()){
            membersList.add(new MutablePair<Member, Integer>(member, ++numMembers));
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

//    public boolean askForDonations(){
////        et qui peuvent ^etre de dierentes natures (ex. services municipaux, entreprises, associations, individus),
////        mais qui doivent tous pouvoir recevoir une demande ecrite de subvention/don emanant de l'association et,
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

    public static void main(String[] args) {

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);

        Member m2 = new Member("Esteban", "Neraudau", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);

        MutablePair<Member, Integer> pair = new MutablePair<>(m1,100);
        ArrayList<Pair<Member, Integer>> list = new ArrayList<>();
        list.add(pair);
        Organisation org = new Organisation("Tree Lovers", 100.0f, m1);

        org.addMember(m2);
        org.addMember(m2);

        System.out.println(org.getMembersList());
    }













}
