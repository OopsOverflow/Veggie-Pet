package com.system;

import com.entity.admin.Organisation;
import com.entity.entity;
import com.entity.person.Member;
import com.veggie.pet.Tree;

import java.util.Date;

public class Report {
    private Tree tree = null;
    private entity author;
    private final Date datePublication;
    private final String content;


    // Si on a le temps ajouter une feature pour lire un fichier texte pour le 'content'
    public Report(Tree tree, Member author, Date datePublication, String content) {
        this.tree = tree;
        this.author = author;
        this.datePublication = datePublication;
        this.content = content;
    }

    public Report(Organisation organisation, Date datePublication, String content) {
        this.datePublication = datePublication;
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        if (tree == )
        sb.append("tree=").append(tree);
        sb.append(", author=").append(author);
        sb.append(", organisation=").append(organisation);
        sb.append('}');
        return sb.toString();
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
