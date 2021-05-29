package com.entity.org;

import com.entity.Entity;
import com.entity.admin.Municipality;
import com.entity.person.Member;
import com.system.FileManager;
import com.system.NotificationManager;
import com.system.OrganisationDB;
import com.system.Report;
import com.veggie.Tree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.Date;

import java.io.File;
import java.util.stream.Collectors;


public class Organisation extends Entity {
    private Float budget;
    private static int numMembers = 0;
    private String DBURL;
    ArrayList<Entity> donorsList;
    Municipality municipality;

    // Financial Record
    File financialRecord;
    private ArrayList<MutablePair<Integer, Member>> membersList = new ArrayList<>();

    // Vote
    private ArrayList<Queue<Tree>> listVoteMember = new ArrayList<>();
    private Map<Integer, Integer> mapTreeVote = new HashMap<>();
    private Map<Integer, Integer> voteRanking = new HashMap<>();

    // Visit
    private Map<Integer, Boolean> mapVisit = new HashMap<>();
    private Deque<Tree> listRemarkableTreeNotVisitedForAWhile = new LinkedList<>();

    /**
     * Constructeur de l'association
     * @param budget le budget de l'association
     * @param donorsList la liste des donateurs
     * @param members une énumeration de personne membre de l'association
     * @param muni la municipalité liée à l'association
     * @param name le nom de l'association
     */
    public Organisation(String name, Float budget, ArrayList<Entity> donorsList, Municipality muni, Member... members) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        OrganisationDB.createMembersTable(DBURL);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    m.getDateOfBirth(), m.getLastRegistrationDate(), "");
        }
        this.financialRecord = FileManager.createFile(name.replaceAll("\\s+","")  +
                "FinancialRecord" + LocalDateTime.now().getYear());
        this.municipality = muni;
    }

    /**
     * Constructeur de l'association
     * @param budget le budget de l'association
     * @param donorsList la liste des donateurs
     * @param members un ArrayList de personne membre de l'association
     * @param muni la municipalité liée à l'association
     * @param name le nom de l'association
     */
    public Organisation(String name, Float budget, ArrayList<Entity> donorsList, ArrayList<Member> members, Municipality muni) {
        super(name);
        this.budget = budget;
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        OrganisationDB.createMembersTable(DBURL);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    m.getDateOfBirth(), m.getLastRegistrationDate(), "");
        }
        // .replaceAll() removes white spaces from name
        // Better indexing on cross platform
        this.financialRecord = FileManager.createFile(name.replaceAll("\\s+","")  +
                "FinancialRecord" + LocalDateTime.now().getYear());
    }

    /**
     * Constructeur de l'association
     * @param budget le budget de l'association
     * @param muni la municipalité liée à l'association
     * @param name le nom de l'association
     */
    public Organisation(String name, Float budget, Municipality muni) {
        super(name);
        this.budget = budget;
        this.DBURL = OrganisationDB.connect(name);
        OrganisationDB.createMembersTable(DBURL);
        this.financialRecord = FileManager.createFile(name.replaceAll("\\s+","")  +
                "FinancialRecord" + LocalDateTime.now().getYear());

        this.municipality = muni;
    }

    /**
     * Constructeur de l'association
     * @param donorsList la liste des donateurs
     * @param members une énumeration de personne membre de l'association
     * @param name le nom de l'association
     */
    public Organisation(String name, ArrayList<Entity> donorsList, Member ...members) {
        super(name);
        this.donorsList = donorsList;
        this.DBURL = OrganisationDB.connect(name);
        OrganisationDB.createMembersTable(DBURL);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    m.getDateOfBirth(), m.getLastRegistrationDate(), "");
        }
        this.financialRecord = FileManager.createFile(name.replaceAll("\\s+","")  +
                "FinancialRecord" + LocalDateTime.now().getYear());
    }

    /**
     * Constructeur de l'association
     * @param budget le budget de l'association
     * @param members une énumeration de personne membre de l'association
     * @param muni la municipalité liée à l'association
     * @param name le nom de l'association
     */
    public Organisation(String name, Float budget, Municipality muni, Member ... members) {
        super(name);
        this.budget = budget;
        this.DBURL = OrganisationDB.connect(name);
        OrganisationDB.createMembersTable(DBURL);
        for (Member m : members){
            membersList.add(new MutablePair<>(++numMembers, m));
            OrganisationDB.insertMemberData(DBURL, numMembers,m.getName(), m.getFamilyName(),
                    m.getDateOfBirth(), m.getLastRegistrationDate(), "");
        }
        this.financialRecord = FileManager.createFile(name.replaceAll("\\s+","")  +
                "FinancialRecord" + LocalDateTime.now().getYear());

        this.municipality = muni;
    }



