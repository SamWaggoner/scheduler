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





// TaskList.n = 5;
// int testN1 = TaskList.incN();
// int testN2 = TaskList.incN();
// System.out.println(testN1);
// System.out.println(testN2);



// TaskList.printTasks();
// TaskList.sortBy("hey");
// System.out.print("HEYYYYLOO");
// TaskList.printTasks();

// LocalDateTime now = LocalDate.now().atTime(5, 30, 00);
// Task x = new Task("myLabel", 1, 2.0, now);
// tasks.set(0,x);






// From Google Calendar:

// import com.google.api.client.auth.oauth2.Credential;
// import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
// import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
// import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
// import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
// import com.google.api.client.http.javanet.NetHttpTransport;
// import com.google.api.client.json.JsonFactory;
// import com.google.api.client.json.gson.GsonFactory;
// import com.google.api.client.util.DateTime;
// import com.google.api.client.util.store.FileDataStoreFactory;
// import com.google.api.services.calendar.Calendar;
// import com.google.api.services.calendar.CalendarScopes;
// import com.google.api.services.calendar.model.Event;
// import com.google.api.services.calendar.model.Events;

// import java.io.FileNotFoundException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.security.GeneralSecurityException;
// import java.util.Collections;
// import java.util.List;


  // /**
  //  * Application name.
  //  */
  // private static final String APPLICATION_NAME = "Sam's Scheduler";
  // /**
  //  * Global instance of the JSON factory.
  //  */
  // private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  // /**
  //  * Directory to store authorization tokens for this application.
  //  */
  // private static final String TOKENS_DIRECTORY_PATH = "tokens";

  // /**
  //  * Global instance of the scopes required by this quickstart.
  //  * If modifying these scopes, delete your previously saved tokens/ folder.
  //  */
  // private static final List<String> SCOPES =
  //     Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
  // private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  // /**
  //  * Creates an authorized Credential object.
  //  *
  //  * @param HTTP_TRANSPORT The network HTTP Transport.
  //  * @return An authorized Credential object.
  //  * @throws IOException If the credentials.json file cannot be found.
  //  */
  // private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
  //     throws IOException {
  //   // Load client secrets.
  //   InputStream in = Scheduler.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
  //   if (in == null) {
  //     throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
  //   }
  //   GoogleClientSecrets clientSecrets =
  //       GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

  //   // Build flow and trigger user authorization request.
  //   GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
  //       HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
  //       .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
  //       .setAccessType("offline")
  //       .build();
  //   LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
  //   Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  //   //returns an authorized Credential object.
  //   return credential;
  // }




  // public static void main(String... args) throws IOException, GeneralSecurityException
  // {


  // // Build a new authorized API client service.
  // final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
  // Calendar service =
  //     new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
  //         .setApplicationName(APPLICATION_NAME)
  //         .build();

  // // List the next 10 events from the primary calendar.
  // DateTime now = new DateTime(System.currentTimeMillis());
  // Events events = service.events().list("primary")
  //     .setMaxResults(10)
  //     .setTimeMin(now)
  //     .setOrderBy("startTime")
  //     .setSingleEvents(true)
  //     .execute();
  // List<Event> items = events.getItems();
  // if (items.isEmpty()) {
  //   System.out.println("No upcoming events found.");
  // } else {
  //   System.out.println("Upcoming events");
  //   for (Event event : items) {
  //     DateTime start = event.getStart().getDateTime();
  //     if (start == null) {
  //       start = event.getStart().getDate();
  //     }
  //     System.out.printf("%s (%s)\n", event.getSummary(), start);
  //   }
  // }