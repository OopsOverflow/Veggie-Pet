package com.system;

import com.entity.person.Member;
import com.veggie.pet.Tree;

import java.util.Date;

public class Report {
    public Tree tree;
    public Member author;
    public static Date datePublication;
    public static String content;

    // Si on a le temps ajouter une feature pour lire un fichier texte pour le 'content'
    public Report(Tree tree, Member author, Date datePublication, String content) {
        this.tree = tree;
        this.author = author;
        this.datePublication = datePublication;
        this.content = content;
    }

    public Member getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDatePublication(){
        return datePublication;
    }
}
