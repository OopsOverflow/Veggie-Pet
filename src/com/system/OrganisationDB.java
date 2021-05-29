package com.system;

import java.io.File;
import java.sql.*;

public class OrganisationDB {
    // Create the DataBase and connect to it.

    /**
     * Méthode modélisant la connexion à la base de données
     * @param name le nom de la base de données
     * @return un String qui est l'url de la connexion SQLite
     */
    public static String connect(String name) {
        Connection conn = null;
        // db parameters
        name = name.replaceAll("\\s+","");
        String fileName = new File("src/com/entity/org").getAbsolutePath() + "\\";
        String url = "jdbc:sqlite:" + fileName + name + ".db";
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                url = null;
            }
        }
        return url;
    }

    /**
     * Méthode modélisant la création de la table MEMBRE dans la base de données
     * @param url : SQLite connection string
     */
    public static void createMembersTable(String url) {

        // Creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS members (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	lastname text NOT NULL,\n"
                + "	dateofbirth date,\n"
                + "	registrationdate date NOT NULL,\n"
                + "	activity text\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("SQLite Table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode modélisant l'ajout d'un membre dans la table MEMBRE
     * @param url l'url de la connexion SQLite
     * @param id l'id du membre
     * @param name le prénom du membre
     * @param lastname le nom du membre
     * @param dateofbirth la date de naissance du membre
     * @param registrationdate la date de registration du membre
     * @param activity l'activité du membre
     */
    public static void insertMemberData(String url, Integer id, String name, String lastname,
                                        Date dateofbirth, Date registrationdate,  String activity) {
        String sql = "INSERT INTO members (id, name, lastname, dateofbirth, " +
                "registrationdate, activity) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, lastname);
            pstmt.setDate(4, dateofbirth);
            pstmt.setDate(5, registrationdate);
            pstmt.setString(6, activity);
            pstmt.executeUpdate();
            System.out.println("Values were successfully inserted into DataBase.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // GDPR

    /**
     * Méthode modélisant la suppression des données d'un membre
     * @param url l'url de la connexion SQLite
     * @param memberID l'idée du membre auquel on veut supprimer les données.
     */
    public static void deleteMemberData(String url, int memberID) {
        String sql = "UPDATE members SET name = ? , "
                + "lastname = ? ,"
                + "dateofbirth = ?"
                + "WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set name and last name to left
            // keep the rest of the data
            pstmt.setString(1, "left");
            pstmt.setString(2, "left");
            pstmt.setDate(3, null);
            pstmt.setInt(4, memberID);
            // update
            pstmt.executeUpdate();
            System.out.println("Values were successfully erased from the DataBase.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode permettant d'afficher tous les membres
     * @param url l'url de la connexion SQLite
     * @return
     */
    public static String fetchMembersData(String url){
        String sql = "SELECT * FROM members";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            StringBuilder stb = new StringBuilder();
            while (rs.next()) {
                stb.append(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("lastname") + "\t" +
                        rs.getDate("dateofbirth") + "\t" +
                        rs.getDate("registrationdate") + "\t" +
                        rs.getString("activity") +
                        "\n");
            }

            return stb.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Méthode modélisant l'affichage des infos d'un membre issue de la table MEMBRE grâce à son ID
     * @param url l'url de connexion SQLite
     * @param MemberID l'idée du membre
     * @return
     */
    public static String fetchMemberData(String url, int MemberID){
        String sql = "SELECT * "
                + "FROM members WHERE members.id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setInt(1, MemberID);
            //
            ResultSet rs  = pstmt.executeQuery();

            StringBuilder stb = new StringBuilder();
            // loop through the result set
            while (rs.next()) {
                stb.append(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("lastname") + "\t" +
                        rs.getDate("dateofbirth") + "\t" +
                        rs.getDate("registrationdate") + "\t" +
                        rs.getString("activity") + "\n");
            }
            return stb.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //@TODO : Select member data and activity, convert it to a string and encrypt it.
    // TODO: 28/05/2021 add AES encryption 


  
    public static void main(String[] args) {
        String url = connect("Tree Lovers");
        System.out.println(url);
        createMembersTable(url);
        //insertMemberData(url,100, "Esteban", "Neradeau", new Date(2021,9,15), "Streaming");
        deleteMemberData(url, 100);
        System.out.println(fetchMemberData(url, 2));


        System.out.println(fetchMembersData(url));

        // Delete the DataBase after each use; At least for now.
//        File data = new File("src\\com\\entity\\org\\TreeLovers.db");
//        data.delete();


    }
}
