/**
 *  File: UserAuthenticator.java
 *  Description: This class is used for the authentication for the login
 *  after the user clicks enters it will retrieve from a temporary array of logins of
 *  both the admins and the users, and authenticate weather they are users or not.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */


package Backend;


public class UserAuthenticator {
    // Admin credentials
    private final String[][] admins = {
            {"huzaifa", "hello"},
            {"musa", "hithere"},
            {"abdullah", "hola"}
    };

    // User credentials
    private final String[][] users = {
            {"student1", "Pass1"},
            {"student2", "Pass2"},
            {"student3", "Pass3"},
            {"student4", "Pass4"},
            {"student5", "Pass5"}
    };

    // Method to authenticate user
    public String login(String username, String password) {
        // Check Admins
        for (String[] admin : admins) {
            //First index of the 2D array stores username
            if (admin[0].equals(username)) {
                //Second index of the 2D array stores password
                if (admin[1].equals(password)) {
                    System.out.println("Admin login successful: " + username);
                    return "admin"; // Return "admin" if the login is an Admin
                } else {

                    System.out.println("Incorrect password for admin: " + username);
                    return "invalid";
                }
            }
        }

        // Check Users
        for (String[] user : users) {
            //First index of the 2D array stores username
            if (user[0].equals(username)) {
                //Second index of the 2D array stores password
                if (user[1].equals(password)) {
                    System.out.println("User login successful: " + username);
                    return "user"; // Return "user" if the login is a User
                } else {
                    System.out.println("Incorrect password for user: " + username);
                    return "invalid";
                }
            }
        }

        System.out.println("Username not found.");
        return "invalid"; // If no user or admin is found, return "invalid"
    }
}
