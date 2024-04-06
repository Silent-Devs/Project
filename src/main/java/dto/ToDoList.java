package dto;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

public class ToDoList {

    private Date date;
    private List<Event1> events;

    public ToDoList() {
        this.date = new Date();
        this.events = new ArrayList<>();
    }

    public ToDoList(Date date, List<Event1> events) {
        this.date = date;
        this.events = events;
    }

    public void addEvent(Event1 event) {
        events.add(event);
    }

    public void removeEvent(Event1 event) {
        events.remove(event);
    }

    public void addSubject(String name, String subject) {
        for (Event1 event : events) {
            if (event.getName().equals(name)) {
                event.setSubject(subject);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void removeSubject(String name) {
        for (Event1 event : events) {
            if (event.getName().equals(name)) {
                event.setSubject(null);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void displayEvents() {
        for (Event1 event : events) {
            System.out.println("Name: " + event.getName());
            System.out.println("Date: " + event.getEventDate());
            System.out.println("Completed: " + event.isCompleted());
            System.out.println("Priority: " + event.getPriority());
            System.out.println("Subject: " + event.getSubject());
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        System.out.println("hi");
        System.out.println(toDoList.date);
        toDoList.addEvent(new Event1("test", new Date(), false, 1, "test"));
        toDoList.displayEvents();
    }
}