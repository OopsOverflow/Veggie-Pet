package com.system;

import com.entity.admin.Municipality;
import com.entity.org.Organisation;
import com.entity.person.Member;
import com.veggie.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Report {
    private Tree tree = null;
    private Member member = null;
    private Organisation organisation = null;
    private LocalDate datePublication;
    private String content = "";
    private String subject;
    private File record;

    // Si on a le temps ajouter une feature pour lire un fichier texte pour le 'content'
    public Report(Tree tree, Member author, String subject, LocalDate datePublication, String content) {
        this.tree = tree;
        this.member = author;
        this.datePublication = datePublication;
        this.content = content;
        this.subject = subject;
    }

    public Report(Organisation author, String subject, LocalDate datePublication, File record) {
        this.organisation = author;
        this.datePublication = datePublication;
        this.subject = subject;
        this.record = record;
    }

    // Getters

    /**
     * Permet d'obtenir le contenu du rapport
     * @return un String avec le contenu du rapport
     */
    public String getContent() {
        return content;
    }

    /**
     * Permet d'obtenir la date de publication du rapport
     * @return la date de publication du rapport
     */
    public LocalDate getDatePublication(){
        return datePublication;
    }

    /**
     * Permet d'obtenir l'arbre sur lequel le rapport est fait
     * @return l'arbre sur lequel le rapport est fait
     */
    public Tree getTree() {
        return tree;
    }

    /**
     * Permet d'obtenir le membre auteur du rapport
     * @return le membre auteur du rapport
     */
    public Member getMember() {
        return member;
    }

    /**
     * Permet d'obtenir l'organisation en rapport avec le rapport ( :D )
     * @return l'organisation en question
     */
    public Organisation getOrganisation() {
        return organisation;
    }

    @Override
    public String toString() {
        StringBuilder reportSTB = new StringBuilder(String.format("[Report INFO]\n"));
        reportSTB.append("\tPublishing Date : " + datePublication + "\n");
        reportSTB.append("\tSubject : " + subject + "\n");

        // If the author of the report is a member, prints this
        if (tree != null) {
            reportSTB.append("\tTree ID : " + tree.getTreeID() + "\n");
            reportSTB.append("\tContent : \n" + content + "\n");
        }

        // If the author of the report is an organisation it'll
        // loop on the file to write the report
        if (organisation != null) {
            reportSTB.append("\tFrom : " + organisation.getName() + "\n");
            try (Scanner myReader = new Scanner(record)){
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    content += data + "\n";
                }
            } catch (FileNotFoundException e) {
                System.err.println("ERROR : RECORD NOT FOUND");
                e.printStackTrace();
            }

            reportSTB.append("\tContent : \n" + content + "\n");
        }


        return reportSTB.toString();
    }

    public static void main(String[] args){
        Tree t = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998,04,30), "Somewhere not far from Tunis",
                new Date(2021,05,23), false, 5000);


        //Municipality muni = new Municipality()

        //Organisation org = new Organisation("Tree Lovers", 100.0f, m1, );

        //Report r = new Report(t,m1, "Some", LocalDate.now(), "Ceci est le contenu du report");
        //System.out.println(r);
    }
}
