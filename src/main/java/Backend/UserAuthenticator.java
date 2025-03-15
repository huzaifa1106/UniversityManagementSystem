package Backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAuthenticator {
    private static final String USER_FILE = "users.txt"; // File for storing users
    private static final String ADMIN_FILE = "admins.txt"; // File for storing admins
    private static List<String[]> users = new ArrayList<>();
    private static List<String[]> admins = new ArrayList<>();

    // Load users from file on startup
    static {
        loadUsers();
        loadAdmins();
    }

    // Method to add a new user and write to file
    public static void newUser(String username, String password) {
        // Ensure username does not already exist
        for (String[] user : users) {
            if (user[0].equals(username)) {
                System.out.println("Error: Username already exists.");
                return;
            }
        }

        // Add new user
        users.add(new String[]{username, password});
        writeUserToFile(username, password); // Save to file
        System.out.println("New user registered successfully: " + username);
    }

    public static void newAdmin(String username, String password) {
        // Ensure username does not already exist
        for (String[] admin : admins) {
            if (admin[0].equals(username)) {
                System.out.println("Error: Username already exists.");
                return;
            }
        }

        // Add new user
        users.add(new String[]{username, password});
        writeUserToFile(username, password); // Save to file
        System.out.println("New user registered successfully: " + username);
    }

    // Method to authenticate user
    public static String login(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username)) {
                if (user[1].equals(password)) {
                    System.out.println("User login successful: " + username);
                    return "user"; // User login
                } else {
                    System.out.println("Incorrect password for user: " + username);
                    return "invalid";
                }
            }
        }

        for (String[] admin : admins) {
            if (admin[0].equals(username)) {
                if (admin[1].equals(password)) {
                    System.out.println("Admin login successful: " + username);
                    return "admin"; // Admin login
                } else {
                    System.out.println("Incorrect password for admin: " + username);
                    return "invalid";
                }
            }
        }

        System.out.println("Username not found.");
        return "invalid"; // No user found
    }

    // Read users from file at startup
    private static void loadUsers() {
        //Clears the file in case of duplicate
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            //Checks if line is null
            while ((line = reader.readLine()) != null) {
                //File was written in format (Username, Password)
                String[] userDetails = line.split(",");
                //Checks if array length is 2 elements
                if (userDetails.length == 2) {
                    //Simply adds to the list array
                    users.add(userDetails);
                }
            }
        } catch (FileNotFoundException e) {
            //If file not found
            System.out.println("User file not found. Creating new one.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to Read admins from file
    private static void loadAdmins() {
        admins.clear();
        try (BufferedReader read = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] adminDetails = line.split(",");
                if (adminDetails.length == 2) {
                    admins.add(adminDetails);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Admin file not found. Creating new one.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write a single user to file
    private static void writeUserToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to write to admin file
    private static void writeAdminToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Print all registered users
    public static void printUsers() {
        System.out.println("Registered Users:");
        for (String[] user : users) {
            System.out.println("Username: " + user[0] + ", Password: " + user[1]);
        }
    }
    // Print all registered admins
    public static void printAdmins() {
        System.out.println("Registered Admins:");
        for (String[] admin : admins) {
            System.out.println("Username: " + admin[0] + ", Password: " + admin[1]);
        }
    }

}
