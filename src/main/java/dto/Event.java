package dto;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Event {

    public String name;
    public Date eventDate;

    public boolean checked;

    public int priority;

    public String subject;

    public Event(String name, Date EventDate, boolean checked, int priority, String subject) {
        this.name = name;
        this.eventDate = EventDate;
        this.checked = checked;
        this.priority = priority;
        this.subject = subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setChecked (boolean checked) {
        this.checked = checked;
    }

    public static Date stringToDate(String date) {
        String[] dateParts = date.split(" ");
        if (dateParts.length != 2) {
            return null;
        }
        String[] dayParts = dateParts[0].split("-");
        String[] timeParts = dateParts[1].split(":");
        if (dayParts.length != 3 || timeParts.length != 2) {
            return null;
        }
        try {
            int year = Integer.parseInt(dayParts[0]);
            int month = Integer.parseInt(dayParts[1]);
            int day = Integer.parseInt(dayParts[2]);
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            int monthInstance = switch (month) {
                case 1 -> Calendar.JANUARY;
                case 2 -> Calendar.FEBRUARY;
                case 3 -> Calendar.MARCH;
                case 4 -> Calendar.APRIL;
                case 5 -> Calendar.MAY;
                case 6 -> Calendar.JUNE;
                case 7 -> Calendar.JULY;
                case 8 -> Calendar.AUGUST;
                case 9 -> Calendar.SEPTEMBER;
                case 10 -> Calendar.OCTOBER;
                case 11 -> Calendar.NOVEMBER;
                case 12 -> Calendar.DECEMBER;
                default -> -1;
            };
            if (monthInstance == -1) {
                return null;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthInstance, day, hour, minute);
            return new Date(calendar.getTimeInMillis());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getEventDate() {
        return eventDate.toString();
    }

    public String isCompleted() {
        if (checked) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public String getPriority() {
        if (priority == 1) {
            return "Low";
        } else if (priority == 2) {
            return "Medium";
        } else {
            return "High";
        }
    }

    public String getSubject() {
        return subject;
    }

    public Date getTime() {
        return eventDate;
    }

    public String toString() {
        return name + " " + eventDate + " " + checked + " " + priority + " " + subject;
    }
}
