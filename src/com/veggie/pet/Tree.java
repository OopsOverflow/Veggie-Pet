package com.veggie.pet;

import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.tuple.Pair;


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
    }

    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    @Override
    public String toString(){
        StringBuilder treeSTB = new StringBuilder(String.format("[Tree %d INFO]\n", this.treeID));
        treeSTB.append("\tGenre : \t" + genre + "\n");
        treeSTB.append("\tSpecie :\t" + specie + "\n");
        treeSTB.append("\tHeight (Meters) : \t" + height + "\n");
        treeSTB.append("\tCircumference (Centimeters) : \t" + circumference + "\n");
        treeSTB.append("\tDevelopment Stage : \t" + developmentStage + "\n");
        treeSTB.append("\tAddress : \t" + address + "\n");
        treeSTB.append("\tGPS Coordinates : \t" + GPS[0] + "," + GPS[1] + "\n");
        treeSTB.append("\tRemarkable : \t" + remarkable + "\n");


        return treeSTB.toString();
    }

    public static void main(String[] args){
        Tree t = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);
        System.out.println(t.toString());
    }

}