//    private File createFile(String fileName) {
//        try {
//            File myObj = new File(fileName + ".txt");
//            if (myObj.createNewFile()) {
//                return myObj;
//            } else {
//                System.err.println("File already exists.");
//                return new File(fileName + ".txt");
//            }
//        } catch (IOException e) {
//            System.err.println("An error occurred.");
//            return this.financialRecord;
//        }
//    }
    // Getters & Setters

    /**
     * Permet d'obtenir le budget de l'association
     * @return le budget de l'association
     */
    public Float getBudget() {
        return budget;
    }

    /**
     * Permet de changer le budget de l'association
     * @param budget le nouveau budget
     */
    public void setBudget(Float budget) {
        this.budget = budget;
    }

    /**
     * Permet d'obtenir la liste des donateurs
     * @return la liste de des donateurs
     */
    public ArrayList<Entity> getDonorsList() {
        return donorsList;
    }

    /**
     * Permet de changer la liste des donateurs avec une nouvelle
     * @param donorsList la nouvelle liste de donateurs
     */
    public void setDonorsList(ArrayList<Entity> donorsList) {
        this.donorsList = donorsList;
    }

    /**
     * Permet d'obtenir la liste des membres
     * @return un ArrayList de pair 'Integer, Member' correspondant à l'ID du membre dans l'association lié
     * membre en question
     */
    public ArrayList<MutablePair<Integer, Member>> getMembersList() {
        return membersList;
    }

    /**
     * Permet de changer la liste des membres avec une nouvelle liste
     * @param membersList la nouvelle liste de membre
     */
    public void setMembersList(ArrayList<MutablePair<Integer, Member>> membersList) {
        this.membersList = membersList;
    }

    /**
     * Permet d'obtenir la liste des votes de membres
     * @return Un array list des files correspondant aux votes du membre
     */
    public ArrayList<Queue<Tree>> getListVoteMember() {
        return listVoteMember;
    }

    /**
     * Permet d'obtenir la map mettant en relation un arbre et son nombre de vote dans l'association
     * @return la map en question
     */
    public Map<Integer, Integer> getMapTreeVote() {
        return mapTreeVote;
    }

    /**
     * Permet d'obtenir la map mettant en relation position dans le classement et ID de l'arbre à cette position
     * Il n'y a que au + 5 arbres dans cette MAP.
     * @return la map en question
     */
    public Map<Integer, Integer> getVoteRanking() {
        return voteRanking;
    }

    /**
     * Permet d'obtenir la map mettant en relation l'ID d'un arbre et sa disponibilité en terme de visite.
     * Si un membre s'est porté volontaire pour visiter cette arbre le booléen sera TRUE sinon FALSE
     * @return la map en question
     */
    public Map<Integer, Boolean> getMapVisit() {
        return mapVisit;
    }

    /**
     * Permet d'obtenir la file d'arbre correspondant aux arbres remarquables pas visité depuis longtemps.
     * L'arbre qui a la date de visite la plus vieille sera en premier dans la liste pour attirer le membre vers
     * celui-ci
     * @return la file en question
     */
    public Deque<Tree> getListRemarkableTreeNotVisitedForAWhile() {
        return listRemarkableTreeNotVisitedForAWhile;
    }

    // Organisation Operations

    /**
     * La méthode qui permet d'actualiser l'organisation. C'est une méthode très importante
     */
    private void update(){
        // Notify members to pay their contibutions
        if (LocalDate.now().getMonthValue() == 12
                && LocalDate.now().getDayOfMonth() == 31){
            membersList.forEach(m -> {
                NotificationManager.sendNotification(this, m.getRight(),
                        "Please Don't Forget to Pay Your Yearly Contibution", LocalDateTime.now());
            });
        }

        // Update the Organisation Structure at each end of the year
        if (LocalDate.now().getMonthValue() == 1
                && LocalDate.now().getDayOfMonth() == 1){
            // Voting at the end of each year
            getVotesFromMember();
            countVote();
            updateVoteRanking();
            municipality.setVotedTrees(this.getVoteRanking());

            // Check if memebers paid contibutions or not
            membersList.forEach(m ->
            {
                if (!m.getRight().isPayedContribution()){
                    // If member didn't pay the yealy contribution
                    // He data is removed, but we keep track of his
                    // past activity on the DataBase.
                    removeMember(m.getRight());
                }
            });

            // Create new Budget Files
            this.financialRecord = FileManager.createFile(this.getName().replaceAll("\\s+","")  +
                    "FinancialRecord" + LocalDateTime.now().getYear());

        }
    }

    /**
     * Permet d'obtenir le nom des membres dans la liste. Ces noms sont ordonés dans un StringBuilder pour l'affichage
     * dans la méthode toString()
     * @return Le StringBuilder en question
     */
    public StringBuilder getNameOfMemberInMemberList(){
        StringBuilder s = new StringBuilder();
        if (membersList.isEmpty()){
                System.err.println("[ORGANISATION] Error : Get Name of Member List is impossible -> " +
                        "The MemberList is empty");
        }
        else{
            for(int i = 0 ; i < membersList.size() ; i++){
                if(i == membersList.size()-1){
                    s.append(membersList.get(i).getRight().getName());
                }
                else{
                    s.append(membersList.get(i).getRight().getName());
                    s.append(" ; ");
                }
            }
        }
        return s;
    }

    // Function

    /**
     * Méthode modélisant l'ajout de fonds à l'organisation grâce aux cotisations du membre
     * @param m le membre qui cotise
     * @param amount le montant de la cotisation
     * @return un booléen
     */
    public boolean addMoneyFromMemberContribution(Member m,float amount){
        // Aux value to locally store value
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(m);
        if(aux.getLeft()){
            m.payContribution(amount);
            setBudget(budget+amount);
            System.out.println(String.format("[%s] Got " + amount + "$ from " + m.getName() + " " +
                    m.getFamilyName() + "\n", this.getName()));
            FileManager.writeToRecord(this, financialRecord, String.format("Recieved @Member %d Contribution",
                    membersList.get(aux.getRight()).getLeft()), amount, budget);
        }
        else{
            System.err.println(String.format("[%s] Error : " + m.getName() + " " + m.getFamilyName() +
                    " is not a member of \"" + this.getName() + "\". Contribution Failed\n", this.getName()));
        }

        return false;
    }

    /**
     * Méthode modélisant la recherche d'un membre dans la liste des membres.
     * Méthode qui permet de savoir si un membre est dans al liste des membres et si oui de récuperer aussi son index
     * @param member le membre recherché
     * @return Une immutable Pair de 'Boolean, Integer'. Le boolean correspond à la présence ou non du membre dans la
     * liste des membres et le INTEGER à l'index du membre s'il est présent.
     */
    private ImmutablePair<Boolean, Integer> checkMemberInMemberList(Member member){
        int index =0;
        while (index < membersList.size()){
            if (membersList.get(index).getRight() == member)
                return new ImmutablePair<>(true, index);
            else
                index++;
        }
        return new ImmutablePair<>(false, -1);
    }

    /**
     * Méthode modélisant l'ajout d'un membre à l'organisation
     * @param member le membre ajouté
     * @return true ou false selon si l'ajout a été un succès ou pas.
     */
    public boolean addMember(Member member){
        if (!checkMemberInMemberList(member).getLeft()){
            membersList.add(new MutablePair<Integer, Member>(++numMembers, member));
            System.out.println("Affichage du membre quand il a été ajouté : index = " + numMembers + " ; prénom du membre : " + member.getName());
            OrganisationDB.insertMemberData(DBURL, numMembers, member.getName(), member.getFamilyName(),
                    member.getDateOfBirth(), member.getLastRegistrationDate(), "");
            return true;
        }
        else{
            System.err.println("[ORGANISATION] Error : " + member.getName() + " " + member.getFamilyName() + " could not be added to \"" + this.getName() + "\".\n");
        }

        return false;
    }


    // VOTE's FUNCTION

    /**
     * Permet de récupérer les votes de tous les membres dans une variable d'instance de l'organisation
     */
    public void getVotesFromMember(){
        for(MutablePair<Integer, Member> m : membersList){
            listVoteMember.add(m.getRight().getVotes());
        }
    }

    /**
     * Permet de compter les votes. Stocke dans une variable d'instance de l'organisation mapTreeVote l'arbre en clé et
     * le nombre de vote a son actif en valeur.
     */
    public void countVote(){
        for(int i = 0 ; i < listVoteMember.size() ; i++){
            listVoteMember.get(i).size();
            Iterator iteratorVals = listVoteMember.get(i).iterator();
            while(iteratorVals.hasNext()){
                Tree next  = (Tree) iteratorVals.next();
                if(!(mapTreeVote.containsKey(next.getTreeID()))){
                    mapTreeVote.put(next.getTreeID(), new Integer(1));
                }
                else{
                    mapTreeVote.put(next.getTreeID(), mapTreeVote.get(next.getTreeID()) + 1);
                }
            }
        }
    }

    /**
     * Méthode pour trier une map par valeur
     * @param map la map a trier
     * @return la map triée
     */
    private Map<Integer, Integer> sortMapTreeValeur(Map<Integer, Integer> map){
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Méthode modélisant l'actualisation du classement des votes. Stocke dans une variable d'instance voteRanking
     * les 5 arbres les plus votés. voteRanking est une map 'Integer, Integer'. Le premier INTEGER est le classement
     * de l'arbre et le deuxième l'ID de l'arbre en question
     */
    public void updateVoteRanking(){
        mapTreeVote = sortMapTreeValeur(mapTreeVote);
        Iterator mapIterateValue = mapTreeVote.entrySet().iterator();
        int i = 1;
        while(mapIterateValue.hasNext() && i <= 5) {
            Map.Entry m = (Map.Entry) mapIterateValue.next();
            voteRanking.put(i, (int)m.getKey());
            i++;
        }
    }

    /**
     * Méthode modélisant la suppression d'un membre
     * @param member le membre à supprimer
     * @return true or false selon si la suppression du membre a été un succès
     */
    public boolean removeMember(Member member){
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            // get the member in the the member list using his index
            // leave the member ID, but delete all his info.
            membersList.get(aux.getRight()).setRight(null);
            OrganisationDB.deleteMemberData(DBURL, aux.getRight());
            System.out.println("Member successfully removed");
            return true;
        }
        System.err.println("FATAL ERROR : MEMBER IS NOT A PART OF THE ORGANISATION");
        return false;
    }

