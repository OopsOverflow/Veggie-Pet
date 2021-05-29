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

    public Municipality(String name, String address, String pathToFile) {
        super(name);
        this.address = address;
        String fileName = new File(pathToFile).getAbsolutePath();
        this.trees = ParserCSV.parseDataFromCSVFile(fileName);
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Tree> getTrees() {
        return (ArrayList<Tree>) trees.clone();
    }


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

    boolean addTreeToTrees(Tree tree){
        if (!trees.contains(tree)){
            trees.add(tree);
            return true;
        }
        return false;
    }

    boolean removeTreeFromTrees(Tree tree){
        return trees.remove(tree);
    }

    public void classifyTree(Tree tree) {
        if (!tree.isRemarkable()) {
            tree.setRemarkable(true);
            gs.classifyTree(tree);
        }
        else{
            System.err.println("Tree already remarkable");
        }
    }

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
