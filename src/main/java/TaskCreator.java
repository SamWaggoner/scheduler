/*
 * Abstract Class: TaskCreator
 * Description: Creates an ArrayList<Task> given an input file. To do this, also
 * includes a method that formats the date into LocalDateTime.
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 */


import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;


public abstract class TaskCreator {

  
    /*
     * Static Method: createTaskList
     * Description: Reads the input file and returns an array of Task objects
     * Arguments:
     * - path: string, the filepath of the input file
     * Returns:
     * - array of Task objects
     * Uses: none
     * Author: Sam Waggoner <samuel.waggoner@maine.edu>
     * Created: 12/09/22
     * For: COS 470, scheduler
     * Modifications: none
     */
    public static ArrayList<Task> createTaskList(String path) throws IOException
    {

    // Get number of lines in the file so we know how large to make the taskList
    BufferedReader lineReader = new BufferedReader(new FileReader(path));
    int lines = 0;
    while (lineReader.readLine() != null) lines++;
    lineReader.close();

    // Read from the input file
    FileReader inputF = null;
    ArrayList<Task> taskList = new ArrayList<Task>(lines-2);

    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(path));
      String line = reader.readLine();
      
      while (line != null)
      {
        String[] parts = line.split("[<>()]");
        // parts index 0: label
        // parts index 1: importance, hours, (optional) start date
        // parts index 2: due date

        // If the line is the title or whitespace, ignore
        if (parts.length < 3)
        {
          line = reader.readLine();
          continue;
        }

        // Else, create a task out of the information
        Task task = new Task();
        task.setLabel(parts[0].substring(4,parts[0].length()-1));
        String[] subParts = parts[1].split(", ");
        task.setImportance(Integer.parseInt(subParts[0]));
        task.setHoursRequired(Double.parseDouble(subParts[1]));

        // Optional third parameter: start date, default: Today
        if (subParts.length > 3)
          System.err.println("Error: incorrect task format, too many parameters");
        else if (subParts.length == 3)
          task.setStart(formatDate(subParts[2]));

        task.setDue(formatDate(parts[3]));

        taskList.add(task);

        line = reader.readLine();
      }

      reader.close();
    }

    catch(Exception e)
    {
      System.err.println("Error creating tasks from input file. The input file "
      + "should be at ./src/main/resources/input.txt");
    }

    finally
    {
      if (inputF != null) {
        inputF.close();
      }
    }

    
    LocalDate today = LocalDate.now();
    Task task;
    LocalDateTime start;
    LocalDateTime end;

    // Add placeholders for start and end of the week
    task = new Task();

    // Add daily events to schedule: meals, sleep, recreation
    // Starts at 1 since calendar begins the next day after the program is run
    // for (int i = 1; i < 8; i++)
    // {
    //   // Add breakfast
    //   start = today.plusDays(i).atTime(7, 30, 00);
    //   end = today.plusDays(i).atTime(10, 00, 00);
    //   task = new Task("Breakfast", 6, 0.5, start, end);
    //   taskList.add(task);
    //   // Add lunch
    //   start = today.plusDays(i).atTime(11, 00, 00);
    //   end = today.plusDays(i).atTime(14, 30, 00);
    //   task = new Task("Lunch", 8, 0.75, start, end);
    //   taskList.add(task);
    //   // Add dinner
    //   start = today.plusDays(i).atTime(16, 30, 00);
    //   end = today.plusDays(i).atTime(20, 00, 00);
    //   task = new Task("Dinner", 8, 0.75, start, end);
    //   taskList.add(task);
    //   // Add physical activity
    //   start = today.plusDays(i).atTime(6, 00, 00);
    //   end = today.plusDays(i).atTime(23, 00, 00);
    //   task = new Task("Physical Activity", 8, 1, start, end);
    //   taskList.add(task);
    //   // Add sleep
    //   start = today.plusDays(i).atTime(22, 30, 00);
    //   end = today.plusDays(i).atTime(10, 00, 00);
    //   task = new Task("Sleep", 10, 8, start, end);
    //   taskList.add(task);
    // }

    // Add breakfast
    start = today.plusDays(1).atTime(7, 30, 00);
    end = today.plusDays(1).atTime(10, 00, 00);
    task = new Task("Breakfast1", 8, 0.5, start, end);
    taskList.add(task);

    start = today.plusDays(2).atTime(7, 30, 00);
    end = today.plusDays(2).atTime(10, 00, 00);
    task = new Task("Breakfast2", 6, 0.5, start, end);
    taskList.add(task);

    start = today.plusDays(3).atTime(7, 30, 00);
    end = today.plusDays(3).atTime(10, 00, 00);
    task = new Task("Breakfast3", 7, 0.5, start, end);
    taskList.add(task);

    return taskList;
  }


    /*
     * Static Method: formatDate
     * Description: formats a date to LocalDateTime
     * Arguments:
     * - date: string, can be: Mon, Jan 1 or Jan 1 or Today or Tomorrow
     * Returns:
     * - LocalDateTime if successful
     * - nothing if unsuccessful
     * Uses: none
     * Author: Sam Waggoner <samuel.waggoner@maine.edu>
     * Created: 12/09/22
     * For: COS 470, scheduler
     * Modifications: none
     */

    public static LocalDateTime formatDate(String date)
    {
      // If the date is today or tomorrow
      LocalDateTime today = LocalDate.now().atTime(23, 59, 59);
      switch(date)
      {
        case "Tomorrow":
          return today.plusDays(1);
        case "Today":
          return today;
      }

      // Else, turn the date of the task into a LocalDateTime object
      // Account for if the date includes the day of the week or not
      boolean abbvDate;
      if (date.length() < 10)
        abbvDate = true;
      else
        abbvDate = false;

      String monthStr;
      if (abbvDate)
        monthStr = date.substring(0,3);
      else
        monthStr = date.substring(5,8);

      Month month = Month.of(1);
      switch(monthStr)
      {
        case "Jan":
          month = Month.of(1);
          break;
        case "Feb":
          month = Month.of(2);
          break;
        case "Mar":
          month = Month.of(3);
          break;
        case "Apr":
          month = Month.of(4);
          break;
        case "May":
          month = Month.of(5);
          break;
        case "Jun":
          month = Month.of(6);
          break;
        case "Jul":
          month = Month.of(7);
          break;
        case "Aug":
          month = Month.of(8);
          break;
        case "Sep":
          month = Month.of(9);
          break;
        case "Oct":
          month = Month.of(10);
          break;
        case "Nov":
          month = Month.of(11);
          break;
        case "Dec":
          month = Month.of(12);
          break;
        default:
          System.err.println("Error: Date formatted incorrectly in task for date "
          + date + " and monthstring: " + monthStr + ".");
      }

      int day;
      if (abbvDate)
        day = Integer.parseInt(date.substring(4,date.length()));
      else
        day = Integer.parseInt(date.substring(9,date.length()));

      return LocalDateTime.of(today.getYear(), month, day, 23, 59, 59);
    }
}
