/*
 * Class: Scheduler
 * Description: Contains the main function for the project.
 * Attributes: none
 * Bugs: none
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Instructions: 
 * 1. cd OneDrive\Desktop\COS_470\scheduler
 * 2. gradle run
 * 
 * Notes:
 * About method comments--since there can be many methods in a class, for
 * ease of readibility, only fields that add value will be included. If fields
 * like Author, Created, and For are not listed, refer to the class declaration
 * comment at the top of the file and assume the values to be the same.
 */


import java.io.IOException;
import java.util.ArrayList;


public class Scheduler {

/*
 * Method: main
 * Description: 
 * 1. creates a task list from the input file.
 * 2. creates an initial population of Weeks.
 * 3. selects the best Weeks to continue on to the next generation.
 * 4. mutates those weeks, creating the new generation.
 * 5  repeats from step 3 until a value threshold is met or a number of
 *    generations is exceeded.
 */
  public static void main(String... args) throws IOException
  {

    ////////////////////////////////////////////////////////////////////////////
    // Create task list from input file ----------------------------------------

    // Create the task list from the input file
    Debug.status("...reading from input file...");
    TaskList.tasks = TaskCreator.createTaskList
                                            ("./src/main/resources/input.txt");


    ////////////////////////////////////////////////////////////////////////////
    // Initialize the original population --------------------------------------

    // size of initial population, >= 20 (random calendars created for members = 20+)
    int numMembers = 25;

    Week[] population = new Week[numMembers];


    // Create weeks (calendars) from the task list

    /*
     * descending vs ascending
     * 
     * importance
     * due date
     * hours required
     * start date
     */

    // Sort by different attributes (in ascending order)
    Debug.status("...sorting by attributes...");
    ArrayList<Task> orig =
      (ArrayList<Task>) TaskList.tasks.clone();


    ArrayList<Task> byImportance = 
      (ArrayList<Task>) TaskList.sortBy("importance").clone();

    ArrayList<Task> byHrsReq =
      (ArrayList<Task>) TaskList.sortBy("hoursRequired").clone();

    ArrayList<Task> byStart =
      (ArrayList<Task>) TaskList.sortBy("start").clone();

    ArrayList<Task> byDue =
      (ArrayList<Task>) TaskList.sortBy("due").clone();

    // After this point TaskList will be unchanged and its list of tasks will be
    // referred to by index by Events.
    TaskList.tasks = orig;


    // System.out.println("\n------------------------------------------------" +
    // "\nOriginal:");
    // TaskList.printTasks(orig);

    // System.out.println("\n------------------------------------------------" +
    // "\nBy Hours Required:");
    // TaskList.printTasks(byHrsReq);

    // System.out.println("\n------------------------------------------------" +
    // "\nBy Due Date:");
    // TaskList.printTasks(byDue);

    // System.out.println("\n------------------------------------------------" +
    // "\nBy Start Date:");
    // TaskList.printTasks(byStart);

    System.out.println("\n------------------------------------------------" +
    "\nBy Importance:");
    TaskList.printTasks(byImportance); 

    // System.out.println("\n------------------------------------------------" +
    // "\nStatic Original:");
    // TaskList.printTasks();

    
    // By importance, high->low, ending at 9:00pm
    Week imp1 = new Week();
    population[0] = imp1;
    ArrayList<Task> reverseByImportance = new ArrayList<Task>();
    reverseByImportance = (ArrayList<Task>) byImportance.clone();
    reverseByImportance = TaskList.reverse(reverseByImportance);
    // imp1.create(reverseByImportance);
    imp1.create(reverseByImportance);

    Debug.status("...printing week...");
    System.out.println(population[0]);

    // By length, low->high, ending at 9:00pm

    // By length, high->low, ending at 9:00pm

    // By due date, closest->farthest, ending at 9:00pm

    // By importance + (10-length), high->low, ending at 9:00pm

    // By importance + (10-length) - distance to due date, high->low, ending at 9:00pm

    // By importance + (20-length) - distance to due date, high->low, ending at 9:00pm

    // By importance + (30-length) - distance to due date, high->low, ending at 9:00pm

    // By importance + (40-length) - distance to due date, high->low, ending at 9:00pm

    // By importance + (40-length) - distance to due date, high->low, ending at 9:00pm

    // By importance, high->low, ending at 12:00pm

    // By length, low->high, ending at 12:00pm

    // By length, high->low, ending at 12:00pm

    // By due date, closest->farthest, ending at 12:00pm

    // By importance + (10-length), high->low, ending at 12:00pm

    // By importance + (10-length) - distance to due date, high->low, ending at 12:00pm

    // By importance + (20-length) - distance to due date, high->low, ending at 12:00pm

    // By importance + (30-length) - distance to due date, high->low, ending at 12:00pm

    // By importance + (40-length) - distance to due date, high->low, ending at 12:00pm

    // By importance + (40-length) - distance to due date, high->low, ending at 12:00pm

    // (members-20) random calendars



    // number of survivors each generation
    int numSurvivors = 15;

    // percent mutation (random changes to single calendar in the group of survivors)
    double mutationRate = .30; 

    // percent crossover (combination between two calendars)
    double crossoverRate = 1-mutationRate;

    // number of generations
    int numGenerations = 5;

    ////////////////////////////////////////////////////////////////////////////
    // Select the s best calendars ---------------------------------------------

    // The fitness function incorporates 
    // reprimanded for events late in the night or early in the morning
    // rewarded for the amount of worth

    // Percent miosis (single reproduction)
    // Percent reproduction (merger)
    // Start size

    /**
     * Types of modifications/evolutions:
     * - Big changes:
     *      - Replacing events 
     */

    // Create start population of start size

    // For n iterations (or for a certain amount of time)? (or until fitness > q)?
    
    
    // Perform insertion sort on the weeks by their total worth,
    // and select the best to survive.
    // List<Week> survivors = InsertionSort.sort(weeks).subList(0,numSurvivors);

    
    // List<Week> survivors = weeks.sort(Comparator.comparing(Week.totalWorth)).subList(0,numSurvivors);

    // // 
    // for (int i=0; i < numGenerations; i++)
    // {
    //   for (int j=0; j < numSurvivors; j++)
    //   {
    //     Week week =  survivors.get(j);
    //     // mutate week
    //     weeks.add(week);
    //   }
    // }
  }

  
  public static void checkDue(ArrayList<Task> arr)
  {
    String res = "not failed due";
    for (int i=0; i < arr.size()-1; i++)
    {
      if (!arr.get(i).due.isBefore(arr.get(i+1).due))
      {
        res = "failed due";
      }
    }
    System.out.println(res);
  }
}







