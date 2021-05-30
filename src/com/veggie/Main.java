package com.veggie;

import com.entity.admin.GreenSpaces;
import com.entity.admin.Municipality;
import com.entity.org.Organisation;
import com.entity.person.Member;
import com.entity.person.Person;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
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

        String fileName = new File(pathToFile).getAbsolutePath();

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
        Member m1 = new Member("Andrea", "Cappo", new Date(1998, 04, 30), "Palermo",
                new Date(2021, 05, 23), false, 5000);

        Member m2 = new Member("Pablo", "Elenore", new Date(2001, 10, 29), "Milan",
                new Date(2021, 05, 23), false, 5000);

        Member m3 = new Member("Juan", "DeJea", new Date(1995, 10, 28), "Mexico",
                new Date(2021, 05, 24), false, 10000);

        Member m4 = new Member("Stan", "Lee", new Date(1999, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

        Member m5 = new Member("Tarik", "Doha", new Date(1996, 05, 01), "Dubai",
                new Date(2021, 05, 25), false, 15000);

        // Create Person
        Person p = new Person("Ice", "Cube", new Date(1970, 03, 15), "San Marino");

        // Create Organisations
        System.out.println("New Organisation : Creating Data Bases & Report Files ...");
        TimeUnit.SECONDS.sleep(2);
        Organisation TreeLovers = new Organisation("Tree Lovers", 10000.0f, paris, m1, m2, m3, m4, m5);
        Organisation peta = new Organisation("PETA", 5000000.0f, paris);

        TimeUnit.SECONDS.sleep(1);
        System.out.println("\nContributions and Financial Operations");
        TimeUnit.SECONDS.sleep(1);
        // Paying Contributions
        m1.payContribution(TreeLovers, 100);
        m2.payContribution(TreeLovers, 100);
        m3.payContribution(TreeLovers, 100);
        m4.payContribution(TreeLovers, 100);
        m5.payContribution(TreeLovers, 100);


        // Paying Bills
        TreeLovers.payBill(5000);
        TimeUnit.SECONDS.sleep(1);
        // Asking for donations
        System.out.println("Asking For Donations...");
        TreeLovers.addDonor(peta);
        TreeLovers.addDonor(p);
        TreeLovers.askForDonations();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Verify Reception of Notifications");
        System.out.println("PETA Notification Box");
        System.out.println(peta.getLetterBox().toString());




//        System.out.println("Tree Lovers Members");
//        System.out.println(TreeLovers.getMembersList());

        // Trees
        System.out.println("Manipulating Trees & Notification System");
        TreeLovers.subscribeToNewsLetter(parisGS);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("----VOTING 🌲-----");
        TimeUnit.SECONDS.sleep(1);
        m1.vote(tree1, tree2, tree3, tree4, tree5, tree8);
        m2.vote(tree1, tree2, tree3, tree4, tree5);
        m3.vote(tree1, tree2, tree6, tree4, tree8);
        m4.vote(tree1, tree2, tree6, tree4, tree8);
        m5.vote(tree1, tree7, tree3, tree8, tree2);


        // Visits
        System.out.println("Manipulating Visit function");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("---- VISIT 🌲 ----");
        TimeUnit.SECONDS.sleep(2);
        TreeLovers.doAllVisitFx();
        System.out.println("Affichage de la mapVisit " + TreeLovers.getMapVisit());
        //System.out.println("Affichage de la file d'attente TreeBlaBla : " +
        //TreeLovers.getListRemarkableTreeNotVisitedForAWhile());

        //tree2 est remarquable
        //tree3 ne l'est pas

        /*if(m1.toVolunteerOn(TreeLovers, TreeLovers.getMunicipality().getTrees().get(5))){
            System.out.println(String.format("[%s] Your visit has been accepted. FOR THE TREES!", m1.getName()));
        }
        else{
            System.out.println(String.format("[%s] Your visit has not been accepted. Sorry", m1.getName()));
        }*/






        File data = new File("TreeLoversFinancialRecord2021.txt");
        File datab = new File("src\\com\\entity\\org\\TreeLovers.db");
        File datab2 = new File("src\\com\\entity\\org\\PETA.db");
        data.delete();
        datab.delete();
        datab2.delete();

        System.out.println(fileName);
    }

}
