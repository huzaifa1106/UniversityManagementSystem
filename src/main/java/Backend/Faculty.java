package Backend;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Faculty {

    // Static list of all faculty members
    private static List<Faculty> facultyList = new ArrayList<>();

    // Property to store courses as a concatenated string
    private String coursesAsString;

    // Faculty attributes
    private String facultyID;
    private String name;
    private Image profilePhoto;
    private String degree;
    private String researchInterest;
    private List<String> coursesOffered;
    private String email;
    private String officeLocation;

    // Constructor
    public Faculty(String facultyID, String name, Image profilePhoto, String degree, String researchInterest,
                   List<String> coursesOffered, String email, String officeLocation) {
        this.facultyID = facultyID;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.degree = degree;
        this.researchInterest = researchInterest;
        this.coursesOffered = coursesOffered;
        this.email = email;
        this.officeLocation = officeLocation;
        this.coursesAsString = String.join(", ", coursesOffered);  // Initialize coursesAsString with a concatenated string
    }

    // Add faculty directly and save to file
    public static void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
        ReadExcelFile.writeToExcel();
    }

    // Remove faculty by ID
    public static void removeFaculty(String facultyID) {
        facultyList.removeIf(f -> f.getFacultyID().equals(facultyID));
    }

    // Retrieve all faculty records
    public static List<Faculty> getFacultyList() {
        return facultyList;
    }

    // Replace entire faculty list
    public static void setFacultyList(List<Faculty> faculties) {
        facultyList = faculties;
    }

    // Find faculty by name
    public static Faculty findByName(String name) {
        for (Faculty f : facultyList) {
            if (f.getName().equalsIgnoreCase(name.trim())) {
                return f;
            }
        }
        return null;
    }

    // Getter for coursesAsString
    public String getCoursesAsString() {
        return coursesAsString;
    }

    // Setter for coursesAsString (although not usually needed if it's derived from coursesOffered)
    public void setCoursesAsString(String coursesAsString) {
        this.coursesAsString = coursesAsString;
    }


    // -------------------------------
    // Getters
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
    // Setters
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
