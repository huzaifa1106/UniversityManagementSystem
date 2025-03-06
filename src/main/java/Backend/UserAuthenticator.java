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
            if (admin[0].equals(username)) {
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
            if (user[0].equals(username)) {
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
