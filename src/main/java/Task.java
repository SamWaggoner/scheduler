/*
 * Class: Task
 * Description: Holds information about a task that someone must do.
 * Attributes: described below
 * Bugs: none
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Input Requirements:
 * - Do not use parentheses or carrots: (,),<,> in the label unless for syntax.
 * - Start time is assumed to start at midnight on specified day.
 */


import java.time.LocalDateTime;


public class Task {

    public String label;            // the "name" of the task
    public String notes;            // any notes
    public int importance;          // integer 1-10
    public double hoursRequired;    // estimated hours required to finish task
    public LocalDateTime start;     // date when you may start working on it
    public LocalDateTime due;       // date by which it must be finished
    public double hrsInCalendar;    // total hours in a calendar, used for
                                    // calculating worth


    public Task (){}


    // "Standard" constructor
    public Task(String label, int importance, double hrs, LocalDateTime due)
    {
        this.label = label;
        this.importance = importance;
        this.hoursRequired = hrs;
        this.due = due;
    }

    // Constructor with just label for start and end of week
    public Task(String label)
    {
        this.label = label;
    }


    // Constructor that includes notes
    public Task(String label, String notes, int importance, double hrs, LocalDateTime due)
    {
        this.label = label;
        this.notes = notes;
        this.importance = importance;
        this.hoursRequired = hrs;
        this.due = due;
    }

    // Constructor that includes start time
    public Task(String label, int importance, double hrs, LocalDateTime start, LocalDateTime due)
    {
        this.label = label;
        this.importance = importance;
        this.hoursRequired = hrs;
        this.start = start;
        this.due = due;
    }


    /*
     * Method: setLabel
     * Description: Used to set the label of a task. Could be modified to
     * sanitize input.
     */
    public boolean setLabel(String label)
    {
        this.label = label;
        return true;
    }


    /*
     * Method: setNotes
     * Description: Used to set the notes of a task. Could be modified to
     * sanitize input.
     */
    public boolean setNotes(String notes)
    {
        this.notes = notes;
        return true;
    }


    /*
     * Method: setImportance
     * Description: Used to set the importance of a task. Sanitizes input, makes
     * sure integer is 1-10 inclusive.
     */
    public void setImportance(int imp)
    {
        if (imp > 0 && imp < 11)
        {
            this.importance = imp;
            return;
        }
        Debug.err("Error: Importance must be 0-10, given: ",imp);
        System.exit(1);
    }


    /*
     * Method: setImportance
     * Description: Used to set the hours required of a task. Sanitizes input,
     * makes sure hours is positive.
     */
    public void setHoursRequired(double hrs)
    {
        if (hrs > 0)
        {
            this.hoursRequired = hrs;
            return;
        }
        Debug.err("Error: Hours required must be positive integer, given: ",hrs);
        System.exit(1);
    }


    /*
     * Method: setStart
     * Description: Used to set when you can start working on a task. Sanitizes
     * input, makes sure start date is not before when it is due.
     */
    public void setStart(LocalDateTime start)
    {
        if (this.due == null || this.due != null && this.due.isAfter(start))
        {
            this.start = start;
            return;
        }
        Debug.err("Error: Start date must be before due date (",this.due,") for",
            " given start date: ",start);
        System.exit(1);
    }


    /*
     * Method: setDue
     * Description: Used to set the due date of a task. Sanitizes input, makes
     * sure due date is not before you can start working on the task.
     */
    public void setDue(LocalDateTime due)
    {
        if (this.start == null || this.start != null && this.start.isBefore(due))
        {
            this.due = due;
            return;
        }
        Debug.err("Due date must be after start date (",this.start,") for given",
        " due date: ",due);
        System.exit(1);
    }


    /*
     * Method: toString
     * Description: Returns all of the information about the task in a formatted way.
     */
    @Override
    public String toString() {
        return "Task: " + label + "\n\t notes: " + notes + "\n\t importance: " + 
        importance + "\n\t hoursRequired: " + hoursRequired + "\n\t start: " +
        start + "\n\t due: " + due + "\n";
    }
}