package com.entity;

import com.entity.admin.GreenSpaces;
import com.entity.org.Organisation;

public class Entity {
    private String name;
    protected StringBuilder letterBox = new StringBuilder();

    /**
     * Constructeur d'une entité
     * @param name le nom de l'entité
     */
    public Entity(String name){
        this.name = name;
    }

    /**
     * Méthode modélisant la donation d'une entité à une organisation
     * @param amount le montant de la donation
     * @param org l'organisation
     */
    public void donate(float amount, Organisation org){
        org.recieveFunds(amount);
    }

    // Getters & Setters

    /**
     * Permet d'obtenir le nom de l'entité
     * @return le nom de l'entité
     */
    public String getName() {
        return name;
    }

    /**
     * Permet de changer le nom d'une entité
     * @param name le nouveau nom de l'entité
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Permet d'obtenir la letterBox d'une entité
     * @return la letterBox de l'entité
     */
    public StringBuilder getLetterBox() {
        return letterBox;
    }

    /**
     * Méthode modélisant l'ajout d'une notification a la boite de reception d'une entité
     * @param notification la notification à ajouter
     */
    public void appendLetterBox(String notification) {
        if (!letterBox.toString().contains(notification))
            letterBox.append(notification);
        else
            System.out.println("Notification Rejected, Already Received");
    }

    public void subscribeToNewsLetter(GreenSpaces gs){
        gs.addToNewsLetter(this);
    }
}
