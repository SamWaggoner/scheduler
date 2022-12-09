/*
 * Class: Scheduler
 * Description: Contains the main function for the project.
 * Attributes: none
 * Bugs: none known
 * Limitations: output color is not supported for text files, only terminal
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Instructions: 
 * 1. cd OneDrive\Desktop\COS_470\scheduler (or whatever directory this is in)
 * 2. gradle run
 * 
 * Notes:
 * About method comments--since there can be many methods in a class, for
 * ease of readibility only fields that add value will be included. If fields
 * like Author, Created, and For are not listed, refer to the class declaration
 * comment at the top of the file and assume the values to be the same.
 * 
 * If you want to direct standard output to a file instead of terminal,
 * refer to the first line in the main function below.
 */


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;


public class Scheduler {

/*
 * Method: main
 * Description: 
 * 1. creates a task list from the input file.
 * 2. creates an initial population of Weeks.
 * 3. selects the best Weeks to continue on to the next generation.
 * 4. TODO: crosses and mutates those weeks and adds the best of the old generation,
 *    creating the new generation.
 * 5  TODO: repeats from step 3 until a value threshold is met or a number of
 *    generations is exceeded.
 */
  public static void main(String... args) throws IOException
  {

    // If you wish to redirect output to a text file, make sure the line below
    // is uncommented. Note that colors are not supported in text files, so
    // debug statements' special characters will be unrecognized.
    // System.setOut(new PrintStream(new File("./src/main/resources/output.txt")));

    ////////////////////////////////////////////////////////////////////////////
    // Create task list from input file ----------------------------------------

    // Create the task list from the input file
    Debug.status("...reading from input file...");
    TaskList.tasks = TaskCreator.createTaskList("./src/main/resources/input.txt");


    ////////////////////////////////////////////////////////////////////////////
    // Initialize the original population --------------------------------------

    // As of 12/8/22, there are:
    // - 11 weeks sorted by various methods
    // - 6 random weeks
    // that make up the original population.

    ArrayList<Object[]> population = new ArrayList<Object[]>();


    // Sort by different attributes and the reverse of each 
    // Note that sortBy() sorts in ascending order UNLESS specefied otherwise,
    //    for example, if "high->low" is included

    Debug.status("...sorting by attributes...");

    ArrayList<Task> orig= new ArrayList<Task>();
    orig = (ArrayList<Task>) TaskList.tasks.clone();

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

    ArrayList<Task> bySmallImportant =
      (ArrayList<Task>) TaskList.sortBy("bySmallImportant").clone();

    ArrayList<Task> byImportantSmall =
      (ArrayList<Task>) TaskList.sortBy("byImportantSmall").clone();

    // Note: random isn't completely random. There is a 50% chance that two
    //       elements in the ArrayList are switched when merging. However, it
    //       does get increasingly more random as more random lists are built
    //       upon previous random lists. The original list is used to start.

    TaskList.tasks = orig;

    ArrayList<Task> byRandom1 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();

    ArrayList<Task> byRandom2 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();

    ArrayList<Task> byRandom3 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();

    ArrayList<Task> byRandom4 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();

    ArrayList<Task> byRandom5 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();

    ArrayList<Task> byRandom6 =
      (ArrayList<Task>) TaskList.sortBy("random").clone();


    // DEBUGGING:
    // To view or ensure correctness of tasks sorted by certain attributes,
    // uncomment any of the blocks below, or add as desired.

    // Debug.log("\n------------------------------------------------" +
    // "\nOriginal:");
    // TaskList.printTasks(orig);

    // Debug.log("\n------------------------------------------------" +
    // "\nBy Hours Required:");
    // TaskList.printTasks(byHrsReq);

    // Debug.log("\n------------------------------------------------" +
    // "\nBy Due Date:");
    // TaskList.printTasks(byDue);

    // Debug.log("\n------------------------------------------------" +
    // "\nBy Start Date:");
    // TaskList.printTasks(byStart);

    // Debug.log("\n------------------------------------------------" +
    // "\nBy Importance:");
    // TaskList.printTasks(byImportance); 

    // Debug.log("\n------------------------------------------------" +
    // "\nBy Importance Reversed:");
    // TaskList.printTasks(reverseByImportance); 

    // Debug.log("\n------------------------------------------------" +
    // "\nStatic Original:");
    // TaskList.printTasks();



    // Create weeks (calendars with events) using the sorted task lists
    Debug.status("...adding events to week...");
    int numEventsFailed;
    String name;


    // logic: do important tasks first
    name = "by importance, high->low"; 
    Week imp1 = new Week();
    numEventsFailed = imp1.create(reverseByImportance);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] pop0 = {imp1, imp1.getWorth(), name};
    insertSorted(population,pop0);

