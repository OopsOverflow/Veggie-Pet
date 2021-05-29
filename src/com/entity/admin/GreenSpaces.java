package com.entity.admin;

import com.entity.Entity;
import com.veggie.*;
import com.system.NotificationManager;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GreenSpaces extends Entity{
    private final Municipality municipality;
    private ArrayList<Entity> entitiesToNotify;

    /**
     * Constructeur d'un Espace Vert
     * @param municipality la municipalité de cet espace vert
     */
    public GreenSpaces(Municipality municipality) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
    }

    /**
     * Constructeur d'un Espace Vert
     * @param municipality la municipalité de cet espace vert
     * @param entities énumeration des entités de cet espace vert
     */
    public GreenSpaces(Municipality municipality, Entity ... entities) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
        for (Entity e : entities){
            this.addToNewsLetter(e);
        }
    }

    /**
     * Constructeur d'un Espace Vert
     * @param municipality la municipalité de cet espace vert
     * @param entitiesToNotify un ArrayList d'entité qui vont être destinataires de notification venant de cet
     *                         espace vert
     */
    public GreenSpaces(Municipality municipality, ArrayList<Entity> entitiesToNotify) {
        super(municipality.getName() + "'s Green Space");
        this.municipality = municipality;
        // Ensure there are no duplicates within the list
        entitiesToNotify.forEach(this::addToNewsLetter);
    }


    /**
     * Méthode modélisant l'ajout d'une entité a la liste de diffusion de notification
     * @param e l'entité à ajouter
     */
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

    /**
     * Méthode modélisant la classification d'un arbre (envoi de la notification associée)
     * @param tree l'arbre classifié
     * @param time la date de la classification
     * @return null
     */
    public TimerTask classifyTree(Tree tree, LocalDateTime time){
        NotificationManager.diffuseNotification(this, entitiesToNotify,
                    "A local Tree Has Been Recognized as Remarkable! Check It Out :" +
                            tree.getAddress(), time);
        return null;
    }

    /**
     * Méthode modélisant la classification d'un arbre (envoi de la notification associée
     * @param tree l'arbre classifié
     */
    // Classify with known date
    public void classifyTree(Tree tree){
        // The Notification can be sent later
        Timer timer = new Timer(); // creating timer
        TimerTask task = this.classifyTree(tree, LocalDateTime.now()); // creating timer task
        timer.schedule(task, new Date()); // scheduling the task

    }

    /**
     * Méthode modélisant la plantation d'un arbre
     * @param tree l'arbre planté
     */
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

    /**
     * Méthode modélisant la coupe d'un arbre
     * @param tree l'arbre coupé
     */
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
