/**
 * File: UserAuthenticator.java
 * Description: Handles user and admin registration, login authentication, and data persistence via local text files.
 * Stores credentials in memory and reads/writes to `users.txt` and `admins.txt`.
 *
 * Author: Huzaifa A. & Group
 * Date: April 2025
 */

package Backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAuthenticator {
    private static final String USER_FILE = "users.txt";
    private static final String ADMIN_FILE = "admins.txt";
    private static final String FACULTY_FILE = "faculty.txt";

    private static List<String[]> users = new ArrayList<>();
    private static List<String[]> admins = new ArrayList<>();
    private static List<String[]> facultors = new ArrayList<>();

    // Load credentials on startup
    static {
        loadUsers();
        loadAdmins();
        loadFacultors();
    }

    // Register a new user
    public static void newUser(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username)) {
                System.out.println("Error: Username already exists.");
                return;
            }
        }

        users.add(new String[]{username, password});
        writeUserToFile(username, password);
        System.out.println("New user registered successfully: " + username);
    }
    // Register new facultor
    public static void newFacultor(String username, String password) {
        for (String[] facultor : facultors) {
            if (facultor[0].equals(username)) {
                System.out.println("Error: Faculty username already exists.");
                return;
            }
        }
        facultors.add(new String[]{username, password});
        writeFacultorToFile(username, password);
        System.out.println("New facultor registered successfully: " + username);
    }

    // Register a new admin
    public static void newAdmin(String username, String password) {
        for (String[] admin : admins) {
            if (admin[0].equals(username)) {
                System.out.println("Error: Username already exists.");
                return;
            }
        }

        admins.add(new String[]{username, password});
        writeAdminToFile(username, password);
        System.out.println("New admin registered successfully: " + username);
    }

    // Authenticate login
    // Login authentication
    public static String login(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username)) {
                return user[1].equals(password) ? "user" : "invalid";
            }
        }
        for (String[] admin : admins) {
            if (admin[0].equals(username)) {
                return admin[1].equals(password) ? "admin" : "invalid";
            }
        }
        for (String[] facultor : facultors) {
            if (facultor[0].equals(username)) {
                return facultor[1].equals(password) ? "faculty" : "invalid";
            }
        }

        System.out.println("Username not found.");
        return "invalid";
    }

    // Load facultors
    private static void loadFacultors() {
        facultors.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FACULTY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] facultorDetails = line.split(",");
                if (facultorDetails.length == 2) facultors.add(facultorDetails);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Faculty file not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load users from file
    private static void loadUsers() {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 2) {
                    users.add(userDetails);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found. Creating new one.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load admins from file
    private static void loadAdmins() {
        admins.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
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

    // Write single user to file
    private static void writeUserToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write single admin to file
    private static void writeAdminToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write facultor
    private static void writeFacultorToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FACULTY_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Print all users
    public static void printUsers() {
        System.out.println("Registered Users:");
        for (String[] user : users) {
            System.out.println("Username: " + user[0] + ", Password: " + user[1]);
        }
    }

    // Print all admins
    public static void printAdmins() {
        System.out.println("Registered Admins:");
        for (String[] admin : admins) {
            System.out.println("Username: " + admin[0] + ", Password: " + admin[1]);
        }
    }
}
