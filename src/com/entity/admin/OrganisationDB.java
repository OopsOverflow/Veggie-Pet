package com.entity.admin;

import java.io.File;
import java.sql.*;

public class OrganisationDB {
    // Create the DataBase and connect to it.
    public static String connect(String name) {
        Connection conn = null;
        // db parameters
        name = name.replaceAll("\\s+","");
        String fileName = new File("src/com/entity/admin").getAbsolutePath() + "\\";
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
     *
     * @param url : SQLite connection string
     */
    public static void createMembersTable(String url) {

        // Creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS members (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	lastname text NOT NULL,\n"
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

    public static void insertMemberData(String url, Integer id, String name, String lastname, Date registrationdate, String activity) {
        String sql = "INSERT INTO members (id, name, lastname, " +
                "registrationdate, activity) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, lastname);
            pstmt.setDate(4, registrationdate);
            pstmt.setString(5, activity);
            pstmt.executeUpdate();
            System.out.println("Values were successfully inserted into DataBase.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //@TODO : Change name and last name to left
    //public static void deleteMemberData
    
    //@TODO : Select member data and activity, convert it to a string and encrypt it.
    // TODO: 28/05/2021 add AES encryption 


  
    public static void main(String[] args) {
        String url = connect("Tree Lovers");
        System.out.println(url);
        createMembersTable(url);
        insertMemberData(url,100, "Esteban", "Neradeau", new Date(2021,9,15), "Streaming");
        // Delete the DataBase after each use; At least for now.
//        File data = new File("src\\com\\entity\\admin\\TreeLovers.db");
//        data.delete();


    }
}
