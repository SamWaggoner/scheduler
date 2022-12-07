/*
 * Class: Week
 * Description: Represents a week's time using Event objects. Instead of
 * accounting for every 15 minute interval in the week, to save space I only
 * keep track of the events present. If a given interval is outside of the range
 * of every event in the week, then it is unscheduled time.
 * Attributes:
 * - int totalWorth: The total worth calculated for the calendar.
 * Bugs: none
 * Limitations: none
 * Input Requirements: travel/transition time should be incorporated into the 
 * events themselves. If it takes 15 minutes to walk to class, then the class
 * event should start 15 minutes earlier than the actual class.
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 */


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class Week {


    public int totalWorth;
    public ArrayList<Event> events;


    public Week(){}

    public Week(ArrayList<Event> week)
    {
        this.events = week;
    }


    /*
     * Method: create
     * Description: Turns a given list of tasks into a calendar of events.
     * To enable verbose debugging information, remove the line at the top of
     * this function that turns off the debugger.
     * Returns: the number of tasks that were unable to be scheduled
     */
    public int create(ArrayList<Task> tasks)
    {
        boolean prevDebugEnabled = Debug.enabled;
        Debug.enabled = false;

        // Check if calendar already exists for object
        if (this.events != null)
        {
            Debug.err("error: overwriting existing events when creating week");
            return -1;
        }

        this.events = new ArrayList<Event>();

        // Create start and end placeholder Events (just for functionality)
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime start = tomorrow.atTime(0, 0, 0);
        LocalDateTime end = tomorrow.plusDays(7).atTime(0, 0, 0);
        Event startWk = new Event(start, start, null);
        Event endWk = new Event(end, end, null);
        this.events.add(startWk);
        this.events.add(endWk);

        // Add each of the tasks in the given tasks list
        Debug.status("...adding events to calendar...");
        int numEventsFailed = 0;
        for (Task task : tasks)
        {
            if (!addEvent(task))
                numEventsFailed++;
        }

        Debug.enabled = prevDebugEnabled;

        return numEventsFailed;
    }


    public int getTotalWorth()
    {
        return this.totalWorth;
    }

    /* 
     * Method: addEvent
     * Description: Tries to fit in a task to the calendar. May turn the task
     * into one or more events. When it does so, it adds to the total worth,
     * since it is cheaper to do this than to recalculate the total worth
     * anytime you want to access it.
     * Arguments:
     * - task: task that you want to add to the object's calendar
     * Returns:
     * - true if it was able to add it completely
     * - false if it was able to add it partially or not at all
     */

    /**
     * Pseudocode for addEvent():
     * 
     * // youre trying to insert AFTER event and BEFORE the next event
     * for event in events
     *  if event.label = endOfweek:
     *      // FAIL, return fail
     *  // if the task's due date has already passed
     *  if task.due is before event.end
     *      // FAIL, return fail
     *  if time between event and next is 0
     *      continue;
     *  if task.start is before event.end:
     *      if the task can be fully inserted
     *          // insert from event's end time using the task's hoursRequired
     *          // return success
     *      else (the task is longer than the available time)
     *          // insert from event's end to next event's start
     *          // reduce duration of task's hoursRequired, continue
     *      continue;   // this could be indented, it doesn't make a difference
     *  if task.start is before event.next.start
     *      if the task can be fully inserted
     *          // insert from task's start time using the task's hoursRequired
     *          // return success
     *      else (the task is longer than the available time)
     *          // insert from task's start to next event's start
     *          // reduce duration of task's hoursRequired, continue
     *      continue;   // this could be indented, it doesn't make a difference
     *  else
     *      // print error, this should never be reached
     */
    public boolean addEvent(Task task)
    {
        Debug.log("\n\nAdding event ", task.label);
        Debug.log("to week");
        Debug.log(this);
        for (int i=0; i < this.events.size(); i++)
        {
            if (this.events.get(i).endTime == LocalDate.now().atTime(23,59,59).plusDays(7))
            {
                // FAIL, no space in calendar
                Debug.log("Task ",task.label,", due on ",task.due," was unable",
                    " to be added because the calendar is completely full.");
                return false;
            }
            if (task.due.isBefore(this.events.get(i).endTime))
            {
                // FAIL, Due date already passed
                Debug.log("Task ",task.label,", due on ",task.due," was unable",
                    " to be added because the calendar is full up to its due date.");
                return false;
            }
            if (ChronoUnit.MINUTES.between(
                this.events.get(i).endTime, this.events.get(i+1).startTime) <= 1)
            {
                Debug.log("Skipping over event ", this.events.get(i));
                continue;
            }
            if (task.start.isBefore(this.events.get(i).endTime))
            {
                double gap = ChronoUnit.HOURS.between(this.events.get(i).endTime, 
                                                this.events.get(i+1).startTime);
                // If the task can be fully inserted
                if (gap >= task.hoursRequired)
                {
                    Debug.log("Task fully inserted at end of event");
                    // add event based off the hours required, starting at event's end time
                    Event newEvent = new Event(this.events.get(i).endTime,
                        this.events.get(i).endTime.plusMinutes((long)(60*task.hoursRequired)),
                        task);
                    events.add(i+1, newEvent);
                    // return true, indicating a successful insertion
                    return true;
                }
                // If the task is longer then available time
                else
                {
                    Debug.log("Task inserted into time between events, longer than gap");
                    // add event based off the available time between events
                    Event newEvent = new Event(this.events.get(i).endTime,
                        this.events.get(i+1).startTime, task);
                    events.add(i+1, newEvent);
                    task.hoursRequired -= gap;
                }
                // and try to find a time to finish the rest of the task later
                continue;
            }
            if (task.start.isBefore(this.events.get(i+1).startTime))
            {
                double gap = ChronoUnit.HOURS.between(task.start, 
                                                this.events.get(i+1).startTime);
                // If the task can be fully inserted
                if (gap >= task.hoursRequired)
                {
                    Debug.log("Task fully inserted, at start time");
                    Debug.log("Inserting for ", (long)(60*task.hoursRequired), " minutes");
                    // add event based off the hours required, starting at task's start time
                    Event newEvent = new Event(task.start,
                        task.start.plusMinutes((long)(60*task.hoursRequired)),
                        task);
                    events.add(i+1, newEvent);
                    Debug.log(events.get(i+1).startTime," to ",events.get(i+1).endTime);
                    // return true, indicating a successful insertion
                    return true;
                }
                // If the task is longer then available time
                else
                {
                    Debug.log("Task partially inserted");
                    // add event based off the available time between start time and next event
                    Event newEvent = new Event(task.start,
                        this.events.get(i+1).startTime, task);
                    events.add(i+1, newEvent);
                    task.hoursRequired -= gap;
                }
                // and try to find a time to finish the rest of the task later
                continue;
            }
            Debug.log("checking next event, start hasn't occurred yet");
        }
        return false;
    }

    
    /*
     * Method: toString (previously printWeek)
     * Description: Prints the events in the week.
     */
    @Override
    public String toString()
    {
        System.out.println("-----------------------------------------------");
        System.out.println("-----------------------------------------------\n");
        for (Event event : this.events)
        {
            System.out.println(event);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("-----------------------------------------------\n");
        return "";
    }
}