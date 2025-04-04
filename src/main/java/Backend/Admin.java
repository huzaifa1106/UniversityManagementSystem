/**
 *  File: Admin.java
 *  Description: This class extends the user class, Admin is the highest hierarchy with
 *  access to all functions of the UofG University Management System
 *  we will use this later on when retrieving data for the UI the
 *  user role when logging in and their functions
 *  Author: Huzaifa A. & Group
 *  Date: April 2025
 *  */

//Declaring Package
package Backend;

public class Admin extends User {

    //Constructor
    public Admin(String u, String p, String r) {
       //calling User Constructor
        super(u, p, r);
    }

    //Method for Admin to change the password, access the student object and changes the password.
    public void changeStudentPassword(String p, Student s) {
        s.setPassword(p);
    }
    // Method for Admin to change the role of a user, by accessing the student object and changing the role.
    public void changeStudentRole(String r, Student s) {
        s.setRole(r);
    }
}
