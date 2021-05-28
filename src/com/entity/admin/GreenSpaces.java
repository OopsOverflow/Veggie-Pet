package com.entity.admin;

import com.entity.Entity;
import com.veggie.*;


import java.util.ArrayList;

public class GreenSpaces {
    private final Municipality municipality;
    private ArrayList<Entity> entitiesToNotify;

    public GreenSpaces(Municipality municipality) {
        this.municipality = municipality;
    }

    public GreenSpaces(Municipality municipality, Entity ... entities) {
        this.municipality = municipality;
        for (Entity e : entities){
            this.addToNewsLetter(e);
        }
    }

    public GreenSpaces(Municipality municipality, ArrayList<Entity> entitiesToNotify) {
        this.municipality = municipality;
        // Ensure there are no duplicates within the list
        entitiesToNotify.forEach(this::addToNewsLetter);
    }


    // TODO: 28/05/2021 INTEGRATE NOTIFICATIONS
    public void addToNewsLetter(Entity e){
        // Check if entity is already in the NewsLetter list
        if (!entitiesToNotify.contains(e)){
            entitiesToNotify.add(e);
            System.out.println(e.getName() + "Has Been Added to NewsLetter.");
            return;
        }
        System.out.println(e.getName() + " Is Already Subscribed to The NewsLetter.");
    }

    public void classifyTree(Tree tree){
        tree.setRemarkable(true);
    }

    public void plantTree(Tree tree){
        if (municipality.addTreeToTrees(tree))
            System.out.println("Tree Was Successfully Planted!");
        else
            System.out.println(String.format("[MUNICIPALITY %s] ERROR : TREE ALREADY EXISTS!", municipality.getName()));
    }

    public void cutDownTree(Tree tree){
        // Ensure that tree is in the list
        if (municipality.removeTreeFromTrees(tree))
            System.out.println("Tree Successfully Cut Down :(");
        else
            System.out.println(String.format("[MUNICIPALITY %s] ERROR : TREE DOESN'T EXISTS!", municipality.getName()));
    }
}
