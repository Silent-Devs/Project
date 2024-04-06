package dto;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.io.Serializable;

public class ToDoListDto {

    public Date date;
    public static List<Event> events = new ArrayList<>();

    public ToDoListDto() {
        this.date = new Date();
        events = new ArrayList<>();
    }

    public ToDoListDto(Date date, List<Event> events) {
        this.date = date;
        ToDoListDto.events = events;
    }

    public static void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public static void addSubject(String name, String subject) {
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