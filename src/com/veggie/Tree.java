package com.veggie;

import com.system.Report;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class Tree {
    
    private Integer treeID;    
    private String commonName;    
    private Integer circumference;    
    private Integer height;    
    private String specie;    
    private String genre;    
    private String developmentStage;    
    private String address;
    private Float[] GPS;
    private boolean remarkable;
    private ArrayList<Report> listReport;
    private LocalDate lastVisit;


    // Constructor
    /**
     * Constructeur de TREE
     * @param address l'adresse de l'arbre
     * @param circumference la circonférence de l'arbre
     * @param commonName la nom courant de l'arbre
     * @param developmentStage l'état de développement de l'arbre
     * @param genre le genre de l'arbre
     * @param GPS la position GPS de l'arbre
     * @param height la taille de l'arbre
     * @param remarkable si l'arbre est remarquable ou pas
     * @param specie l'espece de l'arbre
     * @param treeID l'ID de l'arbre
     */
    public Tree(Integer treeID, String commonName, Integer circumference, Integer height, String specie,
                String genre, String developmentStage, String address,
                Float[] GPS, boolean remarkable) {
        this.treeID = treeID;
        this.commonName = commonName;
        this.circumference = circumference;
        this.height = height;
        this.specie = specie;
        this.genre = genre;
        this.developmentStage = developmentStage;
        this.address = address;
        this.GPS = GPS;
        this.remarkable = remarkable;
        this.lastVisit = LocalDate.MIN;
    }

    // Getters & Setters
    /**
     * Permet d'obtenir l'identifiant de l'arbre (unique)
     * @return l'identifiant de l'arbre
     */
    public Integer getTreeID() {
        return treeID;
    }

    /**
     * Permet d'obtenir le nom courant de l'arbre
     * @return le nom courant de l'arbre
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Permet d'obtenir la circonférence de l'arbre
     * @return la circonférence de l'arbre
     */
    public Integer getCircumference() {
        return circumference;
    }

    /**
     * Permet d'obtenir la hauteur de l'arbre
     * @return la hauteur de l'arbre
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Permet d'obtenir l'espèce de l'arbre
     * @return l'espèce de l'arbre
     */
    public String getSpecie() {
        return specie;
    }

    /**
     * Permet d'obtenir le genre de l'arbre
     * @return le genre de l'arbre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Permet d'obtenir l'état de developpement de l'arbre
     * @return l'état de developpement de l'arbre
     */
    public String getDevelopmentStage() {
        return developmentStage;
    }

    /**
     * Permet d'obtenir l'adresse de l'arbre
     * @return l'adresse de l'arbre
     */
    public String getAddress() {
        return address;
    }

    /**
     * Permet d'obtenir les coordonées GPS de l'arbre
     * @return les coordonées GPS de l'arbre
     */
    public Float[] getGPS() {
        return GPS;
    }

    /**
     * Permet d'obtenir l'informations sur la remarquabilité de l'arbre (pas sûr du français de ce mot)
     * @return un booléen true si l'arbre est remarquable, false sinon
     */
    public boolean isRemarkable() {
        return remarkable;
    }

    /**
     * Permet d'obtenir la date de la dernière visite d'un membre d'association sur l'arbre
     * @return la date de la dernière visite
     */
    public LocalDate getLastVisit() {
        return lastVisit;
    }

    /**
     * Permet d'obtenir la liste des rapports concernant l'arbre
     * @return un ArrayList de REPORT modélisant la liste des rapports
     */
    public ArrayList<Report> getListReport() {
        return listReport;
    }

    /**
     * Permet de modifier le booléen correspondant à la remarquabilité de l'arbre
     * @param remarkable la nouvelle valeur du booléen
     */
    public void setRemarkable(boolean remarkable) {
        if(remarkable)
            System.err.println(String.format("[Tree %d] ERROR : TREE IS ALREADY REMARKABLE", treeID));
        else
            this.remarkable = remarkable;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    /**
     * Méthode static qui permet d'avoir un comparateur qui opère sur la date d'un arbre.
     * Grâce à cela, on pourra comparer deux arbres avec leurs dates de dernières visites.
     * Utile dans la classe ORGANISATION notamment
     */
    public static Comparator<Tree> ComparatorTree = new Comparator<Tree>() {
        @Override
        public int compare(Tree o1, Tree o2) {
            return o1.getLastVisit().compareTo(o2.getLastVisit());
        }
    };


}