//    private boolean writeToRecord(File record, String typeOfOperation, float amount){
//        try(FileWriter writer = new FileWriter(financialRecord.getName(),true)){
//            writer.write(String.format("[%s]\nNEW FINANCIAL OPERATION==========\n",this.getName()));
//            writer.write("\tDate : " + LocalDateTime.now() + "\n");
//            writer.write("\tType : " + typeOfOperation + "\n");
//            writer.write("\tAmount : " + amount + "\n");
//            writer.write("\tNew Budget : " + budget + "\n"); // Change to budget must be done before
//            writer.write("END -----------------\n");
//
//            return true;
//        }
//        catch (IOException e) {
//            System.err.println("I/O ERROR : CAN'T WRITE TO FILE" + record.getAbsolutePath());
//            e.printStackTrace();
//            return false;
//        }
//
//    }

    /**
     * Méthode modélisant une demande de donation
     */
    public void askForDonations(){
//        et qui peuvent ^etre de dierentes natures (ex. services municipaux, entreprises, associations, individus),
//        mais qui doivent tous pouvoir recevoir une demande ecrite de subvention/don emanant de l'association et,
        Report report = new Report(this, "Your Donations Keep Us Going",
                LocalDate.now(), this.financialRecord);
        // Send Notification to Donors
        donorsList.forEach(d -> NotificationManager.sendNotificationWithReport(this, d, "Donation Request",
                report, LocalDateTime.now()));
    }


    //    private String getRecord() {
