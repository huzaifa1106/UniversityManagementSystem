package Backend;
//hi
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private static List<Faculty> facultyList = new ArrayList<>();

    private String facultyID;
    private String name;
    private Image profilePhoto;
    private String degree;
    private String researchInterest;
    private List<String> coursesOffered;
    private String email;
    private String officeLocation;

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
    }



    public static void addFaculty(Faculty faculty) {

        facultyList.add(faculty);
        ReadExcelFile.writeToExcel();

    }

    public static void removeFaculty(String facultyID) {
        facultyList.removeIf(f -> f.getFacultyID().equals(facultyID));
    }

    public static List<Faculty> getFacultyList() {
        return facultyList;
    }

    public String getCoursesAsString() {
        return String.join(", ", coursesOffered);
    }

    public String getEmail() {
        return email;
    }
    public static Faculty findByName(String name) {
        for (Faculty f : facultyList) {
            if (f.getName().equalsIgnoreCase(name.trim())) {
                return f;
            }
        }
        return null;
    }
    public String getOfficeLocation() {
        return officeLocation;
    }

    // Setters
    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

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



    // Getters
    public String getFacultyID() {
        return facultyID;
    }

    public String getName() {
        return name;
    }

    public Image getProfilePhoto() {
        return profilePhoto;
    }

    public String getDegree() {
        return degree;
    }

    public List<String> getCoursesOffered() {
        return coursesOffered;}


    public String getResearchInterest() {
        return researchInterest;
    }

    public static void setFacultyList(List<Faculty> faculties) {
        facultyList = faculties;
    }

    // Display method
    public void displayFacultyInfo() {
        System.out.println("Faculty ID: " + facultyID);
        System.out.println("Faculty Name: " + name);
        System.out.println("Degree: " + degree);
        System.out.println("Research Interest: " + researchInterest);
        System.out.println("Courses Offered: " + coursesOffered);
        System.out.println("Email: " + email);
        System.out.println("Office Location: " + officeLocation);
    }
}

