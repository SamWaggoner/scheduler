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


    // Unused setter methods to cleanse input if desired -----------------------

    public boolean setLabel(String label)
    {
        this.label = label;
        return true;
    }


    public boolean setNotes(String notes)
    {
        this.notes = notes;
        return true;
    }


    public boolean setImportance(int imp)
    {
        if (imp > 0 && imp < 11)
        {
            this.importance = imp;
            return true;
        }
        System.err.println("Error: Importance must be 0-10");
        return false;
    }


    public boolean setHoursRequired(double hrs)
    {
        if (hrs > 0)
        {
            this.hoursRequired = hrs;
            return true;
        }
        System.err.println("Error: Hours required must be positive integer");
        return false;
    }


    public boolean setStart(LocalDateTime start)
    {
        if (this.due == null || this.due != null && this.due.isAfter(start))
        {
            this.start = start;
            return true;
        }
        System.err.println("Error: Start date must be before due date");
        return false;
    }


    public boolean setDue(LocalDateTime due)
    {
        if (this.start == null || this.start != null && this.start.isBefore(due))
        {
            this.due = due;
            return true;
        }
        System.err.println("Error: Due date must be after start date");
        return false;
    }

    /* UNUSED
     * Method: printData
     * Description: Prints out the information of this task in a formatted way.
     */
    public void printData()
    {
        System.out.println("Task: " + this.label);
        System.out.println("    Notes: " + this.notes);
        System.out.println("    Importance: " + this.importance);
        System.out.println("    Hours Required: " + this.hoursRequired);
        System.out.println("    Start Date: " + this.start);
        System.out.println("    Due Date: " + this.due + "\n");
    }


    @Override
    public String toString() {
        return "Task: " + label + "\n\t notes: " + notes + "\n\t importance: " + 
        importance + "\n\t hoursRequired: " + hoursRequired + "\n\t start: " +
        start + "\n\t due: " + due + "\n";
    }
}