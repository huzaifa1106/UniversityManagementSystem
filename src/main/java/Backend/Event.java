/**
 * File: Event.java
 * Description: Represents an event at the University of Guelph. Handles functionality for event
 * creation, duplication validation, student registration, and data access for event listings.
 * Author: Huzaifa A. & Group
 * Date: March 2nd, 2025
 */

package Backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Image;

public class Event {

    // Static list storing all events
    private static List<Event> eventList = new ArrayList<>();

    // Event properties
    private String eventName;
    private String eventCode;
    private String description;
    private Image headerImage;
    private String location;
    private Date dateTime;
    private int capacity;
    private double cost;
    private List<String> registeredStudents;

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

    // Add event directly to the list and persist it
    public static void addEvent(Event event) {
        eventList.add(event);
        ReadExcelFile.eventList.add(event);
        ReadExcelFile.writeToExcel();
    }


    // Remove event by its unique code
    public static void removeEvent(String eventCode) {
        eventList.removeIf(event -> event.getEventCode().equalsIgnoreCase(eventCode));
    }

    // Static accessor for all events
    public static List<Event> getEventList() {
        return eventList;
    }
    public static Event getEventByCode(String code) {
        for (Event event : eventList) {
            if (event.getEventCode().equalsIgnoreCase(code)) {
                return event;
            }
        }
        return null;
    }

    public static void setEventList(List<Event> events) {
        eventList = events;
    }

    // Register a student by their ID
    public void registerStudent(String studentId) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(studentId);
        } else {
            System.out.println("Event is full.");
        }
    }
    // Getters
    public String getEventName() { return eventName; }
    public String getEventCode() { return eventCode; }
    public String getDescription() { return description; }
    public Image getHeaderImage() { return headerImage; }
    public String getLocation() { return location; }
    public Date getDateTime() { return dateTime; }
    public int getCapacity() { return capacity; }
    public double getCost() { return cost; }
    public List<String> getRegisteredStudents() { return registeredStudents; }

    // Setters
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }
    public void setDescription(String description) { this.description = description; }
    public void setHeaderImage(Image headerImage) { this.headerImage = headerImage; }
    public void setLocation(String location) { this.location = location; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setCost(double cost) { this.cost = cost; }
}
