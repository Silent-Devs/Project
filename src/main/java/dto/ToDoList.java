package dto;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

public class ToDoList {

    private Date date;
    private List<Event> events;

    public ToDoList() {
        this.date = new Date();
        this.events = new ArrayList<>();
    }

    public ToDoList(Date date, List<Event> events) {
        this.date = date;
        this.events = events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void addSubject(String name, String subject) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                event.setSubject(subject);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void removeSubject(String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                event.setSubject(null);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void displayEvents() {
        for (Event event : events) {
            System.out.println("Name: " + event.getName());
            System.out.println("Date: " + event.getEventDate());
            System.out.println("Completed: " + event.isCompleted());
            System.out.println("Priority: " + event.getPriority());
            System.out.println("Subject: " + event.getSubject());
            System.out.println("--------------------");
        }
    }
}