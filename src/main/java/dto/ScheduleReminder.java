package dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dto.Event.stringToDate;

public class ScheduleReminder {
    private List<Event> events;

    public ScheduleReminder(List<Event> events) {
        this.events = events;
    }

    public void printUpcomingEvents() {
        Instant now = Instant.now();
        for (Event event : events) {
            if (ChronoUnit.MINUTES.between(now, event.getTime().toInstant()) <= 5) {
                System.out.println("Upcoming event: " + event.getName());
            }
        }
    }

    public static void main(String[] args) {
        try {
        List<Event> events = new ArrayList<>();
        Instant nowPlus10Minutes = Instant.now().plus(2, ChronoUnit.MINUTES);
        String formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(nowPlus10Minutes.atZone(ZoneId.systemDefault()));
        System.out.println(formattedDateTime);
        Date d = stringToDate(formattedDateTime);
        events.add(new Event("Event 1", d,
                false, 1, "gay"));

        ScheduleReminder scheduleReminder = new ScheduleReminder(events);
        scheduleReminder.printUpcomingEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}