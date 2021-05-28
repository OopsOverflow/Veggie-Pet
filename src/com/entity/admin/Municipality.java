package com.entity.admin;

import com.entity.Entity;
import com.veggie.Tree;
import com.veggie.ParserCSV;

import java.io.File;
import java.util.ArrayList;


public class Municipality extends Entity {
    String address;
    ArrayList<Tree> trees;

    // Uses test CSV file by default
    public Municipality(String name, String address){
        super(name);
        this.address = address;

    }

    public Municipality(String name, String address, String pathToFile) {
        super(name);
        this.address = address;
        String fileName = new File(pathToFile).getAbsolutePath();
        this.trees = ParserCSV.parseDataFromCSVFile(fileName);
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




}
