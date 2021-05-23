package com.system;

import com.entity.person.Member;
import com.veggie.pet.Tree;

import java.time.LocalDate;
import java.util.Date;

public class Report {
    private Tree tree;
    private Member author;
    private LocalDate datePublication;
    private String content;

    // Si on a le temps ajouter une feature pour lire un fichier texte pour le 'content'
    public Report(Tree tree, Member author, LocalDate datePublication, String content) {
        this.tree = tree;
        this.author = author;
        this.datePublication = datePublication;
        this.content = content;
    }

    // Getter
    public Member getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDatePublication(){
        return datePublication;
    }

    public Tree getTree() {
        return tree;
    }

    @Override
    public String toString() {
        StringBuilder reportSTB = new StringBuilder(String.format("[Report INFO]\n"));
        reportSTB.append("\tTree ID : " + tree.getTreeID() + "\n");
        reportSTB.append("\tMember : " + author.getName() + " " + author.getFamilyName() + "\n");
        reportSTB.append("\tPublished Date : " + datePublication + "\n");
        reportSTB.append("\tContent : " + content + "\n");

        return reportSTB.toString();
    }

    public static void main(String[] args){
        Tree t = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);

        Report r = new Report(t,m1, LocalDate.now(), "Ceci est le contenu du report");
        System.out.println(r);
    }
}
