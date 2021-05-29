package com.system;

import com.entity.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {

    /**
     * Méthode modélisant la création d'un fichier
     * @return le fichier créé
     */
    public static File createFile(String fileName) {
        try {
            File myObj = new File(fileName + ".txt");
            if (myObj.createNewFile()) {
                return myObj;
            } else {
                System.err.println("File already exists.");
                return new File(fileName + ".txt");
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
            return null;
        }
    }

    /**
     * Méthode modélisant l'écriture d'une opération
     * @return un booléen modélisant la réussite de l'opération
     */
    public static boolean writeToRecord(Entity origin, File record, String typeOfOperation, float amount, float budget){
        try(FileWriter writer = new FileWriter(record.getName(),true)){
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
