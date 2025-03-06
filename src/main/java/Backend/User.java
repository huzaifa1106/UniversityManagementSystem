/**
 *  File: User.java
 *  Description: This class is for storing user logins and roles,
 *  where the student, facilitators and the admin will branch off
 *  later on the in code, and certain methods protected so only
 *  inheritance classes can access them.
 *  Date: March 2nd, 2025
 *  */


package Backend;


public class User {
    String username = "";
    private String password = "";
    private String role = "";

    //Constructor
    public User(String u, String p, String r) {
        this.username = u;
        this.password = p;
        this.role = r;
    }

    //Getter Function
    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public String getUsername() {
        return username;}

    //Setter functions
    protected void setPassword(String p) {
        this.password = p;
    }

    protected void setRole(String r) {
        this.role = r;
    }

}
