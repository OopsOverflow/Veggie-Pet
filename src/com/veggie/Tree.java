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
    private Date lastVisit;


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
        this.lastVisit = new Date(1900,01,01);
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
    public Date getLastVisit() {
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

    /**
     * Méthode static qui permet d'avoir un comparateur qui opère sur la date d'un arbre.
     * Grâce à cela, on pourra comparer deux arbres avec leurs dates de dernières visites.
     * Utile dans la classe ORGANISATION notamment
     * @return soit 0 soit 1 soit -1 selon si les dates sont respectivement égales, o1 plus jeune que o2,
     * o1 plus vieille que o2. o1 la date de l'arbre 1 et o2 la date de l'arbre 2
     */
    public static Comparator<Tree> ComparatorTree = new Comparator<Tree>() {
        @Override
        public int compare(Tree o1, Tree o2) {
            return o1.getLastVisit().compareTo(o2.getLastVisit());
        }
    };


    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    @Override
    public String toString(){
        StringBuilder treeSTB = new StringBuilder(String.format("[Tree %d INFO]\n", this.treeID));
        treeSTB.append("\tGenre : \t" + genre + "\n");
        treeSTB.append("\tSpecie :\t" + specie + "\n");
        treeSTB.append("\tCommon Name :\t" + commonName + "\n");
        treeSTB.append("\tHeight (Meters) : \t" + height + "\n");
        treeSTB.append("\tCircumference (Centimeters) : \t" + circumference + "\n");
        treeSTB.append("\tDevelopment Stage : \t" + developmentStage + "\n");
        treeSTB.append("\tAddress : \t" + address + "\n");
        treeSTB.append("\tGPS Coordinates : \t" + GPS[0] + "," + GPS[1] + "\n");
        treeSTB.append("\tRemarkable : \t" + remarkable + "\n");
        treeSTB.append("\tLast Visit : \t" + lastVisit + "\n");


        return treeSTB.toString();
    }

    public static void main(String[] args){
        Tree t = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);
        System.out.println(t.toString());
    }

}
