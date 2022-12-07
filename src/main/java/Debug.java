/*
 * Class: Debugger
 * Description: Quick class for outputting debugging information. Can be enabled
 * or disabled.
 * Attributes: none
 * Bugs: none
 * Limitations:
 * - Can only print single objects, unlike stdio. 
 * Author: Sam Waggoner <samuel.waggoner@maine.edu>
 * Created: 12/09/22
 * For: COS 470, scheduler
 * Modifications: none
 * 
 * Instructions: 
 * Debug is enabled by default. To disable debugger, set Debug.enabled to false.
 * To use Debug, simply do something like "Debug.log("Adding objects");""
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
    * Description: To be used for general debugging purposes, fixing code.
    */
    // public static void log(Object o){
    //     if(Debug.enabled) {
    //         System.out.println(TEXT_YELLOW+"DEBUG::"+TEXT_RESET+o.toString());
    //     }           
    // }
    public static void log(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_YELLOW+"DEBUG::"+TEXT_RESET);
            for (Object o : objects)
            {
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }


    /*
     * Method: status
     * Description: To be used for showing program progress status.
     */
    public static void status(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_GREEN+"STATUS::"+TEXT_RESET);
            for (Object o : objects)
            {
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }


    /*
     * Method: err
     * Description: To be used for displaying semantic/logic errors.
     */
    public static void err(Object ... objects){
        if(Debug.enabled) {
            System.out.print(TEXT_RED+"ERROR::"+TEXT_RESET);
            for (Object o : objects)
            {
                System.out.print(o.toString());
            }
            System.out.print("\n");
        }           
    }
}