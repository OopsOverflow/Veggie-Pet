package com.entity.admin;

import com.entity.Entity;
import com.veggie.Tree;
import com.veggie.ParserCSV;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;


public class Municipality extends Entity {
    String address;
    ArrayList<Tree> trees;
    final GreenSpaces gs = new GreenSpaces(this);
    private Map<Integer, Integer> votedTrees;


    // Getter

    /**
     * Permet d'obtenir un clone de la liste d'arbre de la municipalité
     * @return un ArrayList d'arbre clone de l'ArrayList d'arbre de la municipalité
     */
    public ArrayList<Tree> getTrees() {
        return (ArrayList<Tree>) trees.clone();
    }

    // Uses test CSV file by default
    public Municipality(String name, String address){
        super(name);
        this.address = address;

    }


    public Municipality(String name, String address, ArrayList<Tree> trees) {
        super(name);
        this.address = address;
        this.trees = trees;
    }


    /**
     * Constructeur d'une municipalité
     * @param name le nom de la municipalité
     * @param address l'adresse de la municipalité
     * @param pathToFile le chemin du fichier .csv correspondant à la liste d'arbre de la municipalité
     */
    public Municipality(String name, String address, String pathToFile) {
        super(name);
        this.address = address;
        String fileName = new File(pathToFile).getAbsolutePath();
        this.trees = ParserCSV.parseDataFromCSVFile(fileName);
    }

    /**
     * Permet d'obtenir l'adresse d'une municipalité
     * @return l'adresse d'une municipalité
     */
    public String getAddress() {
        return address;
    }




    /**
     * Méthode modélisant la classification d'arbres les plus votés.
     * @param votedTrees la map des arbres les plus votés par les membres d'une association
     */
    public void setVotedTrees(Map<Integer, Integer> votedTrees) {
        this.votedTrees = votedTrees;
        votedTrees.forEach((k,v) ->
        {
            this.trees.forEach(x->{
                if (x.getTreeID().equals(k)){
                    classifyTree(x);
                    // Let the GreenSpace send notification
                    this.gs.classifyTree(x);}
            });
        });
    }

    /**
     * Méthode modélisant l'ajout d'un arbre a la liste d'arbre d'une municipalité
     * @param tree l'arbre à ajouter
     * @return un booléen modélisant le succès de l'ajout
     */
    boolean addTreeToTrees(Tree tree){
        if (!trees.contains(tree)){
            trees.add(tree);
            return true;
        }
        return false;
    }

    /**
     * Méthode modélisant la suppression d'un arbre de la liste d'arbre d'une municipalité
     * @param tree l'arbre à supprimer
     * @return un booléen modélisant le succès de la suppression
     */
    boolean removeTreeFromTrees(Tree tree){
        return trees.remove(tree);
    }

    /**
     * Méthode modélisant la classification d'un arbre
     * @param tree l'arbre à classifier
     */
    public void classifyTree(Tree tree) {
        if (!tree.isRemarkable()) {
            tree.setRemarkable(true);
            gs.classifyTree(tree);
        }
        else{
            System.err.println("Tree already remarkable");
        }
    }

    /**
     * Méthode modélisant la classification d'un arbre
     * @param tree l'arbre à classifier
     * @param time la date de la classification
     */
    public void classifyTree(Tree tree, LocalDateTime time) {
        if (!tree.isRemarkable()) {
            tree.setRemarkable(true);
            gs.classifyTree(tree, LocalDateTime.now());
        }
        else{
            System.err.println("Tree already remarkable");
        }
    }
}
