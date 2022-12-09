/*
 * Class: Debugger
 * Description: Quick class for outputting debugging information. Can be enabled
 * or disabled on a global scale in this class.
 * Attributes: none
 * Bugs: none known
 * Limitations: none
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Instructions: 
 * Debug is enabled by default. To globally disable debugger, set Debug.enabled
 * below to false. To use Debug, simply do something like
 * "Debug.log("Adding objects");
 * 
 * Notes:
 * Even if Debug is enabled, it will not print every Debug statement--this is
 * because some individual functions contain blocks like:
 * 
 *  func()
 *      boolean prevDebugEnabled = Debug.enabled;
 *      Debug.enabled = false;
 *      // code in the function
 *      Debg.enabled = prevDebugEnabled;
 * 
 * This is done because this allows for users to print information about functions
 * they are interested in as a whole, and turn off other functions. Thus, if
 * Debug is disabled on a global scale, no debug messages will appear. If Debug
 * is enabled on a global scale, it will not necessarily print all Debug messages,
 * since certain functions could disable it temporarily.
 */


public class Debug {


    public static boolean enabled = true;

    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    /*
    * Method: log
    * Description: To be used for general debugging purposes, fixing code, or
    * low-level information.
    */
    public static void log(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_YELLOW+"DEBUG::"+TEXT_RESET);
            if (objects == null)
            {
                System.out.print("no argument given to Debug.log");
                return;
            }
            for (Object o : objects)
            {
                if (o == null)
                {
                    System.out.print("null");
                    continue;
                }
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }


    /*
     * Method: status
     * Description: To be used for showing program progress status; large-
     * picture program status updates.
     */
    public static void status(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_GREEN+"STATUS::"+TEXT_RESET);
            if (objects == null)
            {
                System.out.print("no argument given to Debug.log");
                return;
            }
            for (Object o : objects)
            {
                if (o == null)
                {
                    System.out.print("null");
                    continue;
                }
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }


    /*
     * Method: err
     * Description: To be used for displaying semantic/logic errors. Does not
     * end the program, only prints a red error.
     */
    public static void err(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_RED+"ERROR::"+TEXT_RESET);
            if (objects == null)
            {
                System.out.print("no argument given to Debug.log");
                return;
            }
            for (Object o : objects)
            {
                if (o == null)
                {
                    System.out.print("null");
                    continue;
                }
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }
}