//
//    }
//
//    void planifiervisite(){
////        l'association planie des visites d'arbres remarquables. Disposant de peu de
////        moyens, celles-ci sont faites sur la base du volontariat de ses membres, qui
//    }
//
//    void affectervisite(Member){
////        Une visite est automatiquement validee et donc aectee a un membre si
////        aucun autre membre n'a deja programme une visite pour cet arbre remarquable. Pour
//    }
//
    private ArrayList<Tree> getSortedTreeByDate(){
        Collections.sort(municipality.getTrees(), Tree.ComparatorTree);
        return municipality.getTrees();
    }

    private void setupListRemarkableTreeNotVisitedForAWhile(){
        ArrayList<Tree> sortedTreeByDate = getSortedTreeByDate();
        // private ArrayList<Queue<Tree>> listRemarkableTreeNotVisitedForAWhile = new ArrayList<>();
        for(int i = 0 ; i < sortedTreeByDate.size() ; i++){
            if(!(sortedTreeByDate.get(i).isRemarkable())){
                listRemarkableTreeNotVisitedForAWhile.addLast(sortedTreeByDate.get(i));
            }
        }
    }

    private void setupListRemarkableTreeVisit(){
        for(int i = 0 ; i < municipality.getTrees().size() ; i++){
            if(!(municipality.getTrees().get(i).isRemarkable())){
                if(municipality.getTrees().get(i).getTreeID() == null){
                    continue;
                }
                else {
                    mapVisit.put(municipality.getTrees().get(i).getTreeID(), false);
                }
            }
        }
    }

    public void allowOrNotVisit(Member m, Tree t){
        System.out.println("La fonction s'éxecute");
        if(t.isRemarkable()){
            System.out.println("t is remarkable");
            if(mapVisit.get(t.getTreeID()) ==  false){
                System.out.println("[ORGANISATION] Visit Accepted for the tree \'" + t.getTreeID() + "\'");
                mapVisit.put(t.getTreeID(), true);
                //@TODO : Ajouter les notifications
            }
            else{
                System.err.println("[ORGANISATION] Visit Rejected for the tree \'" + t.getTreeID() + "\'. " +
                        "Tree already been reserved");
                System.out.println("Here's a list of remarkable tree not visited for a while : " + listRemarkableTreeNotVisitedForAWhile);
                //@TODO : Ajouter les notifications
            }
        }
        else{
            System.out.println("t n'est pas remarkable");
            System.err.println("[ORGANISATION] Tree not remarkable");
        }

    }

    public void doAllVisitFx(){
        setupListRemarkableTreeNotVisitedForAWhile();
        setupListRemarkableTreeVisit();
        System.out.println("ListRemarkableTreeNotVisitedForAWhile : " + listRemarkableTreeNotVisitedForAWhile);
        System.out.println("ListRemarkableTreeVisit : " + mapVisit);
    }

    public void refundMember(Member member, float amount){
//        membre ayant eectue la visite est defraye pour celle-ci d'un montant xe,
//        nombre maximum de visites par an.

        // Check if member is in member list
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            // Check if the amount to refund is within budget
            if (budget - amount > 0){
                budget -= amount;
                FileManager.writeToRecord(this, financialRecord, "Refund @MEMBER " + aux.getRight(), amount, budget);
            }
            else {
                System.err.println("INSUFFICIENT FUNDS");
            }
        }
        else{
            System.err.println("FATAL ERROR : MEMBER IS NOT A PART OF THE ORGANISATION");
        }
    }

    public String sendData(Member member){
        // Get memeber ID
        ImmutablePair<Boolean, Integer> aux = checkMemberInMemberList(member);
        if (aux.getLeft()){
            return OrganisationDB.fetchMemberData(DBURL, aux.getRight());
        }
        return null;
    }

    public void payBill(float amount){
//        Le type de compte en banque de l'association ne lui
//        permettant d'avoir un solde negatif, un paiement ne pourra ^etre eectue que si la somme correspondante
//        existe bien sur le compte bancaire.
        if (budget - amount > 0){
            budget -= amount;
            FileManager.writeToRecord(this, financialRecord, "Bill Payment", amount, budget);
        }
        else{
            System.err.println("INSUFFICIENT FUNDS");
        }
    }

    public void recieveFunds(float amount){
        this.budget += amount;
        FileManager.writeToRecord(this, financialRecord,"Received Donor Payment", amount, budget);
    }

    @Override
    public String toString() {
        StringBuilder OrganisationSTB = new StringBuilder(String.format("[%s INFO]\n",this.getName()));
        OrganisationSTB.append("\tOrganisation name : " + getName() + "\n");
        OrganisationSTB.append("\tOrganisation budget : " + getBudget() + "\n");
        OrganisationSTB.append("\tMember List : " + getNameOfMemberInMemberList() + "\n");

        return OrganisationSTB.toString();
    }

    public static void main(String[] args) throws InterruptedException {

        Member m1 = new Member("Houssem", "Mahmoud", new Date(1998, 04, 30), "Somewhere not far from Tunis",
                new Date(2021, 05, 23), false, 5000);

        Member m2 = new Member("Esteban", "Neraudau", new Date(2001, 10, 29), "Antony",
                new Date(2021, 05, 23), false, 5000);

        Member m3 = new Member("Mohamed", "Mahmoud", new Date(2002, 10, 28), "Somewhere not far from Tunis",
                new Date(2021, 05, 24), false, 10000);

        Member m4 = new Member("Rayane", "Hammadou",
                new Date(2000, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

        Member m5 = new Member("Rayane", "Hammadou",
                new Date(2000, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

        Member m6 = new Member("Rayane", "Hammadou",
                new Date(2000, 05, 01), "Antony",
                new Date(2021, 05, 25), false, 15000);

        Tree t1 = new Tree(147179, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t2 = new Tree(1, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t3 = new Tree(2, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t4 = new Tree(3, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t5 = new Tree(4, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t6 = new Tree(5, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Tree t7 = new Tree(6, "Marronnier", 150, 15, "hippocastanum",
                "Aesculus", "Adulte", "CIMETIERE DU PERE LACHAISE / AVENUE DES THUYAS / DIV 86",
                new Float[]{(float)48.8632712288,(float)2.39435673087}, false);

        Municipality muni = new Municipality("Antony", "Somewhere", "files\\testData.csv");


        Organisation org = new Organisation("Tree Lovers", 1000.0f, muni, m1);

        //System.out.println(org.municipality.getTrees());

        org.doAllVisitFx();


        m1.toVolunteerOn(org, t1);


        //org.payBill(10);
        // org.addMember(m2);
        // System.out.println(org.toString());
        /*org.getVotesFromMember();
        System.out.println(org.getListVoteMember());*/
        /*org.addMember(m2);
        org.addMember(m3);
        org.addMember(m4);
        org.addMember(m5);
        org.addMember(m6);

        m2.vote(t1,t2,t3);
        m1.vote(t1,t3,t5);
        m3.vote(t1,t4,t5);
        m4.vote(t1,t6,t7);
        m5.vote(t1,t5,t6,t3);
        m6.vote(t1,t2,t3,t4,t5);

        org.getVotesFromMember();
        System.out.println(org.getListVoteMember());
        org.countVote();
        System.out.println(org.getMapTreeVote());

        org.updateVoteRanking();

        System.out.println("Affichage de la map \'voteRanking\' : " + org.voteRanking);

        System.out.println("Affichage de la map avec que des arbres remarquables : " + org.mapVisit);


        //org.addMoneyFromMemberContribution(m1, 200);
        // Added time delays for dramatic effect
        //TimeUnit.SECONDS.sleep(2);
        //org.addMember(m3);
        //System.out.println(org.toString());
        //System.out.println(org.checkMemberInMemberList(m3).getLeft());

        //org.refundMember(m2,75);
        //TimeUnit.SECONDS.sleep(2);
        //org.payBill(50);
        //org.recieveFunds(500);
        //TimeUnit.SECONDS.sleep(2);

        //Report r1 = new Report(org, "financialReport", LocalDate.now(), org.financialRecord);
        //System.out.println(r1);
        //org.financialRecord.delete();
        //System.out.println(org.getMembersList());*/

    }
}
