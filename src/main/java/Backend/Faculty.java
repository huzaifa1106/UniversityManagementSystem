package Backend;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private static final List<Faculty> facultyList = new ArrayList<>();

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

    static {
        facultyList.add(new Faculty("F0001", "Dr. Alan Turing", null, "Ph.D.", "Computational Theory",
                List.of("Calculus I"), "turing@university.edu", "Room 201"));

        facultyList.add(new Faculty("F0002", "Prof. Emily Brontë", null, "Master's", "English Literature",
                List.of("Literature Basics", "Introduction to French"), "bronte@university.edu", "Room 202"));

        facultyList.add(new Faculty("F0003", "Dr. Grace Hopper", null, "Ph.D.", "Computer Programming",
                List.of("Programming Fundamentals", "Operating Systems"), "hopper@university.edu", "Lab 203"));
    }

    public static void addFaculty(Faculty faculty) {
        facultyList.add(faculty);
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

    p
