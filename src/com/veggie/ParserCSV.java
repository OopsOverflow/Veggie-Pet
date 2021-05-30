package com.veggie;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ParserCSV {

    /**
     * Méthode permettant d'obtenir des données a partir d'un fichier CSV.
     * @param fileName le fichier en question
     * @return un ArrayList d'arbre
     */
    public static ArrayList<Tree> parseDataFromCSVFile(String fileName){

        Integer treeID;
        String address;
        Integer idEmplacement;
        String commonName;
        String genre;
        String specie;
        String variety;
        Integer circumference;
        Integer height;
        String developmentStage;
        boolean remarkable;
        Float[] GPS = new Float[2];

        ArrayList<Tree> parsedData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                // Check if line is parsable
//                if (lineInArray.length != 16) {
//                    System.err.println("Can't Parse Data at Line " + reader.getLinesRead() +
//                            "Incorrect Number of Columns, Skipping ...");
//                    continue;
//                }

                // Handle Potential Parsing Errors First

                // Parse ID
                try {
                    treeID = Integer.parseInt(lineInArray[0]);
                } catch (NumberFormatException e) {
                    treeID = null;
                }

                try {
                    circumference = Integer.parseInt(lineInArray[12]);
                } catch (NumberFormatException e) {
                    circumference = null;
                }

                try {
                    height = Integer.parseInt(lineInArray[13]);
                } catch (NumberFormatException e) {
                    height = null;
                }

                try {
                    String[] raw = lineInArray[16].split(",");
                    GPS[0] = Float.parseFloat(raw[0]);
                    GPS[1] = Float.parseFloat(raw[1]);
                } catch (NumberFormatException e) {
                    GPS[0] = null;
                    GPS[1] = null;
                }

                // Handle The Rest of Data

                // Parse Common Name
                commonName = lineInArray[8];

                // Parse Address
                address = lineInArray[2] + " " + lineInArray[3] + lineInArray[4] + lineInArray[6];

                // Parse genre
                genre = lineInArray[9];

                // Parse specie
                specie = lineInArray[10];

                // Parse variety
                variety = lineInArray[11];

                // Parse development stage
                developmentStage = lineInArray[14];

                // Parse remarkable
                remarkable = lineInArray[15].toLowerCase().equals("oui");

                // Add new tree to list
                Tree tr = new Tree(treeID, commonName, circumference, height, specie,
                        genre, developmentStage, address, GPS, remarkable);

                parsedData.add(new Tree(treeID, commonName, circumference, height, specie,
                        genre, developmentStage, address, GPS, remarkable));

                //System.out.println(tr.toString());
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return parsedData;
    }

}





