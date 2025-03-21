/**
 *  File: Faculty.java
 *  Description: This class is for assigning faculties, checking course
 *  facilitators, more information about themselves such as offices, education and areas of reasearch
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */

package Backend;

//Import Statements
import java.util.List;
import java.awt.Image;
import java.util.ArrayList;


public class Faculty {

    // Attributes
    private String name;
    private Image profilePhoto;
    private String degree;
    private String researchInterest;
    private List<String> coursesOffered;
    private String email;
    private String officeLocation;

    //stat array
    public static List<Faculty> facultyList = new ArrayList<>();


    // Constructor
    public Faculty(String name, Image profilePhoto, String degree, String researchInterest,
                   List<String> coursesOffered, String email, String officeLocation) {
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.degree = degree;
        this.researchInterest = researchInterest;
        this.coursesOffered = coursesOffered;
        this.email = email;
        this.officeLocation = officeLocation;
    }

    //add faculty
    public static void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
        System.out.println("Faculty added: " + faculty.getName()); // Debugging log
    }

    //retrieve all faculty
    public static List<Faculty> getAllFaculty() {
        return facultyList;
    }
    // New method to format courses for JavaFX TableView
    public String getCoursesAsString() {
        //System.out.println("DEBUG: getCoursesAsString() called for " + name);
        return String.join(", ", coursesOffered); // Converts list to comma-separated string
    }



    // Getter Methods
    public String getName() {
        return name;
    }
    public Image getProfilePhoto() {
        return profilePhoto;
    }
    public String getDegree() {
        return degree;
    }
    public String getResearchInterest() {
        return researchInterest;
    }
    public List<String> getCoursesOffered() {
        return coursesOffered;
    }
    public String getEmail() {
        return email;
    }
    public String getOfficeLocation() {
        return officeLocation;
    }


    // Setters Methods
    public void setName(String name) {
        this.name = name;
    }
    public void setProfilePhoto(Image profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public void setResearchInterest(String researchInterest) {
        this.researchInterest = researchInterest;
    }
    public void setCoursesOffered(List<String> coursesOffered) {
        this.coursesOffered = coursesOffered;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }


    // Method to display details for Faculty
    public void displayFacultyInfo() {
        System.out.println("Faculty Name: " + name);
        System.out.println("Degree: " + degree);
        System.out.println("Research Interest: " + researchInterest);
        System.out.println("Courses Offered: " + coursesOffered);
        System.out.println("Email: " + email);
        System.out.println("Office Location: " + officeLocation);
    }
}

