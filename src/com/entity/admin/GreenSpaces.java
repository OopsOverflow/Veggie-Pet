package com.entity.admin;

import com.entity.Entity;
import com.veggie.*;
import com.system.NotificationManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GreenSpaces extends Entity{
    private final Municipality municipality;
    private ArrayList<Entity> entitiesToNotify;

    public GreenSpaces(Municipality municipality) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
    }

    public GreenSpaces(Municipality municipality, Entity ... entities) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
        for (Entity e : entities){
            this.addToNewsLetter(e);
        }
    }

    public GreenSpaces(Municipality municipality, ArrayList<Entity> entitiesToNotify) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
        // Ensure there are no duplicates within the list
        entitiesToNotify.forEach(this::addToNewsLetter);
    }



    public void addToNewsLetter(Entity e){
        // Check if entity is already in the NewsLetter list
        if (!entitiesToNotify.contains(e)){
            entitiesToNotify.add(e);
            System.out.println(e.getName() + "Has Been Added to NewsLetter.");
            NotificationManager.sendNotification(this, e, "You are Now Subscribed to " +
                    "our NewsLetter.", LocalDateTime.now());
            return;
        }
        System.out.println(e.getName() + " Is Already Subscribed to The NewsLetter.");
    }

    public void classifyTree(Tree tree){
        if (! tree.isRemarkable()) {
            tree.setRemarkable(true);
            NotificationManager.diffuseNotification(this, entitiesToNotify,
                    "A New Local Remarkable Tree! Check It Out :" +
                            tree.getAddress(), LocalDateTime.now());
        }
        else
            System.out.println("ERROR : TREE IS ALREADY REMARKABLE");
    }

    // Classify with known date
    public void classifyTree(Tree tree, LocalDateTime time){
        if (! tree.isRemarkable()) {
            tree.setRemarkable(true);
            NotificationManager.diffuseNotification(this, entitiesToNotify,
                    "A Local Tree Has Been Recoginzed as Remakable on " + time.toString()
                            + "! Check It Out :" +
                            tree.getAddress(), LocalDateTime.now());
        }
        else
            System.out.println("ERROR : TREE IS ALREADY REMARKABLE");
    }

    public void plantTree(Tree tree){
        if (municipality.addTreeToTrees(tree)) {
            System.out.println("Tree Was Successfully Planted!");
            NotificationManager.diffuseNotification(this, entitiesToNotify,
                    "A New Tree Was Planted Locally! Check It Out :" +
                            tree.getAddress(), LocalDateTime.now());
        }
        else
            System.out.println(String.format("[MUNICIPALITY %s] ERROR : TREE ALREADY EXISTS!", municipality.getName()));
    }

    public void cutDownTree(Tree tree){
        // Ensure that tree is in the list
        if (municipality.removeTreeFromTrees(tree)) {
            System.out.println("Tree Successfully Cut Down :(");
            NotificationManager.diffuseNotification(this, entitiesToNotify,
                    "Oh NO, A Local Tree Has Been Taken Down :( ! Check It Out :" +
                            tree.getAddress(), LocalDateTime.now());
        }
        else
            System.out.println(String.format("[MUNICIPALITY %s] ERROR : TREE DOESN'T EXISTS!", municipality.getName()));
    }
}
