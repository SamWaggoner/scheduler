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


    // Sort by different attributes and the reverse of each 
    // Note that sortBy() sorts in ascending order
    Debug.status("...sorting by attributes...");

    ArrayList<Task> orig =
      (ArrayList<Task>) TaskList.tasks.clone();


    ArrayList<Task> byImportance = 
      (ArrayList<Task>) TaskList.sortBy("importance").clone();

    ArrayList<Task> reverseByImportance = new ArrayList<Task>();
    reverseByImportance = (ArrayList<Task>) byImportance.clone();
    TaskList.reverse(reverseByImportance);

    ArrayList<Task> byHrsReq =
      (ArrayList<Task>) TaskList.sortBy("hoursRequired").clone();

    ArrayList<Task> reverseByHrsReq = new ArrayList<Task>();
    reverseByHrsReq = (ArrayList<Task>) byHrsReq.clone();
    TaskList.reverse(reverseByHrsReq);

    ArrayList<Task> byStart =
      (ArrayList<Task>) TaskList.sortBy("start").clone();
    
    ArrayList<Task> reverseByStart = new ArrayList<Task>();
    reverseByStart = (ArrayList<Task>) byStart.clone();
    TaskList.reverse(reverseByStart);

    ArrayList<Task> byDue =
      (ArrayList<Task>) TaskList.sortBy("due").clone();

    ArrayList<Task> reverseByDue = new ArrayList<Task>();
    reverseByDue = (ArrayList<Task>) byDue.clone();
    TaskList.reverse(reverseByDue);

    ArrayList<Task> byTimeframe =
      (ArrayList<Task>) TaskList.sortBy("timeframe").clone();

    ArrayList<Task> byRestriction =
      (ArrayList<Task>) TaskList.sortBy("restriction").clone();



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

    System.out.println("\n------------------------------------------------" +
    "\nBy Importance Reversed:");
    TaskList.printTasks(reverseByImportance); 

    // System.out.println("\n------------------------------------------------" +
    // "\nStatic Original:");
    // TaskList.printTasks();


    
    // By importance, high->low
    Week imp1 = new Week();
    population[0] = imp1;
    imp1.create(reverseByImportance);

    Debug.log("population[0]:");
    Debug.log(population[0]);

    // By hours required, low->high
    Week length1 = new Week();
    population[1] = length1;
    length1.create(byHrsReq);

    // By hours required, high->low
    Week length2 = new Week();
    population[2] = length2;
    length2.create(reverseByHrsReq);

    // By start date, closest->farthest
    Week start1 = new Week();
    population[3] = start1;
    start1.create(byStart);

    // By start date, farthest->closest
    Week start2 = new Week();
    population[4] = start2;
    start2.create(reverseByStart);

    // By due date, closest->farthest
    Week due1 = new Week();
    population[5] = due1;
    due1.create(byDue);

    // By due date, farthest->closest
    Week due2 = new Week();
    population[6] = due2;
    due2.create(reverseByDue);

    // By timeframe (start - due) 
    Week time1 = new Week();
    population[7] = time1;
    time1.create(byTimeframe);

    // By restriction (start - due) - hoursRequired
    Week rest1 = new Week();
    population[8] = rest1;
    rest1.create(byRestriction);

    // Random
    Week rand1 = new Week();
    population[9] = rand1;
    rand1.create(orig);

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


    // // Print weeks
    // for (int i=0; i < population.length; i++)
    // {
    //   if (population[i] != null)
    //   {
    //     Debug.status("...printing week ", i);
    //     Debug.log(population[i]);
    //   }
    // }



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







