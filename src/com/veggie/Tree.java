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
    public Integer getTreeID() {
        return treeID;
    }

    public String getCommonName() {
        return commonName;
    }

    public Integer getCircumference() {
        return circumference;
    }

    public Integer getHeight() {
        return height;
    }

    public String getSpecie() {
        return specie;
    }

    public String getGenre() {
        return genre;
    }

    public String getDevelopmentStage() {
        return developmentStage;
    }

    public String getAddress() {
        return address;
    }

    public Float[] getGPS() {
        return GPS;
    }

    public boolean isRemarkable() {
        return remarkable;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public ArrayList<Report> getListReport() {
        return listReport;
    }

    public void setRemarkable(boolean remarkable) {
        if(remarkable)
            System.err.println(String.format("[Tree %d] ERROR : TREE IS ALREADY REMARKABLE", treeID));
        else
            this.remarkable = remarkable;
    }

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
