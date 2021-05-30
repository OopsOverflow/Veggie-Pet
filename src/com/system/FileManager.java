package com.system;

import com.entity.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {

    /**
     * Méthode modélisant la création d'un fichier
     * @param fileName le nom du fichier
     * @return le fichier créé
     */
    public static File createFile(String fileName) {
        try {
            File myObj = new File("src\\com\\entity\\org\\" + fileName + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("Record Successfully Created");
                return myObj;
            } else {
                System.out.println("Connecting to Already existing Record ...");
                return new File("src\\com\\entity\\org\\" + fileName + ".txt");
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
            return null;
        }
    }

    /**
     * Méthode modélisant l'écriture d'une opération
     * @param amount le montant de l'opération
     * @param budget le nouveau budget
     * @param record le fichier
     * @param origin l'entité qui écrit
     * @param typeOfOperation le type d'opération
     * @return un booléen modélisant la réussite de l'opération
     */
    public static boolean writeToRecord(Entity origin, File record, String typeOfOperation, float amount, float budget){
        try(FileWriter writer = new FileWriter("src\\com\\entity\\org\\" + record.getName(),true)){
            writer.write(String.format("[%s]\nNEW FINANCIAL OPERATION==========\n",origin.getName()));
            writer.write("\tDate : " + LocalDateTime.now() + "\n");
            writer.write("\tType : " + typeOfOperation + "\n");
            writer.write("\tAmount : " + amount + "\n");
            writer.write("\tNew Budget : " + budget + "\n"); // Change to budget must be done before
            writer.write("END -----------------\n");

            return true;
        }
        catch (IOException e) {
            System.err.println("I/O ERROR : CAN'T WRITE TO FILE" + record.getAbsolutePath());
            e.printStackTrace();
            return false;
        }

    }
}
