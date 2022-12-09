/*
 * Class: Week
 * Description: Represents a week's time using Event objects. Instead of
 * accounting for every 15 minute interval in the week, to save space I only
 * keep track of the events present. If a given interval is outside of the range
 * of every event in the week, then it is unscheduled time.
 * Attributes:
 * - events: the week itself, represented as a series of Event objects
 * - tasks: the task list used to create the events. events are added one-by-one
 *      so the order of the tasks list dictates the events.
 * Bugs: none known
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


    public ArrayList<Event> events;
    public ArrayList<Task> tasks;


    public Week(){}

    public Week(ArrayList<Event> week)
    {
        this.events = week;
    }

    /*
     * Method: getWorth
     * Description: Finds the worth of the entire week by iterating through
     * each event and adding the total time that each task is in the week. Then,
     * using the worth function (as seen in the documentation) calculates the
     * worth of each task based off its time in the calendar, importance, and
     * hours required. Sums each task's worth to get the result.
     * Returns: the worth of the entire week
     */
    public double getWorth()
    {
        boolean prevDebugEnabled = Debug.enabled;
        Debug.enabled = false;
        
        for (Event event : this.events)
        {
            if (event.task != null)
            {
                double duration = ChronoUnit.MINUTES.between(event.startTime, event.endTime);
                event.task.hrsInCalendar += duration;
            }
        }

        double worthSum = 0;
        for (Task task : this.tasks)
        {
            Debug.log("Task Importance: ", task.importance);
            Debug.log("Task estimated task: ", task.hoursRequired);
            Debug.log("Task Hours in Calendar: ", task.hrsInCalendar);

            if (task.hrsInCalendar < 0)
                // If task not in calendar, it has no worth
                continue;
            double taskWorth = (0.8 * task.importance) / 
                    (1 + Math.exp(-1*task.hrsInCalendar+0.8*task.hoursRequired))
                    + 0.2 * task.importance;
            worthSum += taskWorth;
            Debug.log("Worth of ", task.label,": ",taskWorth);
        }

        Debug.enabled = prevDebugEnabled;

        // Round to three digits
        worthSum = Math.round(worthSum*1000.0)/1000.0;
        return worthSum;
    }


    /*
     * Method: create
     * Description: Turns a given list of tasks into a calendar of events. Also
     * updates the ArrayList<Task> reference for the object.
     * To enable verbose debugging information, remove the line at the top of
     * this function that turns off the debugger.
     * Returns: the number of tasks that were unable to be scheduled
     */
    public int create(ArrayList<Task> tasks)
    {
        boolean prevDebugEnabled = Debug.enabled;
        Debug.enabled = true;

        // Add task reference to this object for calculating worth
        this.tasks = tasks;

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
        int numEventsFailed = 0;
        for (Task task : tasks)
        {
            if (!addEvent(task))
                numEventsFailed++;
        }

        Debug.enabled = prevDebugEnabled;

        return numEventsFailed;
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
     * // note that you're trying to insert AFTER the event you're "on" and
     * // BEFORE the next event
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
        boolean prevDebugEnabled = Debug.enabled;
        Debug.enabled = false;

        Debug.log("\n\nAdding event ", task.label);
        Debug.log("to week");
        Debug.log(this);

        for (int i=0; i < this.events.size(); i++)
        {
            if (this.events.get(i).endTime.isEqual(LocalDate.now().plusDays(8).atTime(0,0,0)))
            {
                // FAIL, no space in calendar
                Debug.log("Task ",task.label,", due on ",task.due," was unable",
                    " to be added because the calendar is completely full.");
                Debug.enabled = prevDebugEnabled;
                return false;
            }
            if (task.due.isBefore(this.events.get(i).endTime))
            {
                // FAIL, Due date already passed
                Debug.log("Task ",task.label,", due on ",task.due," was unable\n",
                    " to reach its required hours to finish because the calendar",
                    " is full up to its due date.");
                Debug.enabled = prevDebugEnabled;
                return false;
            }
            if (ChronoUnit.MINUTES.between(
                this.events.get(i).endTime, this.events.get(i+1).startTime) <= 5)
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
                    Debug.enabled = prevDebugEnabled;
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
                    // add event based off the hours required, starting at task's start time
                    Event newEvent = new Event(task.start,
                        task.start.plusMinutes((long)(60*task.hoursRequired)),
                        task);
                    events.add(i+1, newEvent);
                    // return true, indicating a successful insertion
                    Debug.enabled = prevDebugEnabled;
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
        Debug.enabled = prevDebugEnabled;
        return false;
    }

    
    /*
     * Method: toString
     * Description: Prints the events in the week in an comprehensible way.
     * Returns: "", does the printing itself
     */
    @Override
    public String toString()
    {
        System.out.println("----------------------------------------");
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