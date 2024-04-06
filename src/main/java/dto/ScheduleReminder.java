package dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static dto.Event.stringToDate;

public class ScheduleReminder {
    private List<Event> events;

    public ScheduleReminder(List<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> printUpcomingEvents() {
        Instant now = Instant.now();
        ArrayList<Event> upcomingEvents = new ArrayList<>();
        for (Event event : events) {
            if (ChronoUnit.MINUTES.between(now, event.getTime().toInstant()) <= 5) {
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
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
            for (Event event : scheduleReminder.printUpcomingEvents()) {
                System.out.println(event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}