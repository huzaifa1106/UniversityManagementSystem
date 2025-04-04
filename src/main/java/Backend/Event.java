/**
 *  File: Event.java
 *  Description: This class is for the Events that occour at UofG,
 *  we can use this for checking schedules, adding a event, and
 *  posting events for other to see and attend
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */
package Backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Image;

public class Event {

    // Static list of events
    private static  List<Event> eventList = new ArrayList<>();

    // Attributes
    private String eventName;
    private String eventCode;
    private String description;
    private Image headerImage;
    private String location;
    private Date dateTime;
    private int capacity;
    private double cost;
    private List<String> registeredStudents;

    // Static block to initialize sample events


    // Constructor
    public Event(String eventName, String eventCode, String description, Image headerImage,
                 String location, Date dateTime, int capacity, double cost,
                 List<String> registeredStudents) {
        this.eventName = eventName;
        this.eventCode = eventCode;
        this.description = description;
        this.headerImage = headerImage;
        this.location = location;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.cost = cost;
        this.registeredStudents = registeredStudents;
    }

    // Static methods to manage the event list
    public static void addEvent(Event event) {
        eventList.add(event);
        ReadExcelFile.eventList.add(event);
        ReadExcelFile.writeToExcel();
    }

    // Method to check if an event already exists
    public static boolean isEventDuplicate(String eventCode, String eventName) {
        for (Event event : eventList) {
            if (event.eventCode.equals(eventCode) || event.eventName.equals(eventName)) {
                return true; // Event is a duplicate
            }
        }
        return false; // Event is not a duplicate
    }

    // Method to add a new event with duplication check
    public static boolean addEventWithValidation(Event newEvent) {
        if (!isEventDuplicate(newEvent.eventCode, newEvent.eventName)) {
            eventList.add(newEvent); // Add the event if it's not a duplicate
            return true; // Event added successfully
        }
        return false; // Event is a duplicate and not added
    }

    public static void removeEvent(String eventCode) {
        eventList.removeIf(event -> event.getEventCode().equalsIgnoreCase(eventCode));
    }

    public static List<Event> getEventList() {
        return eventList;
    }

    // Getter Methods
    public String getEventName() {
        return eventName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getDescription() {
        return description;
    }

    public static void setEventList(List<Event> events) {
        eventList = events;
    }

    public Image getHeaderImage() {
        return headerImage;
    }

    public String getLocation() {
        return location;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCost() {
        return cost;
    }

    public List<String> getRegisteredStudents() {
        return registeredStudents;
    }

    // Setter Methods
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeaderImage(Image headerImage) {
        this.headerImage = headerImage;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Register a student to this event
    public void registerStudent(String studentId) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(studentId);
        } else {
            System.out.println("Event is full.");
        }
    }

    // Display details of the event
    public void viewEventDetails() {
        System.out.println("Event Name: " + eventName);
        System.out.println("Event Code: " + eventCode);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Date and Time: " + dateTime);
        System.out.println("Capacity: " + capacity);
        System.out.println("Cost: " + (cost == 0 ? "Free" : "$" + cost));
        System.out.println("Registered Students: " + registeredStudents);
    }
}
