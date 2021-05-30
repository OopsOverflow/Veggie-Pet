
<p align = right> <img src =https://forthebadge.com/images/badges/made-with-java.svg></p>

Programmation Java @ Et3  
Polytech Paris-Saclay | 2020-21

<h1 align = "center">Veggie Pet</h1>
<p align = center> Trees 🌲 are now Pets 🐱 </p>

***
## About
A console simulation program of a tree organization.</br>
This is a school project made for the Java & OOP course.</br>
Developed with Java SE 16.

## Dependencies

 1. OpenCSV : Parse CSV files for Trees data
 2. Apache Commons Packages : Different Data Structures used to optimize the complexity of certain algorithms. 
 3. SQLite v3.34.0 : Store data in a locally created database. 

## Some Implementation Details 🔎
✅ Certain Data Structure such as `Queues` and `Hash Maps` were used to improve</br> the Visits and Voting algorithms time complexity.


✅  SQLite 💾 was used to create and manage local DBs. All Queries are under `src/com/sys/OrganisationDB.java` 


```java
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
```
✅ The Municipality can push notification at a random time within the year's period.</br>
This is achieved by creating a thread and scheduling ⏳ its execution for later.
```java
    // The Notification can be sent later
    Timer timer = new Timer(); // creating timer
    TimerTask task = this.classifyTree(tree, LocalDateTime.now()); // creating timer task
    timer.schedule(task, new Date()); // scheduling the task
```

✅ An AES 256bit Encryption algorithm is used to secure 🔒 members' data.</br>
  Code is under ``src/com/system/EncryptionDecryptionUtil.java``

```java
public static String encrypt(final String secret, final String data) {

    byte[] decodedKey = Base64.getDecoder().decode(secret);

    try {
        Cipher cipher = Cipher.getInstance("AES");
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(cipherText);
    } catch (Exception e) {
        throw new RuntimeException(
                "Error occured while encrypting data", e);
    }
}
```

## Test Run 🎯

<p align = center>
 <img src = "https://raw.githubusercontent.com/OopsOverflow/Veggie-Pet/main/screenshots/testRun.png" alt="test"/>
 </p>

## Credits ©
|    Name            |GitHub|Group|
|----------------|-------------------------------|-----------------------------|
|Mahmoud Houssem|@OopsOverflow            |GR2           |
|Neraudau Esteban|@s-posito|GR2		|


   
