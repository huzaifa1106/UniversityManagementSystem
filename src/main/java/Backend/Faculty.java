/**
 * File: Faculty.java
 * Description: Represents a Faculty member at the University of Guelph.
 *              Handles creation, authentication, persistence, and access of faculty data.
 * Author: Group 10
 * Date: April 2025
 */

package Backend;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Faculty extends User {

    // A static list to store all faculty instances in memory
    private static List<Faculty> facultyList = new ArrayList<>();

    // Additional faculty attributes
    private String facultyID;
    private String name;
    private Image profilePhoto;
    private String degree;
    private String researchInterest;
    private List<String> coursesOffered;
    private String email;
    private String officeLocation;

    // Cached string representation of courses (e.g., "CS101, MATH120")
    private String coursesAsString;

    /**
     * Constructs a Faculty object and registers it.
     * Automatically adds to the faculty list and authenticates it if not already registered.
     */
    public Faculty(String facultyID, String name, Image profilePhoto, String degree, String researchInterest,
                   List<String> coursesOffered, String email, String officeLocation) {

        // Generate faculty ID if missing or improperly formatted
        if (facultyID == null || !facultyID.toLowerCase().startsWith("f")) {
            facultyID = generateFacultyID();
        }

        // Initialize superclass (User) with default password and role
        super(facultyID, "facultor123", "faculty");

        // Set all faculty properties
        this.facultyID = facultyID;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.degree = degree;
        this.researchInterest = researchInterest;
        this.coursesOffered = coursesOffered;
        this.email = email;
        this.officeLocation = officeLocation;

        // Convert course list into a readable string format for UI
        this.coursesAsString = String.join(", ", coursesOffered);

        // Register in authenticator unless already present
        if (!isUsernameTaken(this.facultyID)) {
            UserAuthenticator.newFacultor(this.getFacultyID(), this.getPassword());
        } else {
            System.out.println("Faculty ID already exists: " + this.getFacultyID());
        }
    }

    /**
     * Generates a unique faculty ID in format like "F382914".
     */
    private static String generateFacultyID() {
        return "F" + (100000 + new java.util.Random().nextInt(900000));
    }

    /**
     * Checks whether a username is already registered in the UserAuthenticator.
     */
    private boolean isUsernameTaken(String username) {
        return !UserAuthenticator.login(username, "check").equals("invalid");
    }

    /**
     * Searches for a faculty by their ID (case-insensitive).
     */
    public static Faculty findByID(String facultyID) {
        for (Faculty f : facultyList) {
            if (f.getFacultyID().equalsIgnoreCase(facultyID)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Adds a faculty to the list and persists the data.
     */
    public static void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
        ReadExcelFile.writeToExcel();
    }

    /**
     * Removes a faculty by their ID.
     */
    public static void removeFaculty(String facultyID) {
        facultyList.removeIf(f -> f.getFacultyID().equals(facultyID));
    }

    /**
     * Retrieves the entire faculty list.
     */
    public static List<Faculty> getFacultyList() {
        return facultyList;
    }

    /**
     * Replaces the faculty list (useful for loading from storage).
     */
    public static void setFacultyList(List<Faculty> faculties) {
        facultyList = faculties;
    }

    /**
     * Looks for a faculty member by their full name (case-insensitive, trimmed).
     */
    public static Faculty findByName(String name) {
        for (Faculty f : facultyList) {
            if (f.getName().equalsIgnoreCase(name.trim())) {
                return f;
            }
        }
        return null;
    }

    // -------------------------------
    // Custom Getters and Setters
    // -------------------------------

    public String getCoursesAsString() {
        return coursesAsString;
    }

    public void setCoursesAsString(String coursesAsString) {
        this.coursesAsString = coursesAsString;
    }

    // -------------------------------
    // Standard Getters
    // -------------------------------

    public String getFacultyID() { return facultyID; }
    public String getName() { return name; }
    public Image getProfilePhoto() { return profilePhoto; }
    public String getDegree() { return degree; }
    public String getResearchInterest() { return researchInterest; }
    public List<String> getCoursesOffered() { return coursesOffered; }
    public String getEmail() { return email; }
    public String getOfficeLocation() { return officeLocation; }

    // -------------------------------
    // Standard Setters
    // -------------------------------

    public void setFacultyID(String facultyID) { this.facultyID = facultyID; }
    public void setName(String name) { this.name = name; }
    public void setProfilePhoto(Image profilePhoto) { this.profilePhoto = profilePhoto; }
    public void setDegree(String degree) { this.degree = degree; }
    public void setResearchInterest(String researchInterest) { this.researchInterest = researchInterest; }
    public void setCoursesOffered(List<String> coursesOffered) { this.coursesOffered = coursesOffered; }
    public void setEmail(String email) { this.email = email; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }
}
