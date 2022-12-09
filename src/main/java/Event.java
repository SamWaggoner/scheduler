/*
 * Abstract Class: Event
 * Description: Represents an event on a calendar. Used in a Week.
 * Attributes: described below
 * Bugs: none known
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 */


import java.time.LocalDateTime;


public class Event {


    public LocalDateTime startTime;    // when the event starts
    public LocalDateTime endTime;      // when the event ends
    public Task task;                  // reference to the task that the event is for

    
    // Standard and only constructor
    public Event (LocalDateTime startTime, LocalDateTime endTime, Task task)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.task = task;
    }


    /*
     * Method: toString
     * Description: Prints out the label, day of week, and start and end times
     * of the event in a readable way.
     * Note: Only prints the starting day--if "sleep" starts on Wednesday 11:30 PM
     * and ends on Thursday 7:30 AM, it will print (without formatting):
     * Wednesday, From 11:30 PM to 7:30 AM
     * Arguments: none
     */
    @Override
    public String toString()
    {
        // Skip the placeholder events at the start and end of a week.
        if (this.startTime == this.endTime)
            return "";

        // Format the start date
        int startHr = this.startTime.getHour();
        boolean startAM = startHr < 12;
        String amPmStart;
        if (startAM)
            amPmStart = "AM";
        else
            amPmStart = "PM";
        startHr = startHr % 12;
        int mins = this.startTime.getMinute();
        String startMins;
        if (mins == 0)
            startMins = "00";
        else
            startMins = String.valueOf(mins);

        // Format the end date
        int endHr = this.endTime.getHour();
        boolean endAM = endHr < 12;
        String amPmEnd;
        if (endAM)
            amPmEnd = "AM";
        else
            amPmEnd = "PM";
        endHr = endHr % 12;
        mins = this.endTime.getMinute();
        String endMins;
        if (mins == 0)
            endMins = "00";
        else
            endMins = String.valueOf(mins);


        System.out.printf("%s\n\t%s\n\tFrom %d:%s %s to %d:%s %s",
        this.task.label, this.startTime.getDayOfWeek(), startHr, startMins,
        amPmStart, endHr, endMins, amPmEnd);

        // For reference/clarification only:
        // System.out.printf("%s\n\t%s\n\tFrom %d:%s %s to %d:%s %s",
        //     this.task.label, "Monday", 5, "00", "PM", 6, "30", "PM");

        return "";
    }
}