    // logic: do small tasks first
    name = "by hours required, low->high";
    Week length1 = new Week();
    numEventsFailed = length1.create(byHrsReq);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] len1 = {length1, length1.getWorth(), name};
    insertSorted(population,len1);

    // logic: do big tasks first
    name = "by hours required, high->low";
    Week length2 = new Week();
    numEventsFailed = length2.create(reverseByHrsReq);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] len2 = {length2, length2.getWorth(), name};
    insertSorted(population,len2);

    // logic: don't procrastinate
    name = "by start date, closest->farthest";
    Week start1 = new Week();
    numEventsFailed = start1.create(byStart);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] st1 = {start1, start1.getWorth(), name};
    insertSorted(population,st1);

    // logic: get ahead as much as possible
    name = "by start date, farthest->closest";
    Week start2 = new Week();
    numEventsFailed = start2.create(reverseByStart);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] st2 = {start2, start2.getWorth(), name};
    insertSorted(population,st2);

    // logic: do tasks that are due sooner
    name = "by due date, closest->farthest";
    Week due1 = new Week();
    numEventsFailed = due1.create(byDue);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] d1 = {due1, due1.getWorth(), name};
    insertSorted(population,d1);

    // logic: get ahead as much as possible
    name = "by due date, farthest->closest";
    Week due2 = new Week();
    numEventsFailed = due2.create(reverseByDue);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] d2 = {due2, due2.getWorth(), name};
    insertSorted(population,d2);

    // logic: do tasks that have a specific timeframe first
    name = "timeframe: (start - due), low->high";
    Week time1 = new Week();
    numEventsFailed = time1.create(byTimeframe);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] t1 = {time1, time1.getWorth(), name};
    insertSorted(population,t1);

    // logic: do tasks that must be done in a specific timeframe first
    name = "restriction: (start - due) - hoursRequired, low->high";
    Week rest1 = new Week();
    numEventsFailed = rest1.create(byRestriction);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] l1 = {rest1, rest1.getWorth(), name};
    insertSorted(population,l1);

    // logic: important small events are most important
    // problem: biased, gives more weight to hours required
    name = "by smallImportant: importance * 1/hours required, high->low";
    Week simp1 = new Week();
    numEventsFailed = simp1.create(bySmallImportant); 
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] si1 = {simp1, simp1.getWorth(), name};
    insertSorted(population,si1);

    // logic: small important events are most important
    // problem: biased, gives more weight to hours required
    name = "importantSmall: 1/importance * hours required, low->high";
    Week imps1 = new Week();
    numEventsFailed = imps1.create(byImportantSmall);
    Debug.log(numEventsFailed," events were unable to added to the calendar ", name);
    Object[] is1 = {imps1, imps1.getWorth(), name};
    insertSorted(population,is1);


    Week rand1 = new Week();
    numEventsFailed = rand1.create(byRandom1);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r1 = {rand1, rand1.getWorth(), "random"};
    insertSorted(population,r1);

    Week rand2 = new Week();
    numEventsFailed = rand2.create(byRandom2);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r2 = {rand2, rand2.getWorth(), "random"};
    insertSorted(population,r2);

    Week rand3 = new Week();
    numEventsFailed = rand3.create(byRandom3);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r3 = {rand3, rand3.getWorth(), "random"};
    insertSorted(population,r3);

    Week rand4 = new Week();
    numEventsFailed = rand4.create(byRandom4);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r4 = {rand4, rand4.getWorth(), "random"};
    insertSorted(population,r4);

    Week rand5 = new Week();
    numEventsFailed = rand5.create(byRandom5);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r5 = {rand5, rand5.getWorth(), "random"};
    insertSorted(population,r5);

    Week rand6 = new Week();
    numEventsFailed = rand6.create(byRandom6);
    Debug.log(numEventsFailed," events were unable to added to the calendar random");
    Object[] r6 = {rand6, rand6.getWorth(), "random"};
    insertSorted(population,r6);


    // Print weeks in order by worth
    Debug.status("...printing weeks...");
    for (int i=0; i < population.size(); i++)
    {
      if (population.get(i)[0] != null)
      {
        Debug.log("Rank(",i+1,"), ",population.get(i)[2],"-- with worth ",population.get(i)[1]);
        Debug.log(population.get(i)[0]);
      }
    }

    // Print only each weeks's worth
    Debug.status("...printing weeks' rankings by worth...");
    for (int i=0; i < population.size(); i++)
    {
      if (population.get(i)[0] != null)
        Debug.log("Rank(",i+1,"), ",population.get(i)[2],"-- with worth ",population.get(i)[1]);
    }



    ////////////////////////////////////////////////////////////////////////////
    // Program unfinished from this point onward -------------------------------
    // - At this point, the initial population has been created. Their worths
    //   have each been calculated, and they are sorted by those worths.
    // - What is left to do:
    //    - Select n best weeks as survivors (simply grab 0-n of the sorted list)
    //    - Perform crossover and mutation to create the next generation, adding
    //      the best m weeks from the previous generation (elitism)
    //    - Use the methods I have written to sort the calendars by worth
    //    - Repeat until desired
    //        - for q iterations
    //        - or for a certain amount of time
    //        - or until the best calendar has a fitness above a given threshold



  
    // number of surviving weeks each generation
    // (as of 12/8/22, this is out of a population size of 17)
    int numSurvivors = 10;

    // percent mutation
    // (chance of random changes happening to single event in a survivor week)
    double mutationRate = .30; 

    // percent crossover
    // (chance of a combination between two or more calendars in each survivor week)
    double crossoverRate = 0.50;

    // number of generations
    int numGenerations = 10;



    ////////////////////////////////////////////////////////////////////////////
    // Perform crossover and mutation ---------------------------------------------

    /*
     * for i from 0 to numGenerations (or time or worth threshold met)
     *  evolve(population)
     * 
     * newPopulation.get(0) // this is our best result after numGenerations
     * 
     * 
     * function evolve(population)
     *  ArrayList<Week> newPopulation = new ArrayList<Week>();
     *  ArrayList<Week> survivors = population.get(0 - numSurvivors)
     *  for week in survivors
     *    crossover? = if random(0,1) > (1-crossoverRate)
     *    if crossover
     *      randomStartingPoint = random(0,survivors.size())
     *      for j from randomStartingPoint to (min (survivors.size(), randStartPt + 3))
     *        // check compatability for sectional crossover by trying to find
     *        // an event 1/3 - 2/3 through the week that ends at the same time
     *        // on both weeks. The weeks will be split on this event.
     *        if compatible
     *          // switch sections. not front->back (because then you would have
     *          // to check each event to make sure it is not violating its
     *          // start and due times), but back->back or front->front
     *          break
     *    for event in week
     *      mutate? = if random(0,1) > (1-mutationRate)
     *      if mutate
     *        // shorten or lengthen the event, shortening or lengthing neighbor events
     *        // or, swap the event with another in the calendar, if the start
     *        // and due times are compatible and the durations are the same
     *    newPopulation.insertSorted(week)
     * 
     *  // Add the best of the previous generation (elitism)
     *  newPopulation.insertSorted(population[0 - (population.length - numSurvivors)])
     * 
     * 
     * 
     * OR, using a different approach:
     * - associate the ordered ArrayList<Task> with the week. The same order of
     *    tasks will create the same week every time. Thus, instead of modifying
     *    the week itself (which is difficult and slow because it involves
     *    constraint satisfaction), modify the order of the tasks. 
     *    
     */


  }


  /*
  * Static Method: insertSorted
  * Description: Inserts a week into the population array such that the 
  * population array stays sorted by worth in descending order.
  * Arguments:
  * - population: the list of weeks in the generation
  * - weekElement: an array containing information about a week, in the format:
        [Week week, double worth, String sortedBy] (but typed only as objects)
  * Returns: void, sorting is done in-place
  */
  public static void insertSorted(ArrayList<Object[]> population, Object[] weekArr)
  {
    int length = population.size();

    if (length == 0)
    {
      population.add(weekArr);
      return;
    }

    for (int i=0; ; i++)
    {
      if ((double) (weekArr[1]) >= (double) (population.get(i)[1]))
      {
        population.add(i,weekArr);
        return;
      }
      if (i+1 == length)
      {
        // At this point, weekArr's worth is lower than population.get(i)'s,
        // so add at the end
        population.add(weekArr);
        return;
      }
    }
  }


  /*
   * Method: checkDue
   * Note: unused and only for debugging purposes
   * Description: Can be used to check the correctness (without printing and seeing
   * it visually) of a list of tasks sorted by due date. This could easily be
   * modified to check validity of sorting by other attributes as well.
   * Arguments:
   * - arr: a list of tasks that should be sorted by due date
   * Returns: void, but prints out its result
   */
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
    Debug.log(res);
  }
}







