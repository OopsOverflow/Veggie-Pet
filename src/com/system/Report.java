package com.system;

import com.entity.admin.Organisation;
import com.entity.person.Member;
import com.veggie.pet.Tree;

import java.time.LocalDate;
import java.util.Date;

public class Report {
    private Tree tree = null;
    private Member member = null;
    private Organisation organisation = null;
    private LocalDate datePublication;
    private String content;

    // Si on a le temps ajouter une feature pour lire un fichier texte pour le 'content'
    public Report(Tree tree, Member author, LocalDate datePublication, String content) {
        this.tree = tree;
        this.member = author;
        this.datePublication = datePublication;
        this.content = content;
    }

    public Report(Organisation author, LocalDate datePublication, String content) {
        this.organisation = author;
        this.datePublication = datePublication;
        this.content = content;
    }

    // Getters

    public String getContent() {
        return content;
    }

    public LocalDate getDatePublication(){
        return datePublication;
    }

    public Tree getTree() {
        return tree;
    }

    public Member getMember() {
        return member;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    @Override
    public String toString() {
        StringBuilder reportSTB = new StringBuilder(String.format("[Report INFO]\n"));
        if (tree != null) {
            reportSTB.append("\tTree ID : " + tree.getTreeID() + "\n");
            reportSTB.append("\tAuthor : " + member.getName() + " " + member.getFamilyName() + "\n");
        }
        if (organisation != null)
            reportSTB.append("\tOrganisation Name: " + organisation.getName() + "\n");

        reportSTB.append("\tPublishing Date : " + datePublication + "\n");
        reportSTB.append("\tContent : " + content + "\n");

        return reportSTB.toString();
    }

    public static void main(String[] args){
        Tree t = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);

        Organisation org = new Organisation("Tree Lovers", 100.0f, m1);
        Report r1 = new Report(org, LocalDate.now(), "DONNEZ NOUS DE L ARGENT");
        Report r = new Report(t,m1, LocalDate.now(), "Ceci est le contenu du report");
        System.out.println(r1);
    }
}
