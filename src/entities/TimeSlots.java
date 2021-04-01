package entities;

import java.io.Serializable;

public class TimeSlots implements Serializable {
    private String timeSlots;

    public TimeSlots() {
    }

    public TimeSlots(String timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        this.timeSlots = timeSlots;
    }

}
