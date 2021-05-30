package com.veggie.app;

import com.entity.admin.GreenSpaces;
import com.entity.admin.Municipality;
import com.entity.org.Organisation;
import com.entity.person.Member;
import com.entity.person.Person;
import com.system.Report;
import com.veggie.ParserCSV;
import com.veggie.Tree;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) throws Exception {

        // Prepare to Parse the Data
        String pathToFile = "";
        String choice;

        if (args.length == 0){
            do {
                System.out.println("No Arguments Found, Would You Like To Run The Program With a Limited Number of Data Lines");
                System.out.println("Yes/No?");
                Scanner sc = new Scanner(System.in);
                choice = sc.next().toLowerCase();
                if (choice.equals("no"))
                    return;
                if (choice.equals("yes"))
                    pathToFile = "files/testData.csv";

            } while (!(choice.equals("yes") || choice.equals("no")));
        }
        else if(args.length > 1){
            System.err.println("ERROR : TOO MANY ARGUMENTS");
            return;
        }
        else {
            pathToFile = args[0];
        }

        System.out.println("CSV File Path ");
        System.out.println("\n\t\t<=== Création d'objet et Base de données ===>\n");

        String fileName = new File(pathToFile).getAbsolutePath();
        System.out.println(fileName + "\n");

        // Parse Data Into Array
        ArrayList<Tree> trees = ParserCSV.parseDataFromCSVFile(fileName);

        // Create Municipality
        Municipality paris = new Municipality("Paris", "SomeWhere", trees);
        GreenSpaces parisGS = new GreenSpaces(paris);

        // Get Some Trees
        Tree tree1 = paris.getTrees().get(0);
        Tree tree2= paris.getTrees().get(1);
        Tree tree3 = paris.getTrees().get(2);
        Tree tree4 = paris.getTrees().get(3);
        Tree tree5 = paris.getTrees().get(4);
        Tree tree6 = paris.getTrees().get(5);
        Tree tree7 = paris.getTrees().get(6);
        Tree tree8 = paris.getTrees().get(7);

        // Create Members
        System.out.print("Création de membres ");
        for(int i = 0 ; i < 5 ; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.print(" .");
        }
        Member m1 = new Member("Andrea", "Cappo", new Date(1998, 4, 30), "Palermo",
                new Date(2021, 5, 23), false, 5000);

        Member m2 = new Member("Pablo", "Elenore", new Date(2001, 10, 29), "Milan",
                new Date(2021, 5, 23), false, 5000);

        Member m3 = new Member("Juan", "DeJea", new Date(1995, 10, 28), "Mexico",
                new Date(2021, 5, 24), false, 10000);

        Member m4 = new Member("Stan", "Lee", new Date(1999, 5, 1), "Antony",
                new Date(2021, 5, 25), false, 15000);

        Member m5 = new Member("Tarik", "Doha", new Date(1996, 5, 1), "Dubai",
                new Date(2021, 5, 25), false, 15000);

        // Create Person
        Person p = new Person("Ice", "Cube", new Date(1970, 3, 15), "San Marino");

        // Create Organisations
        System.out.print("\nAssociation : Creating Data Bases & Report Files");
        for(int i = 0 ; i < 5 ; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.print(" .");
        }
        System.out.print("\n");
        Organisation TreeLovers = new Organisation("Tree Lovers", 10000.0f, paris, m1, m2, m3, m4, m5);
        Organisation peta = new Organisation("PETA", 5000000.0f, paris);
        TimeUnit.SECONDS.sleep(1);

        System.out.println("\n\t\t<=== End ===>\n");
        System.out.print("\t\t");
        for(int i = 0 ; i < 10 ; i++){
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.print(".  ");
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.print("\n");
        TimeUnit.SECONDS.sleep(2);
        System.out.print("\n");
        System.out.println("\t\t<=== Contributions and Financial Operations ===>\n");
        TimeUnit.SECONDS.sleep(3);
        // Paying Contributions
        m1.payContribution(TreeLovers, 100);
        TimeUnit.SECONDS.sleep(2);
        m2.payContribution(TreeLovers, 100);
        TimeUnit.SECONDS.sleep(2);
        m3.payContribution(TreeLovers, 100);
        TimeUnit.SECONDS.sleep(2);
        m4.payContribution(TreeLovers, 100);
        TimeUnit.SECONDS.sleep(2);
        m5.payContribution(TreeLovers, 100);
        TimeUnit.SECONDS.sleep(2);

        System.out.print("\n");

        // Paying Bills
        TreeLovers.payBill(5000);
        System.out.print("\n");
        TimeUnit.SECONDS.sleep(1);
        // Asking for donations
        System.out.println("\tAsking For Donations...\n");
        TimeUnit.SECONDS.sleep(3);
        TreeLovers.addDonor(peta);
        TreeLovers.addDonor(p);
        TreeLovers.askForDonations();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\tAffichage de la boîte de reception de PETA (Exemple) \n");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\t\tPETA Notification Box");
        TimeUnit.SECONDS.sleep(2);
        System.out.println(peta.getLetterBox().toString());

        System.out.print("\t\t");

        for(int i = 0 ; i < 10 ; i++){
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.print(".  ");
        }

        // Trees
        System.out.println("\n\n\t\t<=== Manipulating Trees & Notification System ===>\n");
        TimeUnit.SECONDS.sleep(3);
        TreeLovers.subscribeToNewsLetter(parisGS);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\n\t\t\t----VOTING 🌲-----");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\n\t[Andrea] vote pour les arbres 1, 2, 3, 4, 5 et 8");
        m1.vote(tree1, tree2, tree3, tree4, tree5, tree8);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(String.format("\n\t[%s] vote pour les arbres 1, 2, 3, 4, 5", m2.getName()));
        m2.vote(tree1, tree2, tree3, tree4, tree5);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(String.format("\n\t[%s] vote pour les arbres 1, 2, 4, 6, 8", m3.getName()));
        m3.vote(tree1, tree2, tree6, tree4, tree8);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(String.format("\n\t[%s] vote pour les arbres 1, 2, 4, 6, 8", m4.getName()));
        m4.vote(tree1, tree2, tree6, tree4, tree8);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(String.format("\n\t[%s] vote pour les arbres 1, 7, 3, 8, 2", m5.getName()));
        m5.vote(tree1, tree7, tree3, tree8, tree2);
        TimeUnit.SECONDS.sleep(2);


        // Visits
        TimeUnit.SECONDS.sleep(2);
        System.out.println("\n\n\t\t\t---- VISIT 🌲 ----");
        TimeUnit.SECONDS.sleep(2);
        TreeLovers.doAllVisitFx();

        // TEST DES FONCTIONALITÉS

        //Tree2 est un arbre remarquable | Tree3 ne l'est pas
        // Le membre 1 se porte volontaire pour faire une visite de l'arbre 2
        // Cette visite va être un succès car l'arbre est remarque et aucun autre membre n'a reservé de visite.
        if(m2.toVolunteerOn(TreeLovers, tree2)){
            System.out.println(String.format("\n\t[%s] Your visit has been accepted. FOR THE TREES!", m2.getName()));
            m2.submitReport(tree2, new Date(2021, 5, 30), TreeLovers);
        }
        else{
            System.out.println(String.format("\n\t[%s] Your visit has not been accepted. Sorry", m2.getName()));
        }

        // Le membre 1 se porte volontaire pour faire une visite de l'arbre 3
        // Cette visite va être refusé car l'arbre n'est pas remarquable
        if(m2.toVolunteerOn(TreeLovers, tree3)){
            System.out.println(String.format("\n\t[%s] Your visit has been accepted. FOR THE TREES!", m2.getName()));
        }
        else{
            System.out.println(String.format("\n\t[%s] Your visit has not been accepted. Sorry", m1.getName()));
        }

        // Le membre 2 se porte volontaire pour faire une visite de l'arbre 2
        // Cette visite va être refusé car une visite a dejà été programmé pour l'arbre 2
        if(m1.toVolunteerOn(TreeLovers, tree2)){
            System.out.println(String.format("\n\t[%s] Your visit has been accepted. FOR THE TREES!", m1.getName()));
        }
        else{
            System.out.println(String.format("\n\t[%s] Your visit has not been accepted. Sorry", m1.getName()));
        }

        System.out.println();
        System.out.println(m2.getName() + " Wants to see the Data the organisation keeps on him");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(m2.getMyData(TreeLovers));
        System.out.println("Data is Encrypted only the user can see it using his unique 256 bit key.");

        System.out.println();
        TimeUnit.SECONDS.sleep(2);
        // Leaving the Organisation
        System.out.println(m1.getName() + " Wants to leave the Organisation");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("All his personal data will be deleted, but they keep track of his past activities.");
        System.out.println();
        TimeUnit.SECONDS.sleep(1);
        m1.leaveOrganisation(TreeLovers);

        System.out.println();

        System.out.println();
        System.out.println("----- Tiding Things up ... ------");
        TimeUnit.SECONDS.sleep(2);

        do {
            System.out.println("Would you Like to Delete the Created Files & Data Bases [Yes/No]?");
            Scanner sc = new Scanner(System.in);
            choice = sc.next().toLowerCase();
        }while (!(choice.equals("yes") || choice.equals("no")));

        if (choice.equals("yes")) {
            File data = new File("src\\com\\entity\\org\\TreeLoversFinancialRecord2021.txt");
            File datab = new File("src\\com\\entity\\org\\TreeLovers.db");
            File datab2 = new File("src\\com\\entity\\org\\PETA.db");
            File data2 = new File("src\\com\\entity\\org\\PETAFinancialRecord2021.txt");
            data.delete();
            data2.delete();
            datab.delete();
            datab2.delete();
        }
        else
            return;

    }

}